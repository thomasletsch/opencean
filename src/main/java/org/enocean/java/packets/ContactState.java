package org.enocean.java.packets;

public enum ContactState {
    OPEN(0),
    CLOSED(1);

    private final int enumvalue;

    ContactState(int value) {
        this.enumvalue = value;
    }

    ContactState(byte value) {
        this.enumvalue = value;
    }

    public byte toByte() {
        return (byte) enumvalue;
    }

    @Override
    public String toString() {
        return ( enumvalue == 0 ) ? "Open" : "Closed";
    }
}