package de.thomasletsch.enocean.struct;

import java.nio.ByteBuffer;

/**
 * Request to backbone controller how to handle the received learn request.
 * 
 * @author thomas
 * 
 */
public class ConfirmLearnEventPacket extends EventPacket {

    /**
     * Already post master 0b xxxx 1xxx
     * 
     * Place for mailbox 0b xxxx x1xx
     * 
     * Good RSSI 0b xxxx xx1x
     * 
     * Local 0b xxxx xxx1
     */
    private byte postMasterPrio;

    /**
     * nnn = Most significant 3 bits of the Manufacturer ID <br>
     * 00000 = reserved <br>
     * 
     * 2^2 ... 2^0: Manufacturer ID <br>
     * 2^7 ... 2^3: Res.<br>
     */
    private byte manufactorerIdHigh;

    /**
     * Least significant bits of the Manufact. ID
     */
    private byte manufactorerIdLow;

    /**
     * Code of used EEP profile
     */
    private int eep;

    /**
     * Signal strength. <br>
     * Send case: FF<br>
     * Receive case: actual RSSI
     */
    private byte rssi;

    /**
     * Device ID of the Post master candidate
     */
    private int postMasterId;

    /**
     * This sensor would be Learn IN
     */
    private int smartAckClientId;

    /**
     * Numbers of repeater hop
     */
    private byte hopCount;

    public ConfirmLearnEventPacket() {
    }

    @Override
    protected void readData(ByteBuffer dataBytes) {
        super.readData(dataBytes);
        postMasterPrio = dataBytes.get();
        manufactorerIdHigh = dataBytes.get();
        manufactorerIdLow = dataBytes.get();
        eep = (dataBytes.get() << 16) | (dataBytes.get() << 8) | (dataBytes.get() & 0xFF);
        rssi = dataBytes.get();
        postMasterId = dataBytes.getInt();
        smartAckClientId = dataBytes.getInt();
        hopCount = dataBytes.get();
    }

    @Override
    public boolean isEventCodeSupported() {
        return getEventCode() == EVENT_CODE_SA_CONFIRM_LEARN;
    }

    public byte getPostMasterPrio() {
        return postMasterPrio;
    }

    public byte getManufactorerIdHigh() {
        return manufactorerIdHigh;
    }

    public byte getManufactorerIdLow() {
        return manufactorerIdLow;
    }

    public int getEep() {
        return eep;
    }

    public byte getRssi() {
        return rssi;
    }

    public int getPostMasterId() {
        return postMasterId;
    }

    public int getSmartAckClientId() {
        return smartAckClientId;
    }

    public byte getHopCount() {
        return hopCount;
    }

}
