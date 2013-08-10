package org.enocean.java.eep;

import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.packets.BasicPacket;

public interface EEPParser {

    Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet);

}