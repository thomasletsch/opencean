package org.enocean.java.packets;


public class RadioPacketRPS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xF6;
    private byte dataByte;
    private byte statusByte;

    public RadioPacketRPS(RawPacket rawPacket) {
        super(rawPacket);
    }

    @Override
    public void parseData() {
        super.parseData();
        dataByte = payload.getData()[1];
    }

    public byte getDataByte() {
        return dataByte;
    }

    public byte getStatusByte() {
        return statusByte;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
