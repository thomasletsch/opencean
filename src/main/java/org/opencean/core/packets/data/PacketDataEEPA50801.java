/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50801 extends PacketDataEEPA508 {

    public static final double ILLUMINATION_SCALE_MIN = 0;
    public static final double ILLUMINATION_SCALE_MAX = 510;

    public static final double TEMPERATURE_SCALE_MIN = 0;
    public static final double TEMPERATURE_SCALE_MAX = 51;

    public PacketDataEEPA50801(byte[] eepData) {
        super(eepData, ILLUMINATION_SCALE_MIN, ILLUMINATION_SCALE_MAX, TEMPERATURE_SCALE_MIN, TEMPERATURE_SCALE_MAX);
    }

}
