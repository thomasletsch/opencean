package org.enocean.java;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanId;
import org.enocean.java.eep.EEPId;
import org.enocean.java.eep.EEPParser;
import org.enocean.java.eep.EEPParserFactory;
import org.enocean.java.eep.RockerSwitch;
import org.enocean.java.eep.TemperaturSensor;
import org.enocean.java.eep.Value;
import org.enocean.java.packets.RadioPacket;

public class ValueRetriever {
    private Map<EnoceanId, EEPId> deviceToEEP = new HashMap<EnoceanId, EEPId>();
    private EEPParserFactory parserFactory = new EEPParserFactory();

    public ValueRetriever() {
        deviceToEEP.put(new EnoceanId(new byte[] { 0x00, (byte) 0x82, (byte) 0x90, 0x1D }), TemperaturSensor.EEP_ID);
        deviceToEEP.put(new EnoceanId(new byte[] { 0x00, (byte) 0x22, (byte) 0x5B, (byte) 0xFB }), RockerSwitch.EEP_ID);
    }

    public Map<String, Value> retrieveValue(RadioPacket radioPacket) {
        if (deviceToEEP.containsKey(radioPacket.getSenderId())) {
            EEPId profile = deviceToEEP.get(radioPacket.getSenderId());
            EEPParser parser = parserFactory.getParserFor(profile);
            if (profile != null && parser != null) {
                return parser.parsePacket(radioPacket);
            }
        }
        return new HashMap<String, Value>();
    }

}
