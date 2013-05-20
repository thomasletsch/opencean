package org.enocean.java.packets;

import org.enocean.java.utils.CircularByteBuffer;

public class RawPacket {

    private Header header;

    private Payload payload;

    public void readHeader(CircularByteBuffer inputStream) {
        header = Header.from(inputStream);
    }

    public void readPayload(CircularByteBuffer buffer) {
        payload = Payload.from(header, buffer);
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
