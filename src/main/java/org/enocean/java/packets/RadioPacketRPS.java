package org.enocean.java.packets;

public class RadioPacketRPS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xF6;

    private NUState nu;
    private T21State t21;

    private RockerActionState rocker1;
    private RockerActionState rocker2;
    private EnergyBowState energyBow;
    private SecondActionState secondAction;

    public RadioPacketRPS(RawPacket rawPacket) {
        super(rawPacket);
    }

    @Override
    public void parseData() {
        super.parseData();
        byte dataByte = payload.getData()[1];
        secondAction = SecondActionState.values()[(dataByte & 0x01)];
        rocker2 = RockerActionState.values()[(dataByte & 0x0E) >> 1];
        energyBow = EnergyBowState.values()[(dataByte & 0x10) >> 4];
        rocker1 = RockerActionState.values()[(dataByte & 0xE0) >> 5];

        byte statusByte = payload.getData()[6];
        nu = NUState.values()[(statusByte & 0x10) >> 4];
        t21 = T21State.values()[(statusByte & 0x20) >> 5];
    }

    @Override
    public ParameterMap getAllParameterValues() {
        ParameterMap values = super.getAllParameterValues();
        values.put("rocker#1", rocker1.toAction());
        values.put("rocker#2", rocker2.toAction());
        return values;
    }

    @Override
    public String toString() {
        // The coding is a little strange. but only on button press do you get
        // info on what button is pressed. on release not.

        // return "RPS " + getSenderId() +
        // ", Energy Bow " + energyBow.toString() +
        // ", Rocker " + rocker1.toString() +
        // ( secondAction == SecondActionState.SECONDACTIONVALID ? ", Second " +
        // rocker2.toString() : "");

        if (energyBow == EnergyBowState.RELEASED) {
            return "RPS " + getSenderId() + ", Energy Bow " + energyBow.toString();

        } else {
            return "RPS " + getSenderId() + ", Rocker " + rocker1.toString()
                    + (secondAction == SecondActionState.SECONDACTIONVALID ? ", Second " + rocker2.toString() : "");

        }

    }

    public enum SecondActionState {
        NOSECONDACTION(0), SECONDACTIONVALID(1);

        private final int enumvalue;

        SecondActionState(int value) {
            this.enumvalue = value;
        }

        SecondActionState(byte value) {
            this.enumvalue = value;
        }

        public byte toByte() {
            return (byte) enumvalue;
        }

        @Override
        public String toString() {
            return (enumvalue == 0) ? "No Second Action" : "Second Action Valid";
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
