package org.opencean.core.eep;

import java.util.Map;

import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.Color;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.OnOffState;
import org.opencean.core.common.values.Unit;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.RadioPacket4BS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedLightingStatus extends RadioPacket4BSParser {

    private static Logger logger = LoggerFactory.getLogger(ExtendedLightingStatus.class);
    
    private ServiceMode serviceMode;
    private OperationHoursFlag operationHoursFlag;
    private ErrorState errorState;
    private LrnBit lrnBit;
    private ParameterMode parameterMode;
    private OnOffState status;

	@Override
	protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {

		serviceMode = ServiceMode.values()[((packet.getDb0() & 0x80) >> 7 )]; // bit 7
		operationHoursFlag = OperationHoursFlag.values()[((packet.getDb0() & 0x40) >> 6 )]; // bit 6
		errorState = ErrorState.values()[((packet.getDb0() & 0x30) >> 4 )]; // bit 5-4
		lrnBit = LrnBit.values()[((packet.getDb0() & 0x8) >> 3 )]; // bit 3
		parameterMode = ParameterMode.values()[((packet.getDb0() & 0x6) >> 1 )]; // bit 2-1
		status = ((int) (packet.getDb0() & 0x1)) == 1 ? OnOffState.ON : OnOffState.OFF;

		// Parameter1: 0 Dimm-Value 0 .. 255; 1 R - Red 0 .. 255; 2 Energy Metering Value (MSB 15 .. 8)
		// Parameter2: Mode 0: Lamp operating hours (MSB 15 .. 8); Mode 1: G - Green (0 .. 255); Mode 2: Energy metering value (7 .. 0 LSB) 	
		// Parameter3: Mode 0: Lamp operating hours (7 .. 0 LSB); Mode 1: B - Blue (0 .. 255); Mode 2: Unit for energy values 	
		int parameter1 = unsignedByteToInt(packet.getDb3());
		int parameter2 = unsignedByteToInt(packet.getDb2());
		int parameter3 = unsignedByteToInt(packet.getDb1());
		
		switch (parameterMode) {
		
		case DIMMER:
			values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.DIMM_VALUE), new NumberWithUnit(
					Unit.POINTS, parameter1));

			values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.OPERATING_HOURS), new NumberWithUnit(
					Unit.HOURS, getValueFromMsbLsb(parameter2, parameter3)));			
			break;
			
		case RGB:
			values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.COLOR), new Color(
                    parameter1, parameter2, parameter3));
			break;
			
		case ENERGY:
			values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.ENERGY_METERING_VALUE), getEnergy(
					parameter1, parameter2, parameter3));
			break;
		}		

		values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.POWER), status);
		values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.ERROR_STATE), errorState);
		values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.OPERATIONS_HOURS_FLAG), operationHoursFlag);
		
		logger.info(this.parameterMode.name() + ": " + values.toString());
	}
	
	public String getParameterMode() {
		return parameterMode.name();
	}

	private int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}
	
	private int getValueFromMsbLsb(int msb, int lsb) {
		return msb * 256 + lsb;
	}

	private NumberWithUnit getEnergy(int parameter1, int parameter2, int parameter3) {
		
		// Unit
		Unit unit;
		if (parameter3 <= 3) {
			unit = Unit.WATT;
		}
		else if (parameter3 <= 7) {
			unit = Unit.WATTHOURS;
		}
		else if (parameter3 <= 9) {
			unit = Unit.AMPERE;
		}
		else {
			unit = Unit.VOLT;
		}
		
		// Value
		float rawValue = (float) getValueFromMsbLsb(parameter1, parameter2);
		Float value = null;
		
		switch(parameter3) {
		case 0: value = rawValue / 1000; break;// mW
		case 1: value = rawValue;  break; // W
		case 2: value = rawValue * 1000;  break; // kW
		case 3: value = rawValue * 1000000;  break; // MW
		case 4: value = rawValue;  break; // Wh
		case 5: value = rawValue * 1000;  break; // kWh
		case 6: value = rawValue * 1000000;  break; // MWh
		case 7: value = rawValue * 1000000000;  break; // GWh
		case 8: value = rawValue / 1000;  break;// mA
		case 9: value = rawValue / 10;  break; // 1/10 A
		case 10: value = rawValue / 1000;  break; //mV 
		case 11: value = rawValue / 10;  break; // 1/10V
				
		default: break;

		}
		
		return new NumberWithUnit(unit, value);
	}
	
    public enum ErrorState implements Value {
    	NO_ERROR(0), LAMP_FAILURE(1), INTERNAL_FAILURE(2), EXTERNAL_FAILURE(2);
        
        private final int enumvalue;

        ErrorState(int value) {
            this.enumvalue = value;
        }

        ErrorState(byte value) {
            this.enumvalue = value;
        }

        @Override
        public String toString() {
        	switch (enumvalue) {
        	case 0: return "No error present"; 
        	case 1: return "Lamp-failure";
        	case 2: return "Internal failure";
        	case 3: return "Failure on the external periphery";
        	}
        	return "undefined";
        }

		@Override
		public Object getValue() {
            return name();
		}

		@Override
		public String getDisplayValue() {
            return (name().toLowerCase());
		}
    }
    
    public enum OperationHoursFlag implements Value {
    	HOURS_NOT_AVAILABLE(0), HOURS_AVAILABLE(1);
        
        private final int enumvalue;

        OperationHoursFlag(int value) {
            this.enumvalue = value;
        }

        OperationHoursFlag(byte value) {
            this.enumvalue = value;
        }

        @Override
        public String toString() {
        	switch (enumvalue) {
        	case 0: return "No lamp operating hours available";
        	case 1: return "Lamp operating hours available";
        	}
        	return "undefined";
        }

		@Override
		public Object getValue() {
            return name();
		}

		@Override
		public String getDisplayValue() {
            return (name().toLowerCase());
		}
    }

	
    public enum ServiceMode {
        NORMAL(0), SERVICE(1);
        
        private final int enumvalue;

        ServiceMode(int value) {
            this.enumvalue = value;
        }

        ServiceMode(byte value) {
            this.enumvalue = value;
        }

        @Override
        public String toString() {
        	switch (enumvalue) {
        	case 0: return "Normal mode";
        	case 1: return "Service mode";
        	}
        	return "undefined";
        }
    }
	
    public enum LrnBit {
        TEACH_IN(0), DATA(1);
        
        private final int enumvalue;

        LrnBit(int value) {
            this.enumvalue = value;
        }

        LrnBit(byte value) {
            this.enumvalue = value;
        }

        @Override
        public String toString() {
        	switch (enumvalue) {
        	case 0: return "Teach-in telegram";
        	case 1: return "Data telegram";
        	}
        	return "undefined";
        }
    }
    
    public enum ParameterMode {
        DIMMER(0), RGB(1), ENERGY(2), UNUSED(3);
        
        private final int enumvalue;

        ParameterMode(int value) {
            this.enumvalue = value;
        }

        ParameterMode(byte value) {
            this.enumvalue = value;
        }

        @Override
        public String toString() {
        	switch (enumvalue) {
        	case 0: return "8 Bit Dimmer Value and Lamp operating hours";
        	case 1: return "RGB Value";
        	case 2: return "Energy metering value";
        	case 3: return "Not used";
        	}
        	return "undefined";
        }
    }
}
