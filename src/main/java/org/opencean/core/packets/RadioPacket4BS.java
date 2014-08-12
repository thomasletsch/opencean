package org.opencean.core.packets;

import org.opencean.core.utils.Bits;

public class RadioPacket4BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xA5;

    public static final int DATA_LENGTH = 10;

    private byte[] db;

    public RadioPacket4BS() {
        db = new byte[4];
    }

    public RadioPacket4BS(RawPacket rawPacket) {
        super(rawPacket);
    }

    @Override
    protected void parseData() {
        super.parseData();
        byte[] data = payload.getData();
        db = new byte[4];
        db[0] = data[1];
        db[1] = data[2];
        db[2] = data[3];
        db[3] = data[4];
        //db = Arrays.copyOfRange(data, 1, 5);
    }

    public byte getDb0() {
        return db[3];
    }

    private void setDb0(final byte db0) {
        db[3] = db0;
    }

    public byte getDb1() {
        return db[2];
    }

    private void setDb1(final byte db1) {
        db[2] = db1;
    }

    public byte getDb2() {
        return db[1];
    }

    public byte getDb3() {
        return db[0];
    }

    public byte[] getEEPData() {
        return db;
    }

    public void setTestData(byte db0, byte db1) {
        setDb1(db1);
        setDb0(db0);
    }

    public boolean isTeachInMode() {
        return !Bits.isBitSet(getDb0(), 3);
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(", [db0=%02X, db1=%02X, db2=%02X, db3=%02X, teachIn=%s]", getDb0(), getDb1(), getDb2(), getDb3(), isTeachInMode());
    }
}
