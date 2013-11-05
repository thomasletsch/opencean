package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.values.NumberWithUnit;
import org.enocean.java.common.values.Unit;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.LearnButtonState;
import org.enocean.java.packets.RadioPacket4BS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperaturSensor implements EEPParser {

    public static final EEPId EEP_ID = new EEPId("A5:02:05");

    public static final EEPId EEP_ID_3 = new EEPId("A5:07:03");

    private static Logger logger = LoggerFactory.getLogger(TemperaturSensor.class);

    private static final int RANGE_MIN = 255;
    private static final int RANGE_MAX = 0;

    private int scaleMin;
    private int scaleMax;

    private EEPId eep;
    private LearnButtonState learnButton;

    private CalculationUtil calculationUtil = new CalculationUtil();

    public TemperaturSensor(int scaleMin, int scaleMax, EEPId eep) {
        this.scaleMin = scaleMin;
        this.scaleMax = scaleMax;
        this.eep = eep;
        logger.info("new Temp sensor, eep " + eep.getId());
    }

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket4BS) {
            RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;
            byte db1 = radioPacket4BS.getDb1();
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.TEMPERATURE), new NumberWithUnit(
                    Unit.DEGREE_CELSIUS, calculationUtil.rangeValue(db1, scaleMin, scaleMax, RANGE_MIN, RANGE_MAX, 3)));
        }
        return map;
    }
}
