package org.enocean.java.eep;

public enum Unit {

    DEGREE_CELSIUS("°C");

    private String displayName;

    private Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
