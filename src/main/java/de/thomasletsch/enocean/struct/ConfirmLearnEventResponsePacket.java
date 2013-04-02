package de.thomasletsch.enocean.struct;

import java.nio.ByteBuffer;

public class ConfirmLearnEventResponsePacket extends ResponsePacket {

    /**
     * Response time for Smart Ack Client in ms in which the controller can prepare the data and send it to the postmaster. Only actual if
     * learn return code is Learn IN
     */
    private short responseTime;

    /**
     * @see LearnAckEventPacket
     */
    private byte confirmCode;

    public ConfirmLearnEventResponsePacket(byte confirmCode) {
        this.confirmCode = confirmCode;
        responseTime = (short) System.currentTimeMillis();
    }

    @Override
    protected byte[] getResponseData() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addShort(responseTime);
        wrapper.addByte(confirmCode);
        return wrapper.getArray();
    }

    @Override
    protected void readData(ByteBuffer dataBytes) {
        super.readData(dataBytes);
        responseTime = dataBytes.getShort();
        confirmCode = dataBytes.get();
    }

    public short getResponseTime() {
        return responseTime;
    }

    public byte getConfirmCode() {
        return confirmCode;
    }

}
