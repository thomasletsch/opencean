package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

public class EEPParserFactory {

    private Map<EEPId, EEPParser> parser = new HashMap<EEPId, EEPParser>();

    public EEPParserFactory() {
        parser.put(TemperaturSensor.EEP_ID, new TemperaturSensor(0, 40));
        parser.put(RockerSwitch.EEP_ID_1, new RockerSwitch());
        parser.put(RockerSwitch.EEP_ID_2, new RockerSwitch());
        parser.put(SingleInputContact.EEP_ID, new SingleInputContact());
    }

    public EEPParser getParserFor(EEPId profile) {
        return parser.get(profile);
    }

}
