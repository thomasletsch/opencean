package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA502_8bit extends PacketDataEEPA502 {

    private static final long TEMPERATURE_RANGE_MIN = 255;
    private static final long TEMPERATURE_RANGE_MAX = 0;

    public PacketDataEEPA502_8bit(byte[] eepData,
                                  double tempScaleMin, double tempScaleMax) {
        super(eepData, 1, 7, 1, 0, TEMPERATURE_RANGE_MIN, TEMPERATURE_RANGE_MAX, tempScaleMin, tempScaleMax);
    }

}
