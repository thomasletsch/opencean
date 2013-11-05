package org.enocean.java.eep;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculationUtil {

    public BigDecimal rangeValue(byte input, double scaleMin, double scaleMax, double rangeMin, double rangeMax, int digits) {
        int rawValue = input & 0xFF;
        double multiplier = (scaleMax - scaleMin) / (rangeMax - rangeMin);
        return new BigDecimal(multiplier * (rawValue - rangeMin) + scaleMin, new MathContext(digits));
    }

}
