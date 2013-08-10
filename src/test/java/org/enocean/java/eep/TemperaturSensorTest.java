package org.enocean.java.eep;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.packets.RadioPacket4BS;
import org.junit.Test;

public class TemperaturSensorTest {

    @Test
    public void readPacketMax() {
        EEPParser sensor = new TemperaturSensor(0, 40);
        RadioPacket4BS packet = new RadioPacket4BS();
        packet.setDb1((byte) 255);
        Map<EnoceanParameterAddress, Value> values = sensor.parsePacket(packet);
        assertEquals("size", 1, values.size());
        assertEquals(new BigDecimal(00), values.entrySet().iterator().next().getValue().getValue());
    }

    @Test
    public void readPacketMin() {
        EEPParser sensor = new TemperaturSensor(0, 40);
        RadioPacket4BS packet = new RadioPacket4BS();
        packet.setDb1((byte) 0);
        Map<EnoceanParameterAddress, Value> values = sensor.parsePacket(packet);
        assertEquals("size", 1, values.size());
        assertEquals(new BigDecimal(40), values.entrySet().iterator().next().getValue().getValue());
    }

    @Test
    public void readPacket112() {
        EEPParser sensor = new TemperaturSensor(0, 40);
        RadioPacket4BS packet = new RadioPacket4BS();
        packet.setDb1((byte) 112);
        Map<EnoceanParameterAddress, Value> values = sensor.parsePacket(packet);
        assertEquals("size", 1, values.size());
        assertEquals(new BigDecimal("22.4"), values.entrySet().iterator().next().getValue().getValue());
    }

}
