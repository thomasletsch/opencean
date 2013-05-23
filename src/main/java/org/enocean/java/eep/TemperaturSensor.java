package org.enocean.java.eep;

import java.math.BigDecimal;
import java.math.MathContext;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket4BS;

public class TemperaturSensor implements EEPParser {

    private static final float RANGE_MIN = 0;
    private static final float RANGE_MAX = 255;

    public static final EEPId EEP_ID = new EEPId("A5:02:05");

    private int scaleMin;

    private int scaleMax;

    private BigDecimal currentValue;

    public TemperaturSensor(int scaleMin, int scaleMax) {
        this.scaleMin = scaleMin;
        this.scaleMax = scaleMax;
    }

    @Override
    public Value parsePacket(BasicPacket packet) {
        if (packet instanceof RadioPacket4BS) {
            RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;
            byte source = radioPacket4BS.getDb1();
            calculateCurrentValue(source);
            return new NumberWithUnit(Unit.DEGREE_CELSIUS, currentValue);
        }
        return new UndefinedValue();
    }

    private void calculateCurrentValue(byte source) {
        int multiplier = source & 0xFF;
        currentValue = new BigDecimal(scaleMin + multiplier * ((scaleMax - scaleMin) / (RANGE_MAX - RANGE_MIN)), new MathContext(3));
    }
}
