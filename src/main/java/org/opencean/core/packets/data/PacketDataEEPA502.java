package org.opencean.core.packets.data;

import org.opencean.core.common.values.Unit;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA502 extends PacketData4BS {

    private final int tempStartDB;
    private final int tempStartBit;
    private final int tempEndDB;
    private final int tempEndBit;
    private final long tempRangeMin;
    private final long tempRangeMax;
    private final double tempScaleMin;
    private final double tempScaleMax;
    public static final Unit TEMPERATURE_UNIT = Unit.DEGREE_CELSIUS;

    public PacketDataEEPA502(byte[] eepData,
                             int tempStartDB, int tempStartBit, int tempEndDB, int tempEndBit,
                             long tempRangeMin, long tempRangeMax,
                             double tempScaleMin, double tempScaleMax) {
        super(eepData);
        this.tempStartDB = tempStartDB;
        this.tempStartBit = tempStartBit;
        this.tempEndDB = tempEndDB;
        this.tempEndBit = tempEndBit;
        this.tempRangeMin = tempRangeMin;
        this.tempRangeMax = tempRangeMax;
        this.tempScaleMin = tempScaleMin;
        this.tempScaleMax = tempScaleMax;
    }

    public double getTemperature() throws PacketDataScaleValueException {
        return getScaleValue(tempStartDB, tempStartBit, tempEndDB, tempEndBit,
                tempRangeMin, tempRangeMax,
                tempScaleMin, tempScaleMax);
    }
}
