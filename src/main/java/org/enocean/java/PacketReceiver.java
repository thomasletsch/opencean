package org.enocean.java;

import org.enocean.java.common.ProtocolConnector;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RawPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketReceiver {

    private static Logger logger = LoggerFactory.getLogger(PacketReceiver.class);

    private ProtocolConnector connector;

    public PacketReceiver(ProtocolConnector connector) {
        this.connector = connector;
    }

    /**
     * Waits for a sync byte first. Then checks the read header CRC8 and resets
     * the connector to the state after the last sync byte.
     * 
     * @return The received packet or null if header was incorrect (and that
     *         means no packet start was recognized)
     */
    public BasicPacket receive() {
        seekTillSyncByte();
        connector.mark();
        RawPacket rawPacket = new RawPacket();
        rawPacket.readHeader(connector);
        if (!rawPacket.getHeader().isValid()) {
            connector.reset();
            return null;
        }
        rawPacket.readPayload(connector);
        if (!rawPacket.getPayload().isValid()) {
            logger.warn("Payload CRC not correct! Package received: " + rawPacket);
            connector.reset();
            return null;
        }
        BasicPacket packet = PacketFactory.createFrom(rawPacket);
        return packet;
    }

    private void seekTillSyncByte() {
        while (!(connector.get() == BasicPacket.SYNC_BYTE)) {
            logger.debug("Waiting for sync byte");
        }
    }

}
