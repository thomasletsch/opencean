package org.enocean.java.packets;

public class RadioPacket1BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xD5;

    public enum ContactState {
        OPEN(0),
        CLOSED(1);

        private final int enumvalue;

        ContactState(int value) {
            this.enumvalue = value;
        }

        ContactState(byte value) {
            this.enumvalue = value;
        }

        public byte toByte() {
            return (byte) enumvalue;
        }

        @Override
        public String toString() {
            return ( enumvalue == 0 ) ? "Open" : "Closed";
        }
    }

    public enum LearnButtonState {
        PRESSED(0),
        NOTPRESSED(1);

        private final int enumvalue;

        LearnButtonState(int value) {
            this.enumvalue = value;
        }

        LearnButtonState(byte value) {
            this.enumvalue = value;
        }

        public byte toByte() {
            return (byte) enumvalue;
        }

        @Override
        public String toString() {
            return ( enumvalue == 0 ) ? "Pressed" : "Not Pressed";
        }
    }

    private LearnButtonState learnButton;
    private ContactState contact;


    public RadioPacket1BS(byte[] buffer) {
        super( buffer );
    }

    public static RadioPacket1BS ResolvedPacket( UnknownPacket loPacket) {
        RadioPacket1BS loNew = null;
        try {
            loNew = new RadioPacket1BS( loPacket.toBytes() );
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return loNew;
    }


    @Override
    public void setData(byte[] data) {
        super.setData(data);
        setSenderId(String.format("%1$02X:%2$02X:%3$02X:%4$02X", data[2], data[3], data[4], data[5]));
        byte dataByte = data[1];
        contact = ContactState.values()[(dataByte & 0x01)];
        learnButton = LearnButtonState.values()[(dataByte & 0x08) >> 3];
    }


    @Override
    public String toString() {
        return "1BS " + getSenderId() +
                ", Learn Button " + learnButton.toString() +
                ", Contact " + contact.toString();
    }

}
