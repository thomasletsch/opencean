package org.enocean.java.utils;

public class ByteArrayUtils {
    public static String printByteArray(byte[] data) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < data.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(String.format("%02X", data[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    public static byte[] toByteArray(int number) {
        byte[] data = new byte[4];
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[3 - i] = (byte) ((number & (0xff << shift)) >>> shift);
        }
        return data;
    }
}
