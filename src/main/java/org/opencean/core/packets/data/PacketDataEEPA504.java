package org.opencean.core.packets.data;

import org.opencean.core.common.values.Unit;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA504 extends PacketData4BS {

    public static final long HUMIDITY_RANGE_MIN = 0;
    public static final long HUMIDITY_RANGE_MAX = 250;
    public static final Unit HUMIDITY_UNIT = Unit.HUMIDITY;

    public static final long TEMPERATURE_RANGE_MIN = 0;
    public static final long TEMPERATURE_RANGE_MAX = 250;
    public static final Unit TEMPERATURE_UNIT = Unit.DEGREE_CELSIUS;

    private final double humidityScaleMin;
    private final double humidityScaleMax;
    private final double temperatureScaleMin;
    private final double temperatureScaleMax;

    public PacketDataEEPA504(byte[] eepData,
                             double humidityScaleMin, double humidityScaleMax,
                             double temperatureScaleMin, double temperatureScaleMax) {
        super(eepData);
        this.humidityScaleMin = humidityScaleMin;
        this.humidityScaleMax = humidityScaleMax;
        this.temperatureScaleMin = temperatureScaleMin;
        this.temperatureScaleMax = temperatureScaleMax;
    }

    public double getHumidity() {
        return getScaleValue(2, 7, 2, 0, HUMIDITY_RANGE_MIN, HUMIDITY_RANGE_MAX, humidityScaleMin, humidityScaleMax);
    }

    public Unit getHumidityUnit() {
        return HUMIDITY_UNIT;
    }

    public double getTemperature() {
        return getScaleValue(1, 7, 1, 0, TEMPERATURE_RANGE_MIN, TEMPERATURE_RANGE_MAX, temperatureScaleMin, temperatureScaleMax);
    }

    public Unit getTemperatureUnit() {
        return TEMPERATURE_UNIT;
    }

    public boolean isTemperatureAvailable() {
        return getDataBit(0, 1) == 1;
    }

}
