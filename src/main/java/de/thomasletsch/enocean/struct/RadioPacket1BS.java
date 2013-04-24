package de.thomasletsch.enocean.struct;

public class RadioPacket1BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xD5;

    
    private int learnButton;
    private int contact;
    
    public static RadioPacket1BS ResolvedPacket( UnknownPacket loPacket) {
    	RadioPacket1BS loNew = null;
		try {
			loNew = new RadioPacket1BS( loPacket.toBytes() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return loNew;
    }

    public RadioPacket1BS(byte[] buffer) throws Exception {
    	super( buffer );
    }
    
    public void setData(byte[] poData) {
        super.setData(poData);
        
        byte dataByte = poData[1];
        contact = dataByte & 0x01;
        learnButton = (dataByte & 0x08) >> 3;
    }

    
    public String toString() {
    	return "1BS " + getSenderId() + 
    			", Learn Button " + (learnButton==0 ? "pressed": "not pressed") +
    			", Contact " + (contact==0 ? "open": "closed");
    }

}
