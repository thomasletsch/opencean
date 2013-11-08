package org.enocean.java.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.EEPId;
import org.enocean.java.common.Parameter;
import org.enocean.java.common.values.ContactState;
import org.enocean.java.common.values.NumberWithUnit;
import org.enocean.java.common.values.Unit;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacketVLD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PowerSensor implements EEPParser {

    private static Logger logger = LoggerFactory.getLogger(PowerSensor.class);
    private BigDecimal currentValue;
    private EEPId eep;

    public PowerSensor(EEPId eep) {
        this.eep = eep;
        logger.info("new Power sensor, eep " + eep.getId());
    }

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        logger.info("Power eep " + eep.getId());
        if (packet instanceof RadioPacketVLD) {
            RadioPacketVLD radioPacketVLD = (RadioPacketVLD) packet;
            if (radioPacketVLD.getCMDByte() == 0x07) {
                currentValue = new BigDecimal(radioPacketVLD.getMValue(), new MathContext(3));
                map.put(new EnoceanParameterAddress(radioPacketVLD.getSenderId(), Parameter.POWER), new NumberWithUnit(Unit.WATT,
                        currentValue));
            } else if (radioPacketVLD.getCMDByte() == 0x04) {
                byte value = radioPacketVLD.getStateByte();
                if (value != 0) {
                    value = 1; // any on state == ON
                }
                ContactState contact = ContactState.values()[value];
                logger.info("State " + value + " " + contact.toString());
                map.put(new EnoceanParameterAddress(radioPacketVLD.getSenderId(), Parameter.CONTACT_STATE), contact);
            }
        }
        return map;
    }
}