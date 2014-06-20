package org.opencean.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BitsTest {

    @Test
    public void getBitFirstBit() {
        byte b = 1;
        assertTrue(Bits.isBitSet(b, 0));
    }

    @Test
    public void getBitSecondBit() {
        byte b = 2;
        assertTrue(Bits.isBitSet(b, 1));
    }

    @Test
    public void getBitFirstBitFalse() {
        byte b = 2;
        assertFalse(Bits.isBitSet(b, 0));
    }

    @Test
    public void getBitSecondBitFalse() {
        byte b = 1;
        assertFalse(Bits.isBitSet(b, 1));
    }

    @Test
    public void setBitFirstBit() {
        byte b = 0;
        assertEquals(1, Bits.setBit(b, 0, true));
    }

    @Test
    public void setBitFirstBitAlreadySet() {
        byte b = 1;
        assertEquals(1, Bits.setBit(b, 0, true));
    }

    @Test
    public void setBitSecondBit() {
        byte b = 0;
        assertEquals(2, Bits.setBit(b, 1, true));
    }

    @Test
    public void setBitSecondBitFalse() {
        byte b = 3;
        assertEquals(1, Bits.setBit(b, 1, false));
    }

    @Test
    public void testGetOneBits() {
        assertEquals((byte) 0xFF, Bits.getSetBits(7, 0));
        assertEquals((byte) 0xFE, Bits.getSetBits(7, 1));
        assertEquals((byte) 0x7F, Bits.getSetBits(6, 0));
        assertEquals((byte) 0x3C, Bits.getSetBits(5, 2));
        assertEquals((byte) 0x80, Bits.getSetBits(7, 7));
        assertEquals((byte) 0x10, Bits.getSetBits(4, 4));
        assertEquals((byte) 0x01, Bits.getSetBits(0, 0));
    }

}
