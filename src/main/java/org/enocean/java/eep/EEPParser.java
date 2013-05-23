package org.enocean.java.eep;

import java.util.Map;

import org.enocean.java.packets.BasicPacket;

public interface EEPParser {

    Map<String, Value> parsePacket(BasicPacket packet);

}