package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacketRPS;
import org.enocean.java.utils.Bits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RockerSwitch implements EEPParser {

    private static Logger logger = LoggerFactory.getLogger(RockerSwitch.class);

    public static final EEPId EEP_ID_1 = new EEPId("F6:02:01");
    public static final EEPId EEP_ID_2 = new EEPId("F6:02:01");
    public static final String BUTTON_I = "I";
    public static final String BUTTON_O = "O";
    public static final String PRESSED = "PRESSED";
    public static final String RELEASED = "RELEASED";
    public static final String CHANNEL_A = "A";
    public static final String CHANNEL_B = "B";

    private NUState nu;
    private T21State t21;

    private ButtonState buttonAO;
    private ButtonState buttonAI;
    private ButtonState buttonBO;
    private ButtonState buttonBI;
    private EnergyBowState energyBow;

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacketRPS) {
            RadioPacketRPS radioPacketRPS = (RadioPacketRPS) packet;
            byte statusByte = radioPacketRPS.getStatus();
            byte dataByte = radioPacketRPS.getDataByte();
            energyBow = EnergyBowState.values()[(dataByte & 0x10) >> 4];
            nu = NUState.values()[(statusByte & 0x10) >> 4];
            t21 = T21State.values()[(statusByte & 0x20) >> 5];
            if (energyBow.equals(EnergyBowState.RELEASED)) {
                releaseButton();
                addButtonStateToParameters(map, radioPacketRPS);
            } else {
                if (NUState.UNASSIGNEDMESSAGE.equals(nu)) {
                    logger.info("NU = 0 => unassigned pressed button message received. Not supported!");
                    return map;
                }
                resetButtons();
                byte rocker1 = (byte) ((dataByte & 0xE0) >> 5);
                parseButtonStates(rocker1);
                addButtonStateToParameters(map, radioPacketRPS);
                boolean secondAction = Bits.isBitSet(dataByte, 0);
                if (secondAction) {
                    resetButtons();
                    byte rocker2 = (byte) ((dataByte & 0x0E) >> 1);
                    parseButtonStates(rocker2);
                    addButtonStateToParameters(map, radioPacketRPS);
                }
            }

        }
        return map;
    }

    private void addButtonStateToParameters(Map<EnoceanParameterAddress, Value> map, RadioPacketRPS radioPacketRPS) {
        if (buttonAO != null) {
            map.put(new EnoceanParameterAddress(radioPacketRPS.getSenderId(), CHANNEL_A, BUTTON_O), buttonAO);
        }
        if (buttonAI != null) {
            map.put(new EnoceanParameterAddress(radioPacketRPS.getSenderId(), CHANNEL_A, BUTTON_I), buttonAI);
        }
        if (buttonBO != null) {
            map.put(new EnoceanParameterAddress(radioPacketRPS.getSenderId(), CHANNEL_B, BUTTON_O), buttonBO);
        }
        if (buttonBI != null) {
            map.put(new EnoceanParameterAddress(radioPacketRPS.getSenderId(), CHANNEL_B, BUTTON_I), buttonBI);
        }
    }

    private void resetButtons() {
        buttonAO = null;
        buttonBO = null;
        buttonAI = null;
        buttonBI = null;
    }

    private void releaseButton() {
        if (buttonAO != null) {
            buttonAO = ButtonState.RELEASED;
        }
        if (buttonAI != null) {
            buttonAI = ButtonState.RELEASED;
        }
        if (buttonBO != null) {
            buttonBO = ButtonState.RELEASED;
        }
        if (buttonBI != null) {
            buttonBI = ButtonState.RELEASED;
        }

    }

    private void parseButtonStates(byte channelA) {
        switch (channelA) {
        case 0:
            buttonAI = ButtonState.PRESSED;
            break;
        case 1:
            buttonAO = ButtonState.PRESSED;
            break;
        case 2:
            buttonBI = ButtonState.PRESSED;
            break;
        case 3:
            buttonBO = ButtonState.PRESSED;
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
