package org.enocean.java;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket;
import org.enocean.java.packets.RadioPacket1BS;
import org.enocean.java.packets.RadioPacket4BS;
import org.enocean.java.packets.RadioPacketRPS;
import org.enocean.java.packets.RadioPacketVLD;
import org.enocean.java.packets.RawPacket;
import org.enocean.java.packets.ResponsePacket;
import org.enocean.java.packets.UnknownPacket;

public class PacketFactory {

    public static BasicPacket createFrom(RawPacket rawPacket) {
        switch (rawPacket.getHeader().getPacketType()) {
        case RadioPacket.PACKET_TYPE:
            return createRadioPacket(rawPacket);
        case ResponsePacket.PACKET_TYPE:
            return new ResponsePacket(rawPacket);
        default:
            return new UnknownPacket();
        }
    }

    private static RadioPacket createRadioPacket(RawPacket rawPacket) {
        switch (rawPacket.getPayload().getData()[0]) {
        case RadioPacket1BS.RADIO_TYPE:
            return new RadioPacket1BS(rawPacket);
        case RadioPacketRPS.RADIO_TYPE:
            return new RadioPacketRPS(rawPacket);
        case RadioPacket4BS.RADIO_TYPE:
            if (rawPacket.getPayload().getData().length == RadioPacket4BS.DATA_LENGTH) {
                return new RadioPacket4BS(rawPacket);
            } else {
                return new RadioPacket(rawPacket);
            }
        case RadioPacketVLD.RADIO_TYPE:
            return new RadioPacketVLD(rawPacket);
        default:
            return new RadioPacket(rawPacket);
        }
    }

}
