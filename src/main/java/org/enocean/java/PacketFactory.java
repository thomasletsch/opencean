package org.enocean.java;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket;
import org.enocean.java.packets.RadioPacket1BS;
import org.enocean.java.packets.RadioPacket4BS;
import org.enocean.java.packets.RadioPacketRPS;
import org.enocean.java.packets.RadioPacketVLD;
import org.enocean.java.packets.RawPacket;
import org.enocean.java.packets.UnknownPacket;

public class PacketFactory {

    public static BasicPacket createFrom(RawPacket rawPacket) {
        switch (rawPacket.getHeader().getPacketType()) {
        case BasicPacket.PACKET_TYPE_RADIO:
            return createRadioPacket(rawPacket);
        default:
            return new UnknownPacket();
        }
    }

    private static RadioPacket createRadioPacket(RawPacket rawPacket) {
        switch (rawPacket.getPayload().getData()[0]) {
        case RadioPacket1BS.RADIO_TYPE:
            return new RadioPacket1BS(rawPacket.toBytes());
        case RadioPacketRPS.RADIO_TYPE:
            return new RadioPacketRPS(rawPacket.toBytes());
        case RadioPacket4BS.RADIO_TYPE:
            return new RadioPacket4BS(rawPacket.toBytes());
        case RadioPacketVLD.RADIO_TYPE:
            return new RadioPacketVLD(rawPacket.toBytes());
        default:
            return new RadioPacket(rawPacket.toBytes());
        }
    }

}
