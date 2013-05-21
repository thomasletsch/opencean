package org.enocean.java.packets;

import org.enocean.java.common.ProtocolConnector;

public class RawPacket {

    private Header header;

    private Payload payload;

    public void readHeader(ProtocolConnector connector) {
        header = Header.from(connector);
    }

    public void readPayload(ProtocolConnector connector) {
        payload = Payload.from(header, connector);
    }

    public byte[] toBytes() {
        ByteArrayWrapper bytes = new ByteArrayWrapper();
        bytes.addBytes(header.toBytes());
        bytes.addBytes(payload.toBytes());
        return bytes.getArray();
    }

    public Header getHeader() {
        return header;
    }

    public Payload getPayload() {
        return payload;
    }

}
