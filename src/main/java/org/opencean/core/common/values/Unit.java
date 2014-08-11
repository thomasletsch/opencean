package org.opencean.core.common.values;

public enum Unit {

    DEGREE_CELSIUS("°C"), LUX("lx"), HUMIDITY("%"), PPB("ppb - parts per billion"), PPM("ppm - parts per million"), VOLTAGE("Volt"), WATT(
            "W"), HOURS("hours"), WATTHOURS("Wh"), AMPERE("A"), VOLT("V"), POINTS("points");

    private String displayName;

    private Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
