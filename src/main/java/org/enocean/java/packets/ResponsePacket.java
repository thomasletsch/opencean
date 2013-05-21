package org.enocean.java.packets;


public class ResponsePacket extends BasicPacket {

    private byte returnCode;

    public ResponsePacket() {
        header.setPacketType(PACKET_TYPE_RESPONSE);
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
    protected void fillData() {
        ByteArrayWrapper data = new ByteArrayWrapper();
        data.addByte(getReturnCode());
        data.addBytes(getResponseData());
        payload.setData(data.getArray());
    }

    @Override
    protected void parseData() {
        returnCode = payload.getData()[0];
    }

}
