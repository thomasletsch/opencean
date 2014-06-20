/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opencean.core.packets.data;

import org.opencean.core.utils.Bits;

/**
 *
 * @author rathgeb
 */
public class PacketData {

    protected byte[] data;

    public PacketData(int size) {
        data = new byte[size];
    }

    public PacketData(byte[] data) {
        this.data = data;
    }

    protected int convPosDbToReal(int dbPos) {
        return data.length - 1 - dbPos;
    }

    protected int getDataBit(int db, int bit) {
        return Bits.getBit(data[convPosDbToReal(db)], bit);
    }

    protected long getDataRange(int startDB, int startBit, int endDB, int endBit) {
        // e.g. db3.5 ... db2.7
        assert startDB >= endDB || (startDB == endDB && startBit >= endBit);
        assert startDB <= data.length - 1;

        final int realStartByte = convPosDbToReal(startDB);
        final int realEndByte = convPosDbToReal(endDB);

        return Bits.getBitsFromBytes(data, realStartByte, startBit, realEndByte, endBit);
    }
}
