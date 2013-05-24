package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacketRPS;
import org.enocean.java.utils.Bits;

public class RockerSwitch implements EEPParser {

    public static final EEPId EEP_ID = new EEPId("F6:02:01");
    public static final String PARAMETER_ID = "BUTTON_";
    public static final String B_DOWN = "B_DOWN";
    public static final String B_UP = "B_UP";
    public static final String A_DOWN = "A_DOWN";
    public static final String A_UP = "A_UP";

    private NUState nu;
    private T21State t21;

    private ButtonState buttonAUp;
    private ButtonState buttonADown;
    private ButtonState buttonBUp;
    private ButtonState buttonBDown;
    private EnergyBowState energyBow;

    @Override
    public Map<String, Value> parsePacket(BasicPacket packet) {
        Map<String, Value> map = new HashMap<String, Value>();
        if (packet instanceof RadioPacketRPS) {
            RadioPacketRPS radioPacketRPS = (RadioPacketRPS) packet;
            byte dataByte = radioPacketRPS.getDataByte();
            energyBow = EnergyBowState.values()[(dataByte & 0x10) >> 4];
            if (energyBow.equals(EnergyBowState.RELEASED)) {
                releaseButton();
            } else {
                resetButtons();
                byte rocker1 = (byte) ((dataByte & 0xE0) >> 5);
                parseButtonStates(rocker1);
                boolean secondAction = Bits.getBit(dataByte, 0);
                if (secondAction) {
                    byte rocker2 = (byte) ((dataByte & 0x0E) >> 1);
                    parseButtonStates(rocker2);
                }
            }
            if (buttonAUp != null) {
                map.put(PARAMETER_ID + A_UP, buttonAUp);
            }
            if (buttonADown != null) {
                map.put(PARAMETER_ID + A_DOWN, buttonADown);
            }
            if (buttonBUp != null) {
                map.put(PARAMETER_ID + B_UP, buttonBUp);
            }
            if (buttonBDown != null) {
                map.put(PARAMETER_ID + B_DOWN, buttonBDown);
            }

            byte statusByte = radioPacketRPS.getStatusByte();
            nu = NUState.values()[(statusByte & 0x10) >> 4];
            t21 = T21State.values()[(statusByte & 0x20) >> 5];
        }
        return map;
    }

    private void resetButtons() {
        buttonAUp = null;
        buttonBUp = null;
        buttonADown = null;
        buttonBDown = null;
    }

    private void releaseButton() {
        if (buttonAUp != null) {
            buttonAUp = ButtonState.RELEASED;
        }
        if (buttonADown != null) {
            buttonADown = ButtonState.RELEASED;
        }
        if (buttonBUp != null) {
            buttonBUp = ButtonState.RELEASED;
        }
        if (buttonBDown != null) {
            buttonBDown = ButtonState.RELEASED;
        }

    }

    private void parseButtonStates(byte channelA) {
        switch (channelA) {
        case 0:
            buttonADown = ButtonState.PRESSED;
            break;
        case 1:
            buttonAUp = ButtonState.PRESSED;
            break;
        case 2:
            buttonBDown = ButtonState.PRESSED;
            break;
        case 3:
            buttonBUp = ButtonState.PRESSED;
            break;

        default:
            break;
        }

    }

    public enum NUState {
        UNASSIGNEDMESSAGE(0), NORMALMESSAGE(1);

        private final int enumvalue;

        NUState(int value) {
            this.enumvalue = value;
        }

        NUState(byte value) {
            this.enumvalue = value;
        }

        public byte toByte() {
            return (byte) enumvalue;
        }

        @Override
        public String toString() {
            return (enumvalue == 0) ? "Unassigned" : "Normal";
        }
    }

    public enum T21State {
        PTMType1(0), PTMType2(1);

        private final int enumvalue;

        T21State(int value) {
            this.enumvalue = value;
        }

        T21State(byte value) {
            this.enumvalue = value;
        }

        public byte toByte() {
            return (byte) enumvalue;
        }

        @Override
        public String toString() {
            return (enumvalue == 0) ? "PTM Type 1" : "PTM Type 2";
        }
    }
}
