package org.opencean.core.packets.data;

import org.opencean.core.eep.CalculationUtil;
import org.opencean.core.utils.Bits;

/**
 *
 * @author rathgeb
 */
public class PacketData {

    protected byte[] data;

    public PacketData(int size) {
        data = new byte[size];
    }

    public PacketData(byte[] data) {
        this.data = data;
    }

    protected int convPosDbToReal(int dbPos) {
        return data.length - 1 - dbPos;
    }

    protected int getDataBit(int db, int bit) {
        return Bits.getBit(data[convPosDbToReal(db)], bit);
    }

    protected long getDataRange(int startDB, int startBit, int endDB, int endBit) {
        // e.g. db3.5 ... db2.7
        assert startDB >= endDB || (startDB == endDB && startBit >= endBit);
        assert startDB <= data.length - 1;

        final int realStartByte = convPosDbToReal(startDB);
        final int realEndByte = convPosDbToReal(endDB);

        return Bits.getBitsFromBytes(data, realStartByte, startBit, realEndByte, endBit);
    }

    protected void setDataRange(long value, int startDB, int startBit, int endDB, int endBit) {
        // e.g. db3.5 ... db2.7
        assert startDB >= endDB || (startDB == endDB && startBit >= endBit);
        assert startDB <= data.length - 1;

        final int realStartByte = convPosDbToReal(startDB);
        final int realEndByte = convPosDbToReal(endDB);

        Bits.setBitsOfBytes(value, data, realStartByte, startBit, realEndByte, endBit);
    }

    /**
     * Extract a value of a given bit range and convert it to a scaled one.
     *
     * @param startDB The data byte (EEP order) the value extraction should be start.
     * @param startBit The bit of the start byte the value extraction should be start.
     * @param endDB The data byte (EEP order) the value extraction should be end.
     * @param endBit The bit of the end byte the value extraction should be end.
     * @param rangeMin The lower limit of the range.
     * @param rangeMax The upper limit of the range.
     * @param scaleMin The lower limit of the scaled value.
     * @param scaleMax The upper limit of the scaled value.
     * @return Return a scaled value that does fit in given range.
     * @throws PacketDataScaleValueException This exception is raised if the value extracted from given bit range does
     * not fit in range.
     */
    protected double getScaleValue(int startDB, int startBit, int endDB, int endBit,
                                   long rangeMin, long rangeMax,
                                   double scaleMin, double scaleMax)
            throws PacketDataScaleValueException {
        final long range = getDataRange(startDB, startBit, endDB, endBit);

        /*
         * The range could also be inverse (255..0 instead of 0..255), so we have to improve the check.
         */
        if (range < rangeMin && range < rangeMax
            || range > rangeMin && range > rangeMax) {
            throw new PacketDataScaleValueException(String.format("The coded value does not fit in range (min: %d, max: %d, value: %d).", rangeMin, rangeMax, range));
        }

        final double scale = CalculationUtil.rangeToScale(range, rangeMin, rangeMax, scaleMin, scaleMax);
        return scale;
    }
}
