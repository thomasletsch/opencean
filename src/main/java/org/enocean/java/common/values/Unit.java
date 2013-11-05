package org.enocean.java.common.values;

public enum Unit {

    DEGREE_CELSIUS("°C"), LUX("lx"), HUMIDITY("%"), PPB("ppb - parts per billion"), PPM("ppm - parts per million"), VOLTAGE("Volt"), WATT(
            "W");

    private String displayName;

    private Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
