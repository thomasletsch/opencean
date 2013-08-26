package org.enocean.java;

import org.enocean.java.common.ParameterAddress;
import org.enocean.java.common.ParameterValueChangeListener;
import org.enocean.java.common.values.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingListener implements ParameterValueChangeListener {

    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    @Override
    public void valueChanged(ParameterAddress parameterId, Value value) {
        logger.info("Received RadioPacket with value " + value);
    }

}
