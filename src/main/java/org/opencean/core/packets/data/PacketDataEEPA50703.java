package org.opencean.core.packets.data;

import org.opencean.core.common.values.Unit;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50703 extends PacketData4BS {

    public static final long SUPPLY_VOLTAGE_RANGE_MIN = 0;
    public static final long SUPPLY_VOLTAGE_RANGE_MAX = 250;
    public static final double SUPPLY_VOLTAGE_SCALE_MIN = 0;
    public static final double SUPPLY_VOLTAGE_SCALE_MAX = 5;
    public static Unit SUPPLY_VOLTAGE_UNIT = Unit.VOLTAGE;

    public static final long ILLUMINATION_RANGE_MIN = 0;
    public static final long ILLUMINATION_RANGE_MAX = 1000;
    public static final double ILLUMINATION_SCALE_MIN = 0;
    public static final double ILLUMINATION_SCALE_MAX = 1000;
    public static Unit ILLUMINATION_UNIT = Unit.LUX;

    public PacketDataEEPA50703(byte[] eepData) {
        super(eepData);
    }

    public double getSupplyVoltage() throws PacketDataScaleValueException {
        return getScaleValue(3, 7, 3, 0, SUPPLY_VOLTAGE_RANGE_MIN, SUPPLY_VOLTAGE_RANGE_MAX, SUPPLY_VOLTAGE_SCALE_MIN, SUPPLY_VOLTAGE_SCALE_MAX);
    }

    public Unit getSupplyVoltageUnit() {
        return SUPPLY_VOLTAGE_UNIT;
    }

    public double getIllumination() throws PacketDataScaleValueException {
        return getScaleValue(2, 7, 1, 6, ILLUMINATION_RANGE_MIN, ILLUMINATION_RANGE_MAX, ILLUMINATION_SCALE_MIN, ILLUMINATION_SCALE_MAX);
    }

    public Unit getIlluminationUnit() {
        return ILLUMINATION_UNIT;
    }

    public boolean isPIRStatusOn() {
        return getDataBit(0, 7) == 1;
    }
}
