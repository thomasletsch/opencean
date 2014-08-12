package org.opencean.core.eep;

import java.util.Map;

import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.ButtonState;
import org.opencean.core.common.values.Value;
import org.opencean.core.common.values.WindowHandleState;
import org.opencean.core.packets.RadioPacketRPS;
import org.opencean.core.utils.Bits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser for window handles
 * EEP: F6:10:00
 * 
 * @author Holger Ploch
 */

public class WindowHandle extends RadioPacketRPSParser {

    private static Logger logger = LoggerFactory.getLogger(WindowHandle.class);
      
    public enum Positions { UP, DOWN, MIDDLE };

	WindowHandleState position;
		
	
    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacketRPS radioPacket) {
    	
    	position = null;
    	byte dataByte = radioPacket.getDataByte();
        
        if (Bits.getBit(dataByte, 7) == 1){
        	
        	if (Bits.getBit(dataByte, 6) == 1 && (Bits.getBit(dataByte, 4) == 0)) {
        		position = WindowHandleState.MIDDLE;
        	} else
        		if (Bits.getBit(dataByte, 6) == 1 && (Bits.getBit(dataByte, 5) == 1) && (Bits.getBit(dataByte, 4) == 1)) {
            		position = WindowHandleState.DOWN;
        		} else
        			if (Bits.getBit(dataByte, 6) == 1 && (Bits.getBit(dataByte, 5) == 0) && (Bits.getBit(dataByte, 4) == 1)) {
                		position = WindowHandleState.UP;
        			}
        }
        
        addWindowPositionAndActionToParameters(values, radioPacket);

        logger.info("Current window handle: "+radioPacket.getSenderId()+", position: " + position);
    }

    private void addWindowPositionAndActionToParameters(Map<EnoceanParameterAddress, Value> map, RadioPacketRPS radioPacketRPS) {
    	
    	if (position != null){
            map.put(new EnoceanParameterAddress(radioPacketRPS.getSenderId(), null, Parameter.CONTACT_STATE), position);
        }
    }



    @Override
    public String toString() {
        return "windowHandlePosition=" + position;
    }
    
}
