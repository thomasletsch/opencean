package org.enocean.java.struct;

import org.enocean.java.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Payload {
    private static Logger logger = LoggerFactory.getLogger(Payload.class);

    private byte[] data;
    private byte[] optionaldata;
    private byte crc8d;

    public static Payload from(Header header, CircularByteBuffer buffer) {
        logger.info("Reading payload...");
        Payload payload = new Payload();
        payload.data = new byte[header.getDataLength()];
        buffer.get(payload.data);
        payload.optionaldata = new byte[header.getOptionalDataLength()];
        buffer.get(payload.optionaldata);
        payload.crc8d = buffer.get();
        logger.info(payload.toString());
        payload.checkCrc8();
        return payload;
    }

    public void checkCrc8() {
        if (calculateCrc8() != crc8d) {
            throw new RuntimeException("Payload CRC 8 is not correct! Expected " + calculateCrc8() + ", but received " + crc8d);
        }
    }

    public byte[] toBytes() {
        ByteArrayWrapper bytes = new ByteArrayWrapper();
        bytes.addBytes(data);
        bytes.addBytes(optionaldata);
        bytes.addByte(crc8d);
        return bytes.getArray();
    }

    @Override
    public String toString() {
        return "Payload: " + "data=" + printByteArray(data) + ", optionaldata=" + printByteArray(optionaldata) + ", crc8d=" + crc8d;
    }

    private byte calculateCrc8() {
        CRC8 crc8 = new CRC8();
        crc8.update(data, 0, data.length);
        crc8.update(optionaldata, 0, optionaldata.length);
        return (byte) crc8.getValue();
    }

    private String printByteArray(byte[] data) {
        String s = "[";
        for (int i = 0; i < data.length; i++) {
            if (i != 0) {
                s += ", ";
            }
            s += data[i];
        }
        s += "]";
        return s;
    }
}
