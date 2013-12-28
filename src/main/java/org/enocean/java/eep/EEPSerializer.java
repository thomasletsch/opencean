package org.enocean.java.eep;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;

public interface EEPSerializer {

    BasicPacket createPacket(EnoceanParameterAddress parameterAddress, Value value);

}
