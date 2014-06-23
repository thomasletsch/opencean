package org.opencean.core.eep;

import java.util.Map;

import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.EEPId;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.Unit;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.LearnButtonState;
import org.opencean.core.packets.RadioPacket4BS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempHumiditySensor extends RadioPacket4BSParser {

    private static final Logger logger = LoggerFactory.getLogger(TempHumiditySensor.class);

    private static final int RANGE_MIN = 0;
    private static final int RANGE_MAX = 250;

    private final int scaleMinHum;
    private final int scaleMaxHum;

    private final int scaleMinTemp;
    private final int scaleMaxTemp;

    private final EEPId eep;
    private LearnButtonState learnButton;

    private final CalculationUtil calculationUtil = new CalculationUtil();

    public TempHumiditySensor(int scaleMinTemp, int scaleMaxTemp, int scaleMinHum, int scaleMaxHum, EEPId eep) {
        this.scaleMinTemp = scaleMinTemp;
        this.scaleMaxTemp = scaleMaxTemp;
        this.scaleMinHum = scaleMinHum;
        this.scaleMaxHum = scaleMaxHum;
        this.eep = eep;
    }

    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        if (!packet.isTeachInMode()) {
            byte db1 = packet.getDb1();
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.TEMPERATURE), new NumberWithUnit(Unit.DEGREE_CELSIUS,
                    calculationUtil.rangeValue(db1, scaleMinTemp, scaleMaxTemp, RANGE_MIN, RANGE_MAX, 3)));
            byte db2 = packet.getDb2();
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.HUMIDITY), new NumberWithUnit(Unit.HUMIDITY,
                    calculationUtil.rangeValue(db2, scaleMinHum, scaleMaxHum, RANGE_MIN, RANGE_MAX, 3)));
        }
    }
}
