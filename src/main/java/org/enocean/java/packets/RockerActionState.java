package org.enocean.java.packets;

public enum RockerActionState {
    Button_A_On(0),
    Button_A_Off(1),
    Button_B_On(2),
    Button_B_Off(3);

    private final int enumvalue;

    RockerActionState(int value) {
        this.enumvalue = value;
    }

    RockerActionState(byte value) {
        this.enumvalue = value;
    }

    public byte toByte() {
        return (byte) enumvalue;
    }

    public boolean toAction() {
        switch ( enumvalue){
        case 0:
        case 2: return true;
        case 1:
        case 3: return false;
        default: return false;
        }
    }

    @Override
    public String toString() {
        switch ( enumvalue){
        case 0: return "Button A On";
        case 1: return "Button A Off";
        case 2: return "Button B On";
        case 3: return "Button B Off";
        default: return "Unknown";
        }
    }
}