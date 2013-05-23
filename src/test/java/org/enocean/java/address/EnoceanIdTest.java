package org.enocean.java.address;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnoceanIdTest {

    @Test
    public void fromString() {
        assertEquals(new EnoceanId(new byte[] { (byte) 0xAA, 0x01, (byte) 0xFF, 0x00 }), EnoceanId.fromString("AA:01:FF:00"));
    }

}
