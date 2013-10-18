package org.enocean.java.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
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
    /**
     * Electronic switches and Type dimmers 0x08 (description: with Energy
     * Measurement see table) and Local Control
     */
    public static final EEPId EEP_ID = new EEPId("D2:01:08");

    public static final String PARAMETER_ID = "POWER";
    public static final String PARAMETER_ID2 = "CONTACT_STATE";

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
                map.put(new EnoceanParameterAddress(radioPacketVLD.getSenderId(), PARAMETER_ID), new NumberWithUnit(Unit.DEGREE_CELSIUS,
                        currentValue));
            } else if (radioPacketVLD.getCMDByte() == 0x04) {
                byte value = radioPacketVLD.getStateByte();
                if (value != 0) {
                    value = 1; // any on state == ON
                }
                ContactState contact = ContactState.values()[value];
                logger.info("State " + value + " " + contact.toString());
                map.put(new EnoceanParameterAddress(radioPacketVLD.getSenderId(), PARAMETER_ID2), contact);
            }
        }
        return map;
    }
}