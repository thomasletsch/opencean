package de.thomasletsch.enocean.struct;

public class RadioPacketRPS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xF6;

    
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

    public RadioPacketRPS(byte[] buffer) throws Exception {
    	super( buffer );
    }
    
    public void setData(byte[] poData) {
        super.setData(poData);
        
    }

    
    public String toString() {
    	return "RPS " + getSenderId();
    }

}
