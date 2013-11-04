package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

public class EEPParserFactory {

    private Map<EEPId, EEPParser> parser = new HashMap<EEPId, EEPParser>();

    public EEPParserFactory() {
        parser.put(PowerSensor.EEP_ID, new PowerSensor(PowerSensor.EEP_ID));
        parser.put(TemperaturSensor.EEP_ID, new TemperaturSensor(0, 40, TemperaturSensor.EEP_ID));
        parser.put(LightTempertureAndOccupancySensor.EEP_ID, new LightTempertureAndOccupancySensor());
        parser.put(OccupancySensor.EEP_ID, new OccupancySensor());
        parser.put(RockerSwitch.EEP_ID_1, new RockerSwitch());
        parser.put(RockerSwitch.EEP_ID_2, new RockerSwitch());
        parser.put(SingleInputContact.EEP_ID, new SingleInputContact());
        this.parser.put(EltakoLumSensor.EEP_ID, new EltakoLumSensor());
        // parser.put(SimpleCO2Sensor.EEP_ID, new SimpleCO2Sensor(400f, 2550f));
        this.parser.put(OfficialCO2Sensor.EEP_ID, new OfficialCO2Sensor());
        this.parser.put(OfficialVOCSensor.EEP_ID, new OfficialVOCSensor());
    }

    public EEPParser getParserFor(EEPId profile) {
        return parser.get(profile);
    }

}
