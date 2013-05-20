package org.enocean.java;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RawPacket;
import org.enocean.java.utils.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketReceiver {

    private static Logger logger = LoggerFactory.getLogger(PacketReceiver.class);

    private CircularByteBuffer buffer;

    public PacketReceiver(CircularByteBuffer buffer) {
        this.buffer = buffer;
    }

    public BasicPacket receive() {
        seekTillSyncByte();
        RawPacket rawPacket = new RawPacket();
        buffer.mark();
        rawPacket.readHeader(buffer);
        if(!rawPacket.getHeader().isValid()) {
            buffer.reset();
            return null;
        }
        rawPacket.readPayload(buffer);
        BasicPacket packet = PacketFactory.createFrom(rawPacket);
        return packet;
    }

    private void seekTillSyncByte() {
        while (!(buffer.get() == BasicPacket.SYNC_BYTE)) {
            logger.warn("Waiting for sync byte");
        }
    }

}
