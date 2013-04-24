package de.thomasletsch.enocean.struct;

public class UnknownPacket extends BasicPacket {

	@Override
	public String toString() {
		return super.toString();
	}
	
	
	public BasicPacket GetResolvedPacket(){
		switch ( getChoice() ) {

		case RadioPacket1BS.RADIO_TYPE:
			return RadioPacket1BS.ResolvedPacket(this);

		case RadioPacket.RADIO_TYPE_4BS:
		case RadioPacket.RADIO_TYPE_RPS:
		case RadioPacket.RADIO_TYPE_VLD:
			return RadioPacket.ResolvedPacket(this);

			
		default:
				return this;
		}
	}
	
}
