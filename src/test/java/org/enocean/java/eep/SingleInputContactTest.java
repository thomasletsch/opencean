package org.enocean.java.eep;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.enocean.java.PacketFactory;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.Header;
import org.enocean.java.packets.Payload;
import org.enocean.java.packets.RadioPacket;
import org.enocean.java.packets.RawPacket;
import org.junit.Test;

public class SingleInputContactTest {

    @Test
    public void testParsePacketOpen() {
        RawPacket rawPacket = createRawPacket((byte) 0);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        SingleInputContact sic = new SingleInputContact();
        Map<String, Value> values = sic.parsePacket(basicPacket);
        assertEquals("size", 1, values.size());
        assertEquals(ContactState.OPEN, values.get(SingleInputContact.PARAMETER_ID));
    }

    @Test
    public void testParsePacketClosed() {
        RawPacket rawPacket = createRawPacket((byte) 1);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        SingleInputContact sic = new SingleInputContact();
        Map<String, Value> values = sic.parsePacket(basicPacket);
        assertEquals("size", 1, values.size());
        assertEquals(ContactState.CLOSED, values.get(SingleInputContact.PARAMETER_ID));
    }

    private RawPacket createRawPacket(byte dataByte) {
        Header header = new Header(RadioPacket.PACKET_TYPE, (short) 7, (byte) 0);
        Payload payload = new Payload();
        payload.setData(new byte[] { (byte) 0xD5, dataByte, 0, 0, 0, 0, 0 });
        RawPacket rawPacket = new RawPacket(header, payload);
        return rawPacket;
    }
}
