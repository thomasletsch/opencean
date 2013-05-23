package org.enocean.java.address;

import java.util.Arrays;

import org.enocean.java.utils.ByteArrayUtils;

public class EnoceanId {

    private byte[] id;

    public static EnoceanId fromByteArray(byte[] array, int startPos) {
        byte[] id = new byte[4];
        System.arraycopy(array, startPos, id, 0, 4);
        return new EnoceanId(id);
    }

    public static EnoceanId fromInt(int idInt) {
        return new EnoceanId(ByteArrayUtils.toByteArray(idInt));
    }

    public EnoceanId(byte[] id) {
        this.id = id;
    }

    public byte[] toBytes() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%1$02X:%2$02X:%3$02X:%4$02X", id[0], id[1], id[2], id[3]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EnoceanId other = (EnoceanId) obj;
        if (!Arrays.equals(id, other.id)) {
            return false;
        }
        return true;
    }

}
