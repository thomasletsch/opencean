package org.enocean.java.packets;

public class RadioPacket4BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xA5;

    public RadioPacket4BS(RawPacket rawPacket) {
        super(rawPacket);
    }

}
