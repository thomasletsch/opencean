package org.enocean.java.packets;

import org.enocean.java.eep.ContactState;

public class RadioPacket1BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xD5;

    private LearnButtonState learnButton;
    private ContactState contact;

    public RadioPacket1BS(RawPacket rawPacket) {
        super(rawPacket);
    }

    @Override
    public void parseData() {
        super.parseData();
        byte dataByte = payload.getData()[1];
        contact = ContactState.values()[(dataByte & 0x01)];
        learnButton = LearnButtonState.values()[(dataByte & 0x08) >> 3];
    }

    @Override
    public String toString() {
        return "1BS " + getSenderId() + ", Learn Button " + learnButton.toString() + ", Contact " + contact.toString();
    }

}
