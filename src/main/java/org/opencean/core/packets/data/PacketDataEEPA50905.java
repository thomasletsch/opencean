package org.opencean.core.packets.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.opencean.core.common.values.Unit;
import org.opencean.core.common.values.VOCIdentification;

/**
 *
 * @author rathgeb
 */
public class PacketDataEEPA50905 extends PacketData4BS {

    public static final long VOC_CONCENTRATION_RANGE_MIN = 0;
    public static final long VOC_CONCENTRATION_RANGE_MAX = 65535;
    public static final double VOC_CONCENTRATION_SCALE_MIN = 0;
    public static final double VOC_CONCENTRATION_SCALE_MAX = 65535;
    public static final Unit VOC_CONCENTRATION_UNIT = Unit.PPB;

    private static final Map<Byte, VOCIdentification> VOC_IDENTIFICATION_MAP = Collections.unmodifiableMap(new HashMap<Byte, VOCIdentification>() {
        private static final long serialVersionUID = 1L;

        {
            this.put((byte) 0, VOCIdentification.VOCT_TOTAL);
            this.put((byte) 1, VOCIdentification.FORMALDEHYDE);
            this.put((byte) 2, VOCIdentification.BENZENE);
            this.put((byte) 3, VOCIdentification.STYRENE);
            this.put((byte) 4, VOCIdentification.TOLUENE);
            this.put((byte) 5, VOCIdentification.TETRACHLOROETHYLENE);
            this.put((byte) 6, VOCIdentification.XYLENE);
            this.put((byte) 7, VOCIdentification.HEXANE_N);
            this.put((byte) 8, VOCIdentification.OCTANE_N);
            this.put((byte) 9, VOCIdentification.CYCLOPENTANE);
            this.put((byte) 10, VOCIdentification.METHANOL);
            this.put((byte) 11, VOCIdentification.ETHANOL);
            this.put((byte) 12, VOCIdentification.PENTANOL_1);
            this.put((byte) 13, VOCIdentification.ACETONE);
            this.put((byte) 14, VOCIdentification.ETHYLENE_OXIDE);
            this.put((byte) 15, VOCIdentification.ACETALDEHYDE_UE);
            this.put((byte) 16, VOCIdentification.ACETIC_ACID);
            this.put((byte) 17, VOCIdentification.PROPIONICE_ACID);
            this.put((byte) 18, VOCIdentification.VALERIC_ACID);
            this.put((byte) 19, VOCIdentification.BUTYRIC_ACID);
            this.put((byte) 20, VOCIdentification.AMMONIAC);
            this.put((byte) 22, VOCIdentification.HYDROGEN_SULFIDE);
            this.put((byte) 23, VOCIdentification.DIMETHYLSULFIDE);
            this.put((byte) 24, VOCIdentification.BUTANOL_2);
            this.put((byte) 25, VOCIdentification.METHYLPROPANOL_2);
            this.put((byte) 26, VOCIdentification.DIETHYL_ETHER);
            this.put((byte) 255, VOCIdentification.OZONE);
        }
    });

    private static final Map<Integer, Float> SCALE_MULTIPLIER_MAP = Collections.unmodifiableMap(new HashMap<Integer, Float>() {
        private static final long serialVersionUID = 1L;

        {
            this.put(0, 0.01f);
            this.put(1, 0.1f);
            this.put(2, 1f);
            this.put(3, 10f);
        }
    });

    public PacketDataEEPA50905(byte[] eepData) {
        super(eepData);
    }

    public long getVOCConcentrationRaw() {
        /*
         * The valid range and the scale use the same range, the whole 16 bits.
         * No conversion or range check necessary.
         */
        final long range = getDataRange(3, 7, 2, 0);
        return range;
    }

    public double getVOCConcentration() throws PacketDataScaleValueException {
        return getVOCConcentrationRaw() * getScaleMultiplier();
    }

    public Unit getVOCConcentrationUnit() {
        return VOC_CONCENTRATION_UNIT;
    }

    public VOCIdentification getVOCIdentification() throws PacketDataScaleValueException {
        byte range = (byte) getDataRange(1, 7, 1, 0);
        final VOCIdentification id = VOC_IDENTIFICATION_MAP.get(range);
        if (id == null) {
            throw new PacketDataScaleValueException(String.format("VOC identification unknown (%d).", range));
        }
        return id;
    }

    public double getScaleMultiplier() throws PacketDataScaleValueException {
        int range = (int) getDataRange(0, 1, 0, 0);
        final Float mult = SCALE_MULTIPLIER_MAP.get(range);
        if (mult == null) {
            throw new PacketDataScaleValueException(String.format("Scale multiplier unknown (%f).", mult));
        }
        return mult;
    }
}
