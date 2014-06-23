package org.opencean.core.eep;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculationUtil {

    /**
     * Re-scales an input byte of a given range (rangeMin, rangeMax) to a given scale (scaleMin, scaleMax). Means if the input is the same
     * as rangeMax, it returns an output of scaleMax. If the input is the same as rangeMin, the output is scaleMin. Values between rangeMin
     * and rangeMax get returns in between scaleMin and scaleMax.
     */
    public BigDecimal rangeValue(byte input, double scaleMin, double scaleMax, double rangeMin, double rangeMax, int digits) {
        int rawValue = input & 0xFF;
        double multiplier = (scaleMax - scaleMin) / (rangeMax - rangeMin);
        return new BigDecimal(multiplier * (rawValue - rangeMin) + scaleMin, new MathContext(digits));
    }

    public static <T extends Comparable<T>> T fitInRange(T value, T min, T max) {
        if (value.compareTo(min) < 0) {
            return min;
        } else if (value.compareTo(max) > 0) {
            return max;
        } else {
            return value;
        }
    }

    public static double rangeToScale(long rawValue, long rangeMin, long rangeMax, double scaleMin, double scaleMax) {
        final double multiplier = (scaleMax - scaleMin) / (rangeMax - rangeMin);
        final double devValue = multiplier * (rawValue - rangeMin) + scaleMin;
        return fitInRange(devValue, scaleMin, scaleMax);
    }

    public static long scaleToRange(double devValue, double scaleMin, double scaleMax, long rangeMin, long rangeMax) {
        final double multiplier = (rangeMax - rangeMin) / (scaleMax - scaleMin);
        final double rawValue = multiplier * (devValue - scaleMin) + rangeMin;
        return fitInRange((long) rawValue, rangeMin, rangeMax);
    }
}
