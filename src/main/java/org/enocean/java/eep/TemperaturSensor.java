package org.enocean.java.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket4BS;

public class TemperaturSensor implements EEPParser {

    private static final float RANGE_MIN = 255;
    private static final float RANGE_MAX = 0;

    public static final EEPId EEP_ID = new EEPId("A5:02:05");

    public static final String PARAMETER_ID = "TEMPERATURE";

    private int scaleMin;

    private int scaleMax;

    private BigDecimal currentValue;

    public TemperaturSensor(int scaleMin, int scaleMax) {
        this.scaleMin = scaleMin;
        this.scaleMax = scaleMax;
    }

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket4BS) {
            RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;
            byte source = radioPacket4BS.getDb1();
            calculateCurrentValue(source);
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID), new NumberWithUnit(Unit.DEGREE_CELSIUS,
                    currentValue));
        }
        return map;
    }

    private void calculateCurrentValue(byte source) {
        int rawValue = source & 0xFF;
        float multiplier = (scaleMax - scaleMin) / (RANGE_MAX - RANGE_MIN);
        currentValue = new BigDecimal(multiplier * (rawValue - RANGE_MIN) + scaleMin, new MathContext(3));
    }
}
