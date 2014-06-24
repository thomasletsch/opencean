package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50401 extends PacketDataEEPA504 {

    public static final double HUMIDITY_SCALE_MIN = 0;
    public static final double HUMIDITY_SCALE_MAX = 100;

    public static final double TEMPERATURE_SCALE_MIN = 0;
    public static final double TEMPERATURE_SCALE_MAX = 40;

    public PacketDataEEPA50401(byte[] eepData) {
        super(eepData, HUMIDITY_SCALE_MIN, HUMIDITY_SCALE_MAX, TEMPERATURE_SCALE_MIN, TEMPERATURE_SCALE_MAX);
    }

}
