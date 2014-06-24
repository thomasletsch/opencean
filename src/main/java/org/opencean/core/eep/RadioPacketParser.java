package org.opencean.core.eep;

import org.opencean.core.common.EEPId;

/**
 *
 * @author rathgeb
 */
public abstract class RadioPacketParser implements EEPParser {

    protected final EEPId eep;

    public RadioPacketParser(EEPId eep) {
        this.eep = eep;
    }

}
