package de.thomasletsch.enocean;

import de.thomasletsch.enocean.struct.BasicPacket;

public interface Receiver {

    void receiveRadio(BasicPacket message);

    void receiveRadioSubTel(BasicPacket message);

    void receiveRemoteManagementCommand(BasicPacket message);

    void receiveEvent(BasicPacket message);

}
