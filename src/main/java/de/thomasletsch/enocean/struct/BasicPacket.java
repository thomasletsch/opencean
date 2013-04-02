package de.thomasletsch.enocean.struct;

import java.nio.ByteBuffer;

public abstract class BasicPacket {

    /*
     * More packet types:
     * 
     * 0: Reserved
     * 
     * 8..127: Reserved for EnOcean
     * 
     * 128..255: Manufactorer specific commands and messages
     */

    private static final int HEADER_LENGTH = 3;

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

    private byte syncByte = 0x55;

    private byte packetType = 0x00;

    private short dataLength;

    private byte optionalDataLength;

    public byte[] toBytes() {
        ByteArrayWrapper message = new ByteArrayWrapper();
        message.addByte(syncByte);
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
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        syncByte = bb.get();
        byte[] headerBytes = new byte[HEADER_LENGTH];
        bb.get(headerBytes);
        readHeader(ByteBuffer.wrap(headerBytes));
        byte[] dataBytes = new byte[dataLength];
        bb.get(dataBytes);
        readData(ByteBuffer.wrap(dataBytes));
        byte[] optionalDataBytes = new byte[optionalDataLength];
        bb.get(optionalDataBytes);
        readOptionalData(ByteBuffer.wrap(optionalDataBytes));
    }

    protected void readHeader(ByteBuffer headerBytes) {
        dataLength = headerBytes.getShort();
        optionalDataLength = headerBytes.get();
        packetType = headerBytes.get();
    }

    protected abstract void readData(ByteBuffer dataBytes);

    protected abstract void readOptionalData(ByteBuffer optionalDataBytes);

    protected abstract byte[] getData();

    protected abstract byte[] getOptionalData();

    public byte getPacketType() {
        return packetType;
    }

    public void setPacketType(byte packetType) {
        this.packetType = packetType;
    }

    public void setDataLength(short dataLength) {
        this.dataLength = dataLength;
    }

    protected int getDataLength() {
        return dataLength;
    }

    protected byte getOptionalDataLength() {
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

    private byte getDataCrc8() {
        CRC8 crc8 = new CRC8();
        crc8.update(getData(), 0, getDataLength());
        crc8.update(getOptionalData(), 0, getOptionalDataLength());
        return (byte) crc8.getValue();
    }

}
