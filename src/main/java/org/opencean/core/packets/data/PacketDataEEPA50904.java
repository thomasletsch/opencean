package org.opencean.core.packets.data;

import org.opencean.core.common.values.Unit;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50904 extends PacketData4BS {

    public static final long HUMIDITY_RANGE_MIN = 0;
    public static final long HUMIDITY_RANGE_MAX = 200;
    public static final double HUMIDITY_SCALE_MIN = 0;
    public static final double HUMIDITY_SCALE_MAX = 100;
    public static Unit HUMIDITY_UNIT = Unit.HUMIDITY;
    
    public static final long CONCENTRATION_RANGE_MIN = 0;
    public static final long CONCENTRATION_RANGE_MAX = 255;
    public static final double CONCENTRATION_SCALE_MIN = 0;
    public static final double CONCENTRATION_SCALE_MAX = 2550;
    public static final Unit CONCENTRATION_UNIT = Unit.PPM;
    
    public static final long TEMPERATURE_RANGE_MIN = 0;
    public static final long TEMPERATURE_RANGE_MAX = 255;
    public static final double TEMPERATURE_SCALE_MIN = 0;
    public static final double TEMPERATURE_SCALE_MAX = 51;
    public static final Unit TEMPERATURE_UNIT = Unit.DEGREE_CELSIUS;
    
    public PacketDataEEPA50904(byte[] eepData) {
        super(eepData);
    }
    
    public double getHumidity() throws PacketDataScaleValueException {
        return getScaleValue(3, 7, 3, 0, HUMIDITY_RANGE_MIN, HUMIDITY_RANGE_MAX, HUMIDITY_SCALE_MIN, HUMIDITY_SCALE_MAX);
    }
    
    public Unit getHumidityUnit() {
        return HUMIDITY_UNIT;
    }
    
    public double getConcentration() throws PacketDataScaleValueException {
        return getScaleValue(2, 7, 2, 0, CONCENTRATION_RANGE_MIN, CONCENTRATION_RANGE_MAX, CONCENTRATION_SCALE_MIN, CONCENTRATION_SCALE_MAX);
    }
    
    public Unit getConcentrationUnit() {
        return CONCENTRATION_UNIT;
    }
    
    public double getTemperature() throws PacketDataScaleValueException {
        return getScaleValue(1, 7, 1, 0, TEMPERATURE_RANGE_MIN, TEMPERATURE_RANGE_MAX, TEMPERATURE_SCALE_MIN, TEMPERATURE_SCALE_MAX);
    }
    
    public Unit getTemperatureUnit() {
        return TEMPERATURE_UNIT;
    }
    
    public boolean isHumiditySensorAvailable() {
        return getDataBit(0, 2) == 1;
    }
    
    public boolean isTemperatureSensorAvailable() {
        return getDataBit(0, 1) == 1;
    }

}
