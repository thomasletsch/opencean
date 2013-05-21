package org.enocean.java.packets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoteCommand4BSTeachInTest {

    @Test
    public void toBytes() {
        RemoteCommand4BSTeachIn teachIn = new RemoteCommand4BSTeachIn();
        byte[] bytes = teachIn.toBytes();
        assertEquals("Length", 1 + 5 + 6, bytes.length);
    }

}
