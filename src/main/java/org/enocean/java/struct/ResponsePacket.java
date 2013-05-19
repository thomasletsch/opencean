package org.enocean.java.struct;

import java.nio.ByteBuffer;

public class ResponsePacket extends BasicPacket {

    private byte returnCode;

    public ResponsePacket() {
        setPacketType(PACKET_TYPE_RESPONSE);
        setDataLength((short) 1);
    }

    public ResponsePacket(byte returnCode) {
        this();
        this.returnCode = returnCode;
    }

    public byte getReturnCode() {
        return returnCode;
    }

    protected byte[] getResponseData() {
        return new byte[] {};
    }

    @Override
    protected byte[] getData() {
        ByteArrayWrapper data = new ByteArrayWrapper();
        data.addByte(getReturnCode());
        data.addBytes(getResponseData());
        return data.getArray();
    }

    @Override
    protected byte[] getOptionalData() {
        return new byte[] {};
    }

    protected void readData(ByteBuffer dataBytes) {
        returnCode = dataBytes.get();
    }

    protected void readOptionalData(ByteBuffer optionalDataBytes) {
    }

}
