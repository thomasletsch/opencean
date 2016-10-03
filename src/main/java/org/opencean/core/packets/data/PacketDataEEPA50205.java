package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50205 extends PacketDataEEPA502_8bit {

    private static final double TEMPERATURE_SCALE_MIN = 0;
    private static final double TEMPERATURE_SCALE_MAX = 40;

    public PacketDataEEPA50205(byte[] eepData) {
        super(eepData, TEMPERATURE_SCALE_MIN, TEMPERATURE_SCALE_MAX);
    }

}
