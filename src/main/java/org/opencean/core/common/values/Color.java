package org.opencean.core.common.values;

public class Color implements Value {

    private Integer red;
    private Integer green;
    private Integer blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Object getValue() {
        return Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
    }

    @Override
    public String getDisplayValue() {
        return "red: " + red.toString() + ", green: " + green.toString() + ", blue: " + blue.toString();
    }

    @Override
    public String toString() {
        return getDisplayValue();
    }

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int  getBlue() {
		return blue;
	}
}
