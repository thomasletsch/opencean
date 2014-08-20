package org.opencean.core.common.values;

public enum WindowHandleState implements Value {
	UP, DOWN, MIDDLE;

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
        return (this.equals(UP) ? "Up" : this.equals(MIDDLE) ? "Middle" : "Down");
    }
}