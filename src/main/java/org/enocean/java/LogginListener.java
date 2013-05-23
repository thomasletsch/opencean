package org.enocean.java;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanId;
import org.enocean.java.eep.EEPId;
import org.enocean.java.eep.EEPParser;
import org.enocean.java.eep.EEPParserFactory;
import org.enocean.java.eep.TemperaturSensor;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogginListener implements EnoceanMessageListener {

    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    private Map<EnoceanId, EEPId> deviceToEEP = new HashMap<EnoceanId, EEPId>();
    private EEPParserFactory parserFactory = new EEPParserFactory();

    public LogginListener() {
        deviceToEEP.put(new EnoceanId(new byte[] { 0x00, (byte) 0x82, (byte) 0x90, 0x1D }), TemperaturSensor.EEP_ID);
    }

    @Override
    public void receivePacket(BasicPacket packet) {
        if (packet instanceof RadioPacket) {
            RadioPacket radioPacket = (RadioPacket) packet;
            if (deviceToEEP.containsKey(radioPacket.getSenderId())) {
                EEPId profile = deviceToEEP.get(radioPacket.getSenderId());
                EEPParser parser = parserFactory.getParserFor(profile);
                if (profile != null && parser != null) {
                    logger.info("Received RadioPacket with value " + parser.parsePacket(radioPacket));
                }
            }
        }
    }
}
