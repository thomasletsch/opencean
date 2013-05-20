package org.enocean.java.packets;


public class RadioPacket4BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xA5;

    public RadioPacket4BS(byte[] buffer) {
        super( buffer );
    }

    @Override
    public void setData(byte[] data) {
        super.setData(data);
        setSenderId(String.format("%1$02X:%2$02X:%3$02X:%4$02X", data[5], data[6], data[7], data[8]));
    }

}
