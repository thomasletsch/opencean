package org.enocean.java.packets;

public enum EnergyBowState {
    RELEASED(0),
    PRESSED(1);

    private final int enumvalue;

    EnergyBowState(int value) {
        this.enumvalue = value;
    }

    EnergyBowState(byte value) {
        this.enumvalue = value;
    }

    public byte toByte() {
        return (byte) enumvalue;
    }

    @Override
    public String toString() {
        return ( enumvalue == 0 ) ? "Released" : "Pressed";
    }
}