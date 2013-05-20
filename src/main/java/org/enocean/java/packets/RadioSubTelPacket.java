package org.enocean.java.packets;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class RadioSubTelPacket extends RadioPacket {

    private short timeStamp;

    private List<SubTel> subTels;

    public RadioSubTelPacket(byte subTelNum, int destinationId, byte dBm, byte securityLevel, List<SubTel> subTels) {
        super(subTelNum, destinationId, dBm, securityLevel);
        this.subTels = subTels;
        setTimeStamp((short) (System.currentTimeMillis() & 0xFFFF));
    }

    public List<SubTel> getSubTels() {
        return subTels;
    }

    public void setSubTels(List<SubTel> subTels) {
        this.subTels = subTels;
    }

    @Override
    protected byte[] getOptionalData() {
        ByteArrayWrapper optional = new ByteArrayWrapper(super.getOptionalData());
        optional.addShort(timeStamp);
        for (SubTel subTel : subTels) {
            optional.addByte(subTel.getTick());
            optional.addByte(subTel.getdBm());
            optional.addByte(subTel.getStatus());
        }
        return optional.getArray();
    }

    @Override
    protected void readOptionalData(ByteBuffer optionalDataBytes) {
        super.readOptionalData(optionalDataBytes);
        timeStamp = optionalDataBytes.getShort();
        subTels = new ArrayList<SubTel>();
        while (optionalDataBytes.hasRemaining()) {
            SubTel subTel = new SubTel();
            subTel.setTick(optionalDataBytes.get());
            subTel.setdBm(optionalDataBytes.get());
            subTel.setStatus(optionalDataBytes.get());
        }
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(short timeStamp) {
        this.timeStamp = timeStamp;
    }
}
