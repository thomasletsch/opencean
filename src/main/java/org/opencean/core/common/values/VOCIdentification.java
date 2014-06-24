package org.opencean.core.common.values;

import org.opencean.core.common.Parameter;

/**
 *
 * @author rathgeb
 */
public enum VOCIdentification {

    /* The parameter suffix must not be changed to keep backward compatibility. */
    VOCT_TOTAL("VOCT (total)", "VOCT_total"),
    FORMALDEHYDE("Formaldehyde", "Formaldehyde"),
    BENZENE("Benzene", "Benzene"),
    STYRENE("Styrene", "Styrene"),
    TOLUENE("Toluene", "Toluene"),
    TETRACHLOROETHYLENE("Tetrachloroethylene", "Tetrachloroethylene"),
    XYLENE("Xylene", "Xylene"),
    HEXANE_N("n-Hexane", "n-Hexane"),
    OCTANE_N("n-Octane", "n-Octane"),
    CYCLOPENTANE("Cyclopentane", "Cyclopentane"),
    METHANOL("Methanol", "Methanol"),
    ETHANOL("Ethanol", "Ethanol"),
    PENTANOL_1("1-Pentanol", "1-Pentanol"),
    ACETONE("Acetone", "Acetone"),
    ETHYLENE_OXIDE("ethylene Oxide", "ethylene_Oxide"),
    ACETALDEHYDE_UE("Acetaldehyde ue", "Acetaldehyde_ue"),
    ACETIC_ACID("Acetic Acid", "Acetic_Acid"),
    PROPIONICE_ACID("Propionice Acid", "Propionice_Acid"),
    VALERIC_ACID("Valeric Acid", "Valeric_Acid"),
    BUTYRIC_ACID("Butyric Acid", "Butyric_Acid"),
    AMMONIAC("Ammoniac", "Ammoniac"),
    HYDROGEN_SULFIDE("Hydrogen Sulfide", "Hydrogen_Sulfide"),
    DIMETHYLSULFIDE("Dimethylsulfide", "Dimethylsulfide"),
    BUTANOL_2("2-Butanol (butyl Alcohol)", "2-Butanol_butyl_Alcohol"),
    METHYLPROPANOL_2("2-Methylpropanol", "2-Methylpropanol"),
    DIETHYL_ETHER("Diethyl ether", "Diethyl_ether"),
    OZONE("ozone", "ozone");

    private final String displayName;
    private final String parameterSuffix;

    private VOCIdentification(final String displayName,
                              final String parameterSuffix) {
        this.displayName = displayName;
        this.parameterSuffix = parameterSuffix;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getParameter() {
        return Parameter.VOC_CONCENTRATION + "_" + parameterSuffix;
    }
}
