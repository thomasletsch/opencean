package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.common.EEPId;

public class EEPSerializerFactory {

    private Map<EEPId, EEPSerializer> parser = new HashMap<EEPId, EEPSerializer>();

    public EEPSerializerFactory() {
    }

    public EEPSerializer getSerializerFor(EEPId profile) {
        return parser.get(profile);
    }

}
