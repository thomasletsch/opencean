package org.enocean.java;

import org.enocean.java.packets.BasicPacket;

public interface EnoceanReceiver {

    void receivePacket(BasicPacket packet);

}
