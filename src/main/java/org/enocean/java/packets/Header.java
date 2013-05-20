package org.enocean.java.packets;

import org.enocean.java.utils.CRC8;
import org.enocean.java.utils.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Header {
    private static Logger logger = LoggerFactory.getLogger(Header.class);

    private byte packetType;
    private short dataLength;
    private byte optionalDataLength;
    private byte crc8h;

    public static Header from(CircularByteBuffer buffer) {
        logger.info("Reading header...");
        Header header = new Header();
        header.dataLength = buffer.getShort();
        header.optionalDataLength = buffer.get();
        header.packetType = buffer.get();
        header.crc8h = buffer.get();
        logger.info(header.toString());
        return header;
    }

    public Header() {
    }

    public Header(byte packetType, short dataLength, byte optionalDataLength, byte crc8h) {
        this.packetType = packetType;
        this.dataLength = dataLength;
        this.optionalDataLength = optionalDataLength;
        this.crc8h = crc8h;
    }

    public byte[] toBytes() {
        ByteArrayWrapper bytes = new ByteArrayWrapper();
        bytes.addByte(BasicPacket.SYNC_BYTE);
        bytes.addShort(dataLength);
        bytes.addByte(optionalDataLength);
        bytes.addByte(packetType);
        bytes.addByte(crc8h);
        return bytes.getArray();
    }

    public boolean isValid() {
        return calculateCrc8() == crc8h;
    }

    public void checkCrc8() {
        if (calculateCrc8() != crc8h) {
            throw new RuntimeException("Header CRC 8 is not correct! Expected " + calculateCrc8() + ", but received " + crc8h);
        }
    }

    public byte getPacketType() {
        return packetType;
    }

    public short getDataLength() {
        return dataLength;
    }

    public byte getOptionalDataLength() {
        return optionalDataLength;
    }

    private byte[] getDataLengthBytes() {
        byte lowByte = (byte) (dataLength & 0xFF);
        byte highByte = (byte) ((dataLength >> 8) & 0xFF);
        return new byte[] { highByte, lowByte };
    }

    private byte calculateCrc8() {
        CRC8 crc8 = new CRC8();
        crc8.update(getDataLengthBytes()[0]);
        crc8.update(getDataLengthBytes()[1]);
        crc8.update(optionalDataLength);
        crc8.update(packetType);
        return (byte) crc8.getValue();
    }

    @Override
    public String toString() {
        return "Header: " + "dataLength=" + dataLength + ", optionalDataLength=" + optionalDataLength + ", packetType=" + packetType
                + ", crc8h=" + crc8h;
    }

}
