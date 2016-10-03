package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA502_10bit extends PacketDataEEPA502 {

    private static final long TEMPERATURE_RANGE_MIN = 1023;
    private static final long TEMPERATURE_RANGE_MAX = 0;

    public PacketDataEEPA502_10bit(byte[] eepData,
                                   double tempScaleMin, double tempScaleMax) {
        super(eepData, 2, 1, 1, 0, TEMPERATURE_RANGE_MIN, TEMPERATURE_RANGE_MAX, tempScaleMin, tempScaleMax);
    }

}
