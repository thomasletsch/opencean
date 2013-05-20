package org.enocean.java.struct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.enocean.java.CircularByteBuffer;
import org.junit.Before;
import org.junit.Test;

public class HeaderTest {

    public static final byte[] testHeader = new byte[] { 0x00, 0x0F, 0x07, 0x01, 0x2B };
    private Header header;

    @Before
    public void setupTestHeader() {
        CircularByteBuffer buffer = new CircularByteBuffer(2048);
        buffer.put(testHeader);
        header = Header.from(buffer);
    }

    @Test
    public void testToBytes() {
        assertEquals("Lenth", testHeader.length + 1, header.toBytes().length);
    }

    @Test
    public void testIsValid() {
        assertTrue("isValid", header.isValid());
    }

    @Test
    public void testCheckCrc8() {
        header.checkCrc8();
    }

    @Test
    public void testGetPacketType() {
        assertEquals(BasicPacket.PACKET_TYPE_RADIO, header.getPacketType());
    }

    @Test
    public void testGetDataLength() {
        assertEquals(15, header.getDataLength());
    }

    @Test
    public void testGetOptionalDataLength() {
        assertEquals(7, header.getOptionalDataLength());
    }

}
