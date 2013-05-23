package org.enocean.java;

import java.util.Map;

import org.enocean.java.eep.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingListener implements EnoceanMessageListener {

    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);
    private ValueRetriever valueRetriever = new ValueRetriever();

    public LoggingListener() {
    }

    @Override
    public void receivePacket(BasicPacket packet) {
        if (packet instanceof RadioPacket) {
            RadioPacket radioPacket = (RadioPacket) packet;
            Map<String, Value> values = valueRetriever.retrieveValue(radioPacket);
            if (!values.isEmpty()) {
                logger.info("Received RadioPacket with values " + values);
            }
        }
    }
}
