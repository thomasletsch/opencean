package org.enocean.java.common.values;

public enum Unit {

    DEGREE_CELSIUS("°C"), LUX("lx"), WATT("W"), VOLTAGE("Volt");

    private String displayName;

    private Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
