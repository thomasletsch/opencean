package org.enocean.java.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteBitSetTest {

    @Test
    public void testSetBit() {
        ByteBitSet bitSet = new ByteBitSet();
        bitSet.setBit(0, true);
        assertEquals(1, bitSet.getByte());
    }

}
