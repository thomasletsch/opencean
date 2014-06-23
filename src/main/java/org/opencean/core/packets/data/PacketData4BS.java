/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opencean.core.packets.data;

/**
 *
 * @author rathgeb
 */
public class PacketData4BS extends PacketData {

    public PacketData4BS() {
        super(4);
    }

    public PacketData4BS(byte[] data) {
        super(data);
        assert data.length == 4;
    }

    public boolean isTeachIn() {
        return getDataBit(0, 3) == 0;
    }
}
