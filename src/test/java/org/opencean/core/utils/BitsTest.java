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

    @Test
    public void testGetBitsFromByte() {
        assertEquals((byte) 0x7F, Bits.getBitsFromByte((byte) 0xFF, 7, 1, true));
        assertEquals((byte) 0xFE, Bits.getBitsFromByte((byte) 0xFF, 7, 1, false));
        assertEquals((byte) 0x01, Bits.getBitsFromByte((byte) 0xFE, 7, 7, true));
        assertEquals((byte) 0x80, Bits.getBitsFromByte((byte) 0xFE, 7, 7, false));
        assertEquals((byte) 0x0F, Bits.getBitsFromByte((byte) 0xFE, 5, 2, true));
        assertEquals((byte) 0x3C, Bits.getBitsFromByte((byte) 0xFE, 5, 2, false));
        assertEquals((byte) 0x01, Bits.getBitsFromByte((byte) 0x03, 1, 1, true));
        assertEquals((byte) 0x02, Bits.getBitsFromByte((byte) 0x03, 1, 1, false));
    }

    @Test
    public void testGetBitsInRange() {
        /*
         * 0b10110110 = 0xB6
         * 0b01001110 = 0x4E
         */

        final byte[] testInput = {(byte) 0xB6, (byte) 0x4E};

        assertEquals(0xB64E, Bits.getBitsFromBytes(testInput, 0, 7, 1, 0));
        assertEquals(0xB6, Bits.getBitsFromBytes(testInput, 0, 7, 0, 0));
        assertEquals(0x4E, Bits.getBitsFromBytes(testInput, 1, 7, 1, 0));
        assertEquals(0x64, Bits.getBitsFromBytes(testInput, 0, 3, 1, 4));
        assertEquals(0x0B, Bits.getBitsFromBytes(testInput, 0, 7, 0, 4));
        assertEquals(0x06, Bits.getBitsFromBytes(testInput, 0, 3, 0, 0));
        assertEquals(0x04, Bits.getBitsFromBytes(testInput, 1, 7, 1, 4));
        assertEquals(0x0E, Bits.getBitsFromBytes(testInput, 1, 3, 1, 0));
    }

    @Test
    public void testSetBitsOfBytes() {
        byte[] data = new byte[3];

        Bits.setBitsOfBytes(0xAB, data, 0, 7, 0, 0);
        Bits.setBitsOfBytes(0xCD, data, 1, 7, 1, 0);
        Bits.setBitsOfBytes(0xEF, data, 2, 7, 2, 0);
        assertEquals((byte) 0xAB, data[0]);
        assertEquals((byte) 0xCD, data[1]);
        assertEquals((byte) 0xEF, data[2]);

        Bits.setBitsOfBytes(0x01, data, 0, 7, 0, 4);
        Bits.setBitsOfBytes(0x02, data, 0, 3, 0, 0);
        Bits.setBitsOfBytes(0x03, data, 1, 7, 1, 4);
        Bits.setBitsOfBytes(0x04, data, 1, 3, 1, 0);
        Bits.setBitsOfBytes(0x05, data, 2, 7, 2, 4);
        Bits.setBitsOfBytes(0x06, data, 2, 3, 2, 0);
        assertEquals((byte) 0x12, data[0]);
        assertEquals((byte) 0x34, data[1]);
        assertEquals((byte) 0x56, data[2]);

        Bits.setBitsOfBytes(0x4F5E6D, data, 0, 7, 2, 0);
        assertEquals((byte) 0x4F, data[0]);
        assertEquals((byte) 0x5E, data[1]);
        assertEquals((byte) 0x6D, data[2]);
        Bits.setBitsOfBytes(0x00, data, 1, 3, 2, 4);
        assertEquals((byte) 0x4F, data[0]);
        assertEquals((byte) 0x50, data[1]);
        assertEquals((byte) 0x0D, data[2]);

    }
}
