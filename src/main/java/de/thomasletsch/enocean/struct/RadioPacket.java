package de.thomasletsch.enocean.struct;

import java.nio.ByteBuffer;

public class RadioPacket extends BasicPacket {

    public static final byte RADIO_TYPE_VLD = (byte) 0xD2;

    public static final byte RADIO_TYPE_4BS = (byte) 0xA5;

    private byte[] data;

    private byte subTelNum;

    private int destinationId;

    private byte dBm;

    private byte securityLevel;

    /**
     * @param subTelNum
     *            Number of subTelegram. Send = 3, receive = 1..x
     * @param destinationId
     *            Destination Id (4 byte). Broadcast Radio = FF FF FF FF, ADT radio: Destination ID (address)
     * @param dBm
     *            Send case: FF, Receive case: best RSSI value of all received subtelegrams (value decimal without minus)
     * @param securityLevel
     *            Security Level. 0 = unencrypted, x = type of encryption
     */
    public RadioPacket(byte[] data, byte subTelNum, int destinationId, byte dBm, byte securityLevel) {
        this(subTelNum, destinationId, dBm, securityLevel);
        this.data = data;
        setDataLength((short) data.length);
    }

    /**
     * @param subTelNum
     *            Number of subTelegram. Send = 3, receive = 1..x
     * @param destinationId
     *            Destination Id (4 byte). Broadcast Radio = FF FF FF FF, ADT radio: Destination ID (address)
     * @param dBm
     *            Send case: FF, Receive case: best RSSI value of all received subtelegrams (value decimal without minus)
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

    public void setData(byte[] data) {
        this.data = data;
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

    public byte getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(byte securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    protected byte[] getData() {
        return data;
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

    @Override
    protected void readData(ByteBuffer dataBytes) {
        data = dataBytes.array();
    }

    @Override
    protected void readOptionalData(ByteBuffer optionalDataBytes) {
        subTelNum = optionalDataBytes.get();
        destinationId = optionalDataBytes.getInt();
        dBm = optionalDataBytes.get();
        securityLevel = optionalDataBytes.get();
    }
}
