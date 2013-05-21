package org.enocean.java.packets;

public class RemoteManagementCommandPacket extends BasicPacket {

    public static final byte PACKET_TYPE = 0x07;

    private static final int SOURCE_ID_SEND_CASE = 0x0000000;

    // data
    private short functionNumber;
    private short manufactorerId;
    private byte[] messageData;

    // optionalData
    private int destinationId;
    private int sourceId;
    private byte dBm;
    private boolean sendWithDelay;

    public RemoteManagementCommandPacket(short functionNumber, short manufactorerId, byte[] messageData, int destinationId, int sourceId,
            byte dBm, boolean sendWithDelay) {
        this.functionNumber = functionNumber;
        this.manufactorerId = manufactorerId;
        this.messageData = messageData;
        this.destinationId = destinationId;
        this.sourceId = sourceId;
        this.dBm = dBm;
        this.sendWithDelay = sendWithDelay;
    }

    public RemoteManagementCommandPacket(short functionNumber, short manufactorerId, byte[] messageData, int destinationId, byte dBm,
            boolean sendWithDelay) {
        this(functionNumber, manufactorerId, messageData, destinationId, SOURCE_ID_SEND_CASE, dBm, sendWithDelay);
    }

}
