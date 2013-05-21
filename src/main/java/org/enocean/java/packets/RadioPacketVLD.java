package org.enocean.java.packets;

public class RadioPacketVLD extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xD2;

    public RadioPacketVLD(RawPacket rawPacket) {
        super(rawPacket);
    }

}
