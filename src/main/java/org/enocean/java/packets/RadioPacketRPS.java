package org.enocean.java.packets;

public class RadioPacketRPS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xF6;


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




    public enum EnergyBowState {
        RELEASED(0),
        PRESSED(1);

        private final int enumvalue;

        EnergyBowState(int value) {
            this.enumvalue = value;
        }

        EnergyBowState(byte value) {
            this.enumvalue = value;
        }

        public byte toByte() {
            return (byte) enumvalue;
        }

        @Override
        public String toString() {
            return ( enumvalue == 0 ) ? "Released" : "Pressed";
        }
    }



    public enum SecondActionState {
        NOSECONDACTION(0),
        SECONDACTIONVALID(1);

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
            return ( enumvalue == 0 ) ? "No Second Action" : "Second Action Valid";
        }
    }


    public enum NUState {
        UNASSIGNEDMESSAGE(0),
        NORMALMESSAGE(1);

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
            return ( enumvalue == 0 ) ? "Unassigned" : "Normal";
        }
    }


    public enum T21State {
        PTMType1(0),
        PTMType2(1);

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
            return ( enumvalue == 0 ) ? "PTM Type 1" : "PTM Type 2";
        }
    }

    private NUState nu;
    private T21State t21;

    private RockerActionState rocker1;
    private RockerActionState rocker2;
    private EnergyBowState energyBow;
    private SecondActionState secondAction;

    public static RadioPacketRPS ResolvedPacket( UnknownPacket loPacket) {
        RadioPacketRPS loNew = null;
        try {
            loNew = new RadioPacketRPS( loPacket.toBytes() );
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return loNew;
    }

    public RadioPacketRPS(byte[] buffer) {
        super( buffer );
    }

    @Override
    public void setData(byte[] data) {
        super.setData(data);

        byte dataByte = data[1];
        secondAction = SecondActionState.values()[(dataByte & 0x01)];
        rocker2 = RockerActionState.values()[(dataByte & 0x0E) >> 1];
        energyBow = EnergyBowState.values()[(dataByte & 0x10) >> 4];
        rocker1 = RockerActionState.values()[(dataByte & 0xE0) >> 5];


        byte statusByte = data[6];
        nu = NUState.values()[(statusByte & 0x10) >> 4];
        t21 = T21State.values()[(statusByte & 0x20) >> 5];
    }


    @Override
    public String toString() {
        // The coding is a little strange. but only on button press do you get info on what button is pressed. on release not.

        //return "RPS " + getSenderId() +
        //		", Energy Bow " + energyBow.toString() +
        //		", Rocker " + rocker1.toString() +
        //		( secondAction == SecondActionState.SECONDACTIONVALID ? ", Second " + rocker2.toString() : "");

        if ( energyBow == EnergyBowState.RELEASED) {
            return "RPS " + getSenderId() +
                    ", Energy Bow " + energyBow.toString();

        } else {
            return "RPS " + getSenderId() +
                    ", Rocker " + rocker1.toString() +
                    ( secondAction == SecondActionState.SECONDACTIONVALID ? ", Second " + rocker2.toString() : "");

        }

    }

}
