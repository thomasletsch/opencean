package org.enocean.java.eep;

import org.enocean.java.packets.BasicPacket;

public interface EEPParser {

    Value parsePacket(BasicPacket packet);

}