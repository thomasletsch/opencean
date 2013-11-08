package org.enocean.java.eep;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.enocean.java.PacketFactory;
import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.Parameter;
import org.enocean.java.common.values.ButtonState;
import org.enocean.java.common.values.ContactState;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.Header;
import org.enocean.java.packets.Payload;
import org.enocean.java.packets.RadioPacket;
import org.enocean.java.packets.RawPacket;
import org.junit.Assert;
import org.junit.Test;

/**
 * "55, 00, 05, 07, 01, AC, 00, 00, D9, AB, 00, 01, FF, FF, FF, FF, 50, 00, B5"
 * 
 * @author Thomas Letsch (contact@thomas-letsch.de)
 * 
 */
public class SingleInputContactTest {

    @Test
    public void testParsePacketOpen() {
        RawPacket rawPacket = createRawPacket((byte) 8);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        SingleInputContact sic = new SingleInputContact();
        Map<EnoceanParameterAddress, Value> values = sic.parsePacket(basicPacket);
        assertEquals("size", 2, values.size());
        assertContainsReturnValue(values, ContactState.OPEN, Parameter.CONTACT_STATE.name());
        assertContainsReturnValue(values, ButtonState.RELEASED, Parameter.LEARN_BUTTON.name());
    }

    @Test
    public void testParsePacketClosed() {
        RawPacket rawPacket = createRawPacket((byte) 9);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        SingleInputContact sic = new SingleInputContact();
        Map<EnoceanParameterAddress, Value> values = sic.parsePacket(basicPacket);
        assertEquals("size", 2, values.size());
        assertContainsReturnValue(values, ContactState.CLOSED, Parameter.CONTACT_STATE.name());
        assertContainsReturnValue(values, ButtonState.RELEASED, Parameter.LEARN_BUTTON.name());
    }

    private void assertContainsReturnValue(Map<EnoceanParameterAddress, Value> values, Value expectedValue, String expectedParameterId) {
        for (Entry<EnoceanParameterAddress, Value> entry : values.entrySet()) {
            if (entry.getKey().getParameterId().equals(expectedParameterId)) {
                assertEquals(expectedValue, entry.getValue());
                return;
            }
        }
        Assert.fail();
    }

    private RawPacket createRawPacket(byte dataByte) {
        Header header = new Header(RadioPacket.PACKET_TYPE, (short) 7, (byte) 0);
        Payload payload = new Payload();
        payload.setData(new byte[] { (byte) 0xD5, dataByte, 0, 0, 0, 0, 0 });
        RawPacket rawPacket = new RawPacket(header, payload);
        return rawPacket;
    }
}
