package org.enocean.java.struct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.enocean.java.CircularByteBuffer;
import org.junit.Before;
import org.junit.Test;

public class PayloadTest {

    private static final byte[] testPayload = new byte[] { (byte) 0xD2, (byte) 0xDD, (byte) 0xDD, (byte) 0xDD, (byte) 0xDD, (byte) 0xDD,
        (byte) 0xDD, (byte) 0xDD, (byte) 0xDD, (byte) 0xDD, 0x00, (byte) 0x80, 0x35, (byte) 0xC4, 0x00, (byte) 0x03, (byte) 0xFF,
        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x4D, (byte) 0x00, (byte) 0x36 };

    private Payload payload;

    @Before
    public void setupTestPayload() {
        CircularByteBuffer buffer = new CircularByteBuffer(2048);
        buffer.put(testPayload);
        Header testHeader = new Header((byte) 1, (short) 15, (byte) 7, (byte) 43);
        payload = Payload.from(testHeader, buffer);
    }

    @Test
    public void testCheckCrc8() {
        payload.checkCrc8();
    }

    @Test
    public void testToBytes() {
        assertEquals("Lenth", testPayload.length, payload.toBytes().length);
    }

    @Test
    public void testToString() {
        String string = payload.toString();
        assertTrue("Contains data", string.contains("data"));
    }

}
