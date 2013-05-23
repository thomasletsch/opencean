package org.enocean.java.eep;

public class UndefinedValue implements Value {

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getDisplayValue() {
        return "UNDEFINED";
    }
}
