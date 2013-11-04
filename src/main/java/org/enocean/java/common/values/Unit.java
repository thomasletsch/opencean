package org.enocean.java.common.values;

public enum Unit {

    DEGREE_CELSIUS("°C"), LUX("lx"), HUMIDITY("%"), PPB("ppb"), PPM("ppm"), VOLTAGE("Volt"), WATT("W");

    private String displayName;

    private Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
