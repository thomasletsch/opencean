package org.enocean.java.struct;

public class RadioPacket1BS extends RadioPacket {

    public static final byte RADIO_TYPE = (byte) 0xD5;

    public enum ContactState {
    	OPEN(0),
    	CLOSED(1);
    	
		private final int enumvalue;

    	ContactState(int value) {
			this.enumvalue = value;
		}

    	ContactState(byte value) {
			this.enumvalue = value;
		}

		public byte toByte() {
			return (byte) enumvalue;
		}
		
		public String toString() {
			return ( enumvalue == 0 ) ? "Open" : "Closed";
		}
    }

    public enum LearnButtonState {
    	PRESSED(0),
    	NOTPRESSED(1);
    	
		private final int enumvalue;

		LearnButtonState(int value) {
			this.enumvalue = value;
		}

		LearnButtonState(byte value) {
			this.enumvalue = value;
		}

		public byte toByte() {
			return (byte) enumvalue;
		}
		
		public String toString() {
			return ( enumvalue == 0 ) ? "Pressed" : "Not Pressed";
		}
    }

    private LearnButtonState learnButton;
    private ContactState contact;
    

    public RadioPacket1BS(byte[] buffer) throws Exception {
    	super( buffer );
    }
    
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

    
    public void setData(byte[] poData) {
        super.setData(poData);
        
        byte dataByte = poData[1];
        contact = ContactState.values()[(dataByte & 0x01)];
        learnButton = LearnButtonState.values()[(dataByte & 0x08) >> 3];
    }

    
    public String toString() {
    	return "1BS " + getSenderId() + 
    			", Learn Button " + learnButton.toString() +
    			", Contact " + contact.toString();
    }

}
