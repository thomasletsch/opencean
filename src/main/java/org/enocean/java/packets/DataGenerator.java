package org.enocean.java.packets;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.utils.ByteArray;

public class DataGenerator {
	private ByteArray data = new ByteArray();
	
	public DataGenerator(byte type, byte state, EnoceanParameterAddress deviceID, byte status){
		data.addByte(type);
		data.addByte(state);
		data.addBytes(deviceID.getEnoceanDeviceId().toBytes());
		data.addByte(status);
	}
	
	public byte[] getData(){
		return data.getArray();
	}
}
