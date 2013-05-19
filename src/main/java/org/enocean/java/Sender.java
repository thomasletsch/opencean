package org.enocean.java;

import org.enocean.java.struct.BasicPacket;
import org.enocean.java.struct.ResponsePacket;

public interface Sender {

    ResponsePacket sendRadio(BasicPacket message);

    ResponsePacket sendRadioSubTel(BasicPacket message);

    ResponsePacket sendRemoteManagementCommand(BasicPacket message);

    ResponsePacket sendCommonCommand(BasicPacket message);

    ResponsePacket sendSmartAckCommand(BasicPacket message);
}
