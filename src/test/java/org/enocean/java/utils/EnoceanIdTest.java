package org.enocean.java.utils;

import static org.junit.Assert.assertEquals;

import org.enocean.java.address.EnoceanId;
import org.junit.Test;

public class EnoceanIdTest {

    @Test
    public void testToString() {
        byte[] id = new byte[] { 0x00, (byte) 0xA1, (byte) 0xb2, (byte) 0xc3 };
        assertEquals("00:A1:B2:C3", new EnoceanId(id).toString());
    }

}
