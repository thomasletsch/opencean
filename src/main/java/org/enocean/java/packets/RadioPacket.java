package org.enocean.java.packets;

import java.nio.ByteBuffer;

public class RadioPacket extends BasicPacket {

    public static final byte PACKET_TYPE = 0x01;

    public static final byte RADIO_TYPE_4BS = (byte) 0xA5;
    public static final byte RADIO_TYPE_VLD = (byte) 0xD2;

    private String senderId;
    private int repeaterCount;

    private byte subTelNum;
    private int destinationId;
    private byte dBm;
    private byte securityLevel;

    public RadioPacket(RawPacket rawPacket) {
        super(rawPacket);
    }

    public RadioPacket() {
        header.setPacketType(PACKET_TYPE);
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
    public RadioPacket(byte[] data, byte subTelNum, int destinationId, byte dBm, byte securityLevel) {
        this(subTelNum, destinationId, dBm, securityLevel);
        payload.setData(data);
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
        header.setPacketType(PACKET_TYPE);
    }

    @Override
    protected void parseData() {
        byte[] data = payload.getData();
        int length = data.length;
        senderId = String.format("%1$02X:%2$02X:%3$02X:%4$02X", data[length - 5], data[length - 4], data[length - 3], data[length - 2]);
        repeaterCount = (data[6] & 0x0F);
    }

    public ParameterMap getAllParameterValues() {
        ParameterMap result = new ParameterMap();
        return result;
    }

    @Override
    protected void fillOptionalData() {
        ByteArrayWrapper wrapper = new ByteArrayWrapper();
        wrapper.addByte(subTelNum);
        wrapper.addInt(destinationId);
        wrapper.addByte(dBm);
        wrapper.addByte(securityLevel);
        payload.setOptionalData(wrapper.getArray());
    }

    @Override
    protected void parseOptionalData() {
        ByteBuffer optionalDataBytes = ByteBuffer.wrap(payload.getOptionalData());
        subTelNum = optionalDataBytes.get();
        destinationId = optionalDataBytes.getInt();
        dBm = optionalDataBytes.get();
        securityLevel = optionalDataBytes.get();
    }

    public String getSenderId() {
        return senderId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[sender=" + senderId + ", repeaterCount=" + repeaterCount + "]";
    }

}
