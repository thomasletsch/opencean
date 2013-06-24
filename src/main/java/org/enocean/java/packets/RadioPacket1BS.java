package org.enocean.java.packets;


public class RadioPacket1BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xD5;

    private LearnButtonState learnButton;

    public RadioPacket1BS(RawPacket rawPacket) {
        super(rawPacket);
    }

    @Override
    public void parseData() {
        super.parseData();
        learnButton = LearnButtonState.values()[(getDataByte() & 0x08) >> 3];
    }

    public byte getDataByte() {
        return payload.getData()[1];
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", [dataByte=%02X, learnButton=%s]", getDataByte(), learnButton);
    }

}
