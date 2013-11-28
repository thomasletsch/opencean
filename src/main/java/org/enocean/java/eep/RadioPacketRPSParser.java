package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacketRPS;

public abstract class RadioPacketRPSParser implements EEPParser {

    protected abstract void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacketRPS radioPacket);

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacketRPS) {
            RadioPacketRPS radioPacketRPS = (RadioPacketRPS) packet;
            parsePacket(map, radioPacketRPS);
        }
        return map;
    }

}
