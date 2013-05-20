package org.enocean.java.struct;

import static org.junit.Assert.assertEquals;

import org.enocean.java.utils.CRC8;
import org.junit.Test;

public class CRC8Test {

    @Test
    public void testGetValue() {
        byte[] data = new byte[] { 1, 2, 3 };
        CRC8 crc8 = new CRC8();
        crc8.update(data, 0, 3);
        assertEquals(0x48, crc8.getValue());
    }

}
