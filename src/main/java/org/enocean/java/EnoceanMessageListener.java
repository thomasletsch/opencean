package org.enocean.java;

import org.enocean.java.packets.BasicPacket;

public interface EnoceanMessageListener {

    void receivePacket(BasicPacket packet);

}
