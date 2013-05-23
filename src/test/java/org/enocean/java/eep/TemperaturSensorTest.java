package org.enocean.java.eep;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.enocean.java.packets.RadioPacket4BS;
import org.junit.Test;

public class TemperaturSensorTest {

    @Test
    public void readPacketMax() {
        EEPParser sensor = new TemperaturSensor(0, 40);
        RadioPacket4BS packet = new RadioPacket4BS();
        packet.setDb1((byte) 255);
        assertEquals(new BigDecimal(00), sensor.parsePacket(packet).getValue());
    }

    @Test
    public void readPacketMin() {
        EEPParser sensor = new TemperaturSensor(0, 40);
        RadioPacket4BS packet = new RadioPacket4BS();
        packet.setDb1((byte) 0);
        assertEquals(new BigDecimal(40), sensor.parsePacket(packet).getValue());
    }

    @Test
    public void readPacket112() {
        EEPParser sensor = new TemperaturSensor(0, 40);
        RadioPacket4BS packet = new RadioPacket4BS();
        packet.setDb1((byte) 112);
        assertEquals(new BigDecimal("22.4"), sensor.parsePacket(packet).getValue());
    }

}
