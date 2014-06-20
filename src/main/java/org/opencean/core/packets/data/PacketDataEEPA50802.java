/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opencean.core.packets.data;

import org.opencean.core.common.values.Unit;
import org.opencean.core.eep.CalculationUtil;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50802 extends PacketData4BS {

    public static final int SUPPLY_VOLTAGE_RANGE_MIN = 0;
    public static final int SUPPLY_VOLTAGE_RANGE_MAX = 255;
    public static final double SUPPLY_VOLTAGE_SCALE_MIN = 0;
    public static final double SUPPLY_VOLTAGE_SCALE_MAX = 5.1;
    public static final Unit SUPPLY_VOLTAGE_UNIT = Unit.VOLTAGE;

    public static final int ILLUMINATION_RANGE_MIN = 0;
    public static final int ILLUMINATION_RANGE_MAX = 1020;
    public static final double ILLUMINATION_SCALE_MIN = 0;
    public static final double ILLUMINATION_SCALE_MAX = 510;
    public static final Unit ILLUMINATION_UNIT = Unit.LUX;

    public static final int TEMPERATURE_RANGE_MIN = 0;
    public static final int TEMPERATURE_RANGE_MAX = 255;
    public static final double TEMPERATURE_SCALE_MIN = 0;
    public static final double TEMPERATURE_SCALE_MAX = 51;
    public static final Unit TEMPERATURE_UNIT = Unit.DEGREE_CELSIUS;

    public PacketDataEEPA50802() {
    }

    public PacketDataEEPA50802(byte[] eepData) {
        super(eepData);
    }

    public double getSupplyVoltage() {
        final long range = getDataRange(3, 7, 3, 0);
        final double scale = CalculationUtil.rangeToScale(range, SUPPLY_VOLTAGE_RANGE_MIN, SUPPLY_VOLTAGE_RANGE_MAX, SUPPLY_VOLTAGE_SCALE_MIN, SUPPLY_VOLTAGE_SCALE_MAX);
        return scale;
    }

    public double getIllumination() {
        final long range = getDataRange(2, 7, 2, 0);
        final double scale = CalculationUtil.rangeToScale(range, ILLUMINATION_RANGE_MIN, ILLUMINATION_RANGE_MAX, ILLUMINATION_SCALE_MIN, ILLUMINATION_SCALE_MAX);
        return scale;
    }

    public double getTemperature() {
        final long range = getDataRange(1, 7, 1, 0);
        final double scale = CalculationUtil.rangeToScale(range, TEMPERATURE_RANGE_MIN, TEMPERATURE_RANGE_MAX, TEMPERATURE_SCALE_MIN, TEMPERATURE_SCALE_MAX);
        return scale;
    }

    public boolean isPIRStatusOn() {
        return getDataBit(0, 1) == 0;
    }

    public boolean isOccupancyButtonPressed() {
        return getDataBit(0, 0) == 0;
    }
}
