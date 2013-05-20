package org.enocean.java.struct;

import java.nio.ByteBuffer;

public class RadioPacket extends BasicPacket {

    public static final byte RADIO_TYPE_4BS = (byte) 0xA5;
    public static final byte RADIO_TYPE_VLD = (byte) 0xD2;

    private String senderId;
    private int repeaterCount;

    private byte subTelNum;
    private int destinationId;
    private byte dBm;
    private byte securityLevel;

    public static RadioPacket ResolvedPacket(UnknownPacket loPacket) {
        RadioPacket loNew = null;
        loNew = new RadioPacket(loPacket.toBytes());
        return loNew;
    }

    public RadioPacket(byte[] buffer) {
        readFrom(buffer);
        setData(super.getData());
    }

    /**
     * @param subTelNum
     *            Number of subTelegram. Send = 3, receive = 1..x
     * @param destinationId
     *            Destination Id (4 byte). Broadcast Radio = FF FF FF FF, ADT
     *            radio: Destination ID (address)
     * @param dBm
     *            Send case: FF, Receive case: best RSSI value of all received
     *            subtelegrams (value decimal without minus)
     * @param securityLevel
     *            Security Level. 0 = unencrypted, x = type of encryption
     */
    public RadioPacket(byte[] poData, byte subTelNum, int destinationId, byte dBm, byte securityLevel) {
        this(subTelNum, destinationId, dBm, securityLevel);
        setData(poData);
    }

    /**
     * @param subTelNum
     *            Number of subTelegram. Send = 3, receive = 1..x
     * @param destinationId
     *            Destination Id (4 byte). Broadcast Radio = FF FF FF FF, ADT
     *            radio: Destination ID (address)
     * @param dBm
     *            Send case: FF, Receive case: best RSSI value of all received
     *            subtelegrams (value decimal without minus)
     * @param securityLevel
     *            Security Level. 0 = unencrypted, x = type of encryption
     */
    protected RadioPacket(byte subTelNum, int destinationId, byte dBm, byte securityLevel) {
        this.subTelNum = subTelNum;
        this.destinationId = destinationId;
        this.dBm = dBm;
        this.securityLevel = securityLevel;
        setPacketType(PACKET_TYPE_RADIO);
        setOptionalDataLength((byte) 7);
    }

    @Override
    public void setData(byte[] poData) {
        super.setData(poData);
        senderId = String.format("%1$02X:%2$02X:%3$02X:%4$02X", poData[2], poData[3], poData[4], poData[5]);
        repeaterCount = (poData[6] & 0x0F);
    }

    public byte getSubTelNum() {
        return subTelNum;
    }

    public void setSubTelNum(byte subTelNum) {
        this.subTelNum = subTelNum;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public byte getdBm() {
        return dBm;
    }

    public void setdBm(byte dBm) {
        this.dBm = dBm;
    }

    public String getSenderId() {
        return senderId;
    }

    public int getRepeaterCount() {
        return repeaterCount;
    }

    public byte getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(byte securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    protected byte[] getOptionalData() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addByte(subTelNum);
        wrapper.addInt(destinationId);
        wrapper.addByte(dBm);
        wrapper.addByte(securityLevel);
        return wrapper.getArray();
    }

    protected void readData(ByteBuffer dataBytes) {
        setData(dataBytes.array());
    }

    protected void readOptionalData(ByteBuffer optionalDataBytes) {
        subTelNum = optionalDataBytes.get();
        destinationId = optionalDataBytes.getInt();
        dBm = optionalDataBytes.get();
        securityLevel = optionalDataBytes.get();
    }
}
