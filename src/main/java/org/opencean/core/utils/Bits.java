package org.opencean.core.utils;

public class Bits {

    public static boolean isBitSet(byte b, int pos) {
        return ((b >> pos) & 1) == 1;
    }

    public static int getBit(byte b, int pos) {
        return ((b >> pos) & 1);
    }

    public static boolean getBool(byte b, int pos) {
        return ((b >> pos) & 1) == 1;
    }

    public static boolean isBitSet(short s, int pos) {
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

    /**
     * Generate a byte with given bit range set and rest bits unset.
     *
     * @param startBit The bit position the one(s) should begin. The value must
     * be between 0 and 7, and be at least as large as @endBit.
     * @param endBit The bit position the one(s) should begin. The value must be
     * between 0 and 7, and must not be greater then @startBit.
     * @return Return a byte with only given bit range set.
     */
    public static byte getSetBits(int startBit, int endBit) {
        assert startBit <= 7;
        assert endBit <= 7;
        assert startBit >= endBit;

        final byte mask1 = (byte) ((1 << (startBit + 1)) - 1);
        final byte mask2 = (byte) ((1 << (endBit)) - 1);
        final byte mask = (byte) (mask1 ^ mask2);

        return mask;
    }

}
