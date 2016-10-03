package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50220 extends PacketDataEEPA502_10bit {
    
    private static final double TEMPERATURE_SCALE_MIN = -10;
    private static final double TEMPERATURE_SCALE_MAX = 41.2;
    
    public PacketDataEEPA50220(byte[] eepData) {
        super(eepData, TEMPERATURE_SCALE_MIN, TEMPERATURE_SCALE_MAX);
    }
}
