package org.enocean.java.utils;

public class Bits {

    public static boolean getBit(byte b, int pos) {
        return ((b >> pos) & 1) == 1;
    }

    public static boolean getBit(short s, int pos) {
        return ((s >> pos) & 1) == 1;
    }

    public static byte setBit(byte b, int pos, boolean bit) {
        if (bit) {
            b = (byte) (b | (1 << pos));
        } else {
            b = (byte) (b & ~(1 << pos));
        }
        return b;
    }

}