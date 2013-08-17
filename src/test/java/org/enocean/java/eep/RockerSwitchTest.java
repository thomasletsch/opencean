package org.enocean.java.eep;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.enocean.java.PacketFactory;
import org.enocean.java.address.EnoceanId;
import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.Header;
import org.enocean.java.packets.Payload;
import org.enocean.java.packets.RadioPacket;
import org.enocean.java.packets.RadioPacketRPS;
import org.enocean.java.packets.RawPacket;
import org.enocean.java.utils.Bits;
import org.junit.Before;
import org.junit.Test;

public class RockerSwitchTest {

    private RockerSwitch rockerSwitch;

    @Before
    public void createRockerSwitch() {
        rockerSwitch = new RockerSwitch();
    }

    @Test
    public void testAIPressed() {
        Map<EnoceanParameterAddress, Value> values = pressAI();
        assertEquals("size", 1, values.size());
        Entry<EnoceanParameterAddress, Value> value = values.entrySet().iterator().next();
        assertEquals(ButtonState.PRESSED, value.getValue());
        assertEquals(new EnoceanParameterAddress(EnoceanId.fromString("00:00:00:00"), RockerSwitch.CHANNEL_A, "I"), value.getKey());
    }

    @Test
    public void testAIReleased() {
        pressAI();
        byte dataByte = 0;
        byte statusByte = 0;
        statusByte = Bits.setBit(statusByte, 5, false); // T21
        statusByte = Bits.setBit(statusByte, 4, true); // NU
        RawPacket rawPacket = createRawPacket(dataByte, statusByte);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = rockerSwitch.parsePacket(basicPacket);
        assertEquals("size", 1, values.size());
        Entry<EnoceanParameterAddress, Value> value = values.entrySet().iterator().next();
        assertEquals(ButtonState.RELEASED, value.getValue());
        assertEquals(new EnoceanParameterAddress(EnoceanId.fromString("00:00:00:00"), RockerSwitch.CHANNEL_A, "I"), value.getKey());
    }

    private Map<EnoceanParameterAddress, Value> pressAI() {
        byte dataByte = 0;
        dataByte = Bits.setBit(dataByte, 4, true); // PRESSED
        byte statusByte = 0;
        statusByte = Bits.setBit(statusByte, 5, false); // T21
        statusByte = Bits.setBit(statusByte, 4, true); // NU
        RawPacket rawPacket = createRawPacket(dataByte, statusByte);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = rockerSwitch.parsePacket(basicPacket);
        return values;
    }

    private RawPacket createRawPacket(byte dataByte, byte statusByte) {
        Header header = new Header(RadioPacket.PACKET_TYPE, (short) 7, (byte) 0);
        Payload payload = new Payload();
        payload.setData(new byte[] { RadioPacketRPS.RADIO_TYPE, dataByte, 0, 0, 0, 0, statusByte });
        RawPacket rawPacket = new RawPacket(header, payload);
        return rawPacket;
    }

}
