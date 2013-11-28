package org.enocean.java.eep;

import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.EEPId;
import org.enocean.java.common.Parameter;
import org.enocean.java.common.values.NumberWithUnit;
import org.enocean.java.common.values.Unit;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.LearnButtonState;
import org.enocean.java.packets.RadioPacket4BS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperaturSensor extends RadioPacket4BSParser {

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
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        byte db1 = packet.getDb1();
        values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.TEMPERATURE), new NumberWithUnit(Unit.DEGREE_CELSIUS,
                calculationUtil.rangeValue(db1, scaleMin, scaleMax, RANGE_MIN, RANGE_MAX, 3)));
    }
}
