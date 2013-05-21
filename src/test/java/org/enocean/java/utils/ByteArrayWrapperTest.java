package org.enocean.java.utils;

import static org.junit.Assert.assertEquals;

import org.enocean.java.packets.ByteArrayWrapper;
import org.junit.Test;

public class ByteArrayWrapperTest {

    @Test
    public void testSetInt() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[4]);
        wrapper.setInt(10, 0);
        byte[] array = wrapper.getArray();
        assertEquals("length", 4, array.length);
        assertEquals(0, array[0]);
        assertEquals(0, array[1]);
        assertEquals(0, array[2]);
        assertEquals(10, array[3]);
    }

    @Test
    public void testAddInt() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addInt(10);
        byte[] array = wrapper.getArray();
        assertEquals("length", 4, array.length);
        assertEquals(0, array[0]);
        assertEquals(0, array[1]);
        assertEquals(0, array[2]);
        assertEquals(10, array[3]);
    }

    @Test
    public void testAddShort() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addShort((short) 10);
        byte[] array = wrapper.getArray();
        assertEquals("length", 2, array.length);
        assertEquals(0, array[0]);
        assertEquals(10, array[1]);
    }

    @Test
    public void testSetByte() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[1]);
        wrapper.setByte((byte) 10, 0);
        byte[] array = wrapper.getArray();
        assertEquals("length", 1, array.length);
        assertEquals(10, array[0]);
    }

    @Test
    public void testAddBytes() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addBytes(new byte[] { 1, 2, 3 });
        byte[] array = wrapper.getArray();
        assertEquals("length", 3, array.length);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testAddByte() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addByte((byte) 10);
        byte[] array = wrapper.getArray();
        assertEquals("length", 1, array.length);
        assertEquals(10, array[0]);
    }

}
