package org.enocean.java;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.ResponsePacket;

public interface Sender {

    ResponsePacket sendRadio(BasicPacket message);

    ResponsePacket sendRadioSubTel(BasicPacket message);

    ResponsePacket sendRemoteManagementCommand(BasicPacket message);

    ResponsePacket sendCommonCommand(BasicPacket message);

    ResponsePacket sendSmartAckCommand(BasicPacket message);
}
