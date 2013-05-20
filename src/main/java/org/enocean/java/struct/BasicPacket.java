package org.enocean.java.struct;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public abstract class BasicPacket {

    /*
     * More packet types:
     * 
     * 0: Reserved
     * 
     * 8..127: Reserved for EnOcean
     * 
     * 128..255: Manufacturer specific commands and messages
     */

    public static final int HEADER_LENGTH = 4;

    /**
     * Radio telegram
     */
    public static final byte PACKET_TYPE_RADIO = 0x01;

    /**
     * Response to any Packet
     */
    public static final byte PACKET_TYPE_RESPONSE = 0x02;

    /**
     * Radio subtelegram
     */
    public static final byte PACKET_TYPE_RADIO_SUB_TEL = 0x03;

    /**
     * Event message
     */
    public static final byte PACKET_TYPE_EVENT = 0x04;
    public static final byte PACKET_TYPE_COMMON_COMMAND = 0x05;
    public static final byte PACKET_TYPE_SMART_ACK_COMMAND = 0x06;

    /**
     * Remote management command
     */
    public static final byte PACKET_TYPE_REMOTE_MAN_COMMAND = 0x07;
    public static final int POS_HEADER_START = 1;
    public static final int POS_DATA_START = 6;
    public static final byte SYNC_BYTE = 0x55;

    private byte packetType = 0x00;
    private short dataLength;
    private byte optionalDataLength;
    private byte crc8h;
    private byte crc8d;
    private byte[] data;
    private byte[] optionaldata;


    public byte[] toBytes() {
        ByteArrayWrapper message = new ByteArrayWrapper();
        message.addByte(SYNC_BYTE);
        message.addBytes(getDataLengthBytes());
        message.addByte(getOptionalDataLength());
        message.addByte(getPacketType());
        message.addByte(getHeaderCrc8());
        message.addBytes(getData());
        message.addBytes(getOptionalData());
        message.addByte(getDataCrc8());
        return message.getArray();
    }

    public void readFrom(byte[] bytes) {
        ByteArrayWrapper loReceiveBuffer = new ByteArrayWrapper(bytes);
        readMessage(loReceiveBuffer);
    }

    public void readHeader(ByteArrayWrapper headerBytes) {
        ByteBuffer bb = ByteBuffer.wrap(headerBytes.getArray());

        if ( bb.capacity() >= POS_DATA_START) {
            //syncByte first
            bb.get();
            dataLength = bb.getShort();
            optionalDataLength = bb.get();
            packetType = bb.get();
            crc8h = bb.get();
        }
        else
        {
            throw new RuntimeException(String.format("Invalid header length, should be %d but is %d", POS_DATA_START, bb.capacity()));
        }
    }

    public void readMessage(ByteArrayWrapper dataBytes) {

        ByteBuffer bb = ByteBuffer.wrap(dataBytes.getArray());
        if ( bb.capacity() >= 6 ) {

            byte[] loHeader = new byte[POS_DATA_START];
            bb.get( loHeader );

            readHeader( new ByteArrayWrapper(loHeader) );

            if ( bb.capacity() >= getDataLength() ) {
                data = new byte[ getDataLength() ];
                bb.get(data);

                optionaldata = new byte[ getOptionalDataLength() ];
                bb.get(optionaldata);

                crc8d = bb.get();
            }
            else
            {
                throw new RuntimeException(String.format("Invalid data length. Expecting %d, found %d", getDataLength(), bb.capacity() ));
            }
        }
        else
        {
            throw new RuntimeException(String.format("Invalid header length, should be %d", POS_DATA_START));
        }
    }

    protected byte[] getData() {
        return data;
    }
    protected void setData(byte[] poData) {
        data = poData;
        setDataLength( (short) poData.length );
    }

    // This field corresponds also to R-ORG
    protected byte getChoice() {
        if ( data == null || data.length == 0) {
            return 0;
        }
        return data[0];
    }

    protected byte[] getOptionalData() {
        return optionaldata;
    }

    public byte getPacketType() {
        return packetType;
    }

    public void setPacketType(byte packetType) {
        this.packetType = packetType;
    }

    public void setDataLength(short dataLength) {
        this.dataLength = dataLength;
    }

    public int getDataLength() {
        return dataLength;
    }

    public byte getOptionalDataLength() {
        return optionalDataLength;
    }

    public void setOptionalDataLength(byte optionalDataLength) {
        this.optionalDataLength = optionalDataLength;
    }

    private byte[] getDataLengthBytes() {
        byte lowByte = (byte) (getDataLength() & 0xFF);
        byte highByte = (byte) ((getDataLength() >> 8) & 0xFF);
        return new byte[] { highByte, lowByte };
    }

    private byte getHeaderCrc8() {
        CRC8 crc8 = new CRC8();
        crc8.update(getDataLengthBytes()[0]);
        crc8.update(getDataLengthBytes()[1]);
        crc8.update(getOptionalDataLength());
        crc8.update(getPacketType());
        return (byte) crc8.getValue();
    }

    public boolean isHeaderCrc8Correct() {
        return (getHeaderCrc8() == crc8h);
    }

    private byte getDataCrc8() {
        CRC8 crc8 = new CRC8();
        crc8.update(getData(), 0, getDataLength());
        crc8.update(getOptionalData(), 0, getOptionalDataLength());
        return (byte) crc8.getValue();
    }

    public boolean isDataCrc8Correct() {
        return (getDataCrc8() == crc8d);
    }

    public String BuffertoString(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

}
