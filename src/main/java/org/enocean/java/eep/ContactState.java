package org.enocean.java.eep;

public enum ContactState implements Value {
    OPEN(0), CLOSED(1);

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
        return getDisplayValue();
    }

    @Override
    public Object getValue() {
        return name();
    }

    @Override
    public String getDisplayValue() {
        return (this.equals(OPEN) ? "Open" : "Closed");
    }
}