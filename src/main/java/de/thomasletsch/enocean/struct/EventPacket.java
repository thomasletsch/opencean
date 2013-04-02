package de.thomasletsch.enocean.struct;

import java.nio.ByteBuffer;

public class EventPacket extends BasicPacket {

    public static final byte EVENT_CODE_SA_RECLAIM_NOT_SUCCESSFUL = 0x01;

    public static final byte EVENT_CODE_SA_CONFIRM_LEARN = 0x02;

    public static final byte EVENT_CODE_SA_LEARN_ACK = 0x03;

    public static final byte EVENT_CODE_CO_READY = 0x04;

    public static final byte EVENT_CODE_CO_EVENT_SECUREDEVICES = 0x0;

    private byte eventCode;

    public EventPacket() {
        setPacketType(PACKET_TYPE_EVENT);
        setDataLength((short) 1);
    }

    @Override
    protected byte[] getData() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addByte(eventCode);
        wrapper.addBytes(getEventData());
        return wrapper.getArray();
    }

    public byte getEventCode() {
        return eventCode;
    }

    public void setEventCode(byte eventCode) {
        this.eventCode = eventCode;
    }

    public byte[] getEventData() {
        return new byte[] {};
    }

    public boolean isEventCodeSupported() {
        return eventCode == EVENT_CODE_SA_RECLAIM_NOT_SUCCESSFUL;
    }

    @Override
    protected byte[] getOptionalData() {
        return new byte[] {};
    }

    @Override
    protected void readData(ByteBuffer dataBytes) {
        eventCode = dataBytes.get();
    }

    @Override
    protected void readOptionalData(ByteBuffer optionalDataBytes) {
    }

}
