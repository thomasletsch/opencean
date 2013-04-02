package de.thomasletsch.enocean;

import de.thomasletsch.enocean.struct.BasicPacket;
import de.thomasletsch.enocean.struct.ResponsePacket;

public interface Sender {

    ResponsePacket sendRadio(BasicPacket message);

    ResponsePacket sendRadioSubTel(BasicPacket message);

    ResponsePacket sendRemoteManagementCommand(BasicPacket message);

    ResponsePacket sendCommonCommand(BasicPacket message);

    ResponsePacket sendSmartAckCommand(BasicPacket message);
}
