package org.enocean.java;

import org.enocean.java.packets.BasicPacket;

public interface Receiver {

    void receiveRadio(BasicPacket message);

    void receiveRadioSubTel(BasicPacket message);

    void receiveRemoteManagementCommand(BasicPacket message);

    void receiveEvent(BasicPacket message);

}
