package org.enocean.java;

import org.enocean.java.struct.BasicPacket;
import org.enocean.java.struct.RadioPacket;
import org.enocean.java.struct.RadioPacket1BS;
import org.enocean.java.struct.RadioPacketRPS;
import org.enocean.java.struct.UnknownPacket;

public class PacketFactory {

    public static BasicPacket createFrom(RawPacket rawPacket) {
        switch (rawPacket.getHeader().getPacketType()) {

        case RadioPacket1BS.RADIO_TYPE:
            return new RadioPacket1BS(rawPacket.toBytes());

        case RadioPacketRPS.RADIO_TYPE:
            return new RadioPacketRPS(rawPacket.toBytes());

        case RadioPacket.RADIO_TYPE_4BS:
        case RadioPacket.RADIO_TYPE_VLD:
            return new RadioPacket(rawPacket.toBytes());

        default:
            return new UnknownPacket();

        }
    }

}
