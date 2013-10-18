package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.values.ContactState;
import org.enocean.java.common.values.NumberWithUnit;
import org.enocean.java.common.values.Unit;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.LearnButtonState;
import org.enocean.java.packets.RadioPacket4BS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccupancySensor implements EEPParser {

    public static final EEPId EEP_ID = new EEPId("A5:07:03");

    public static final String PARAMETER_ID = "TEMPERATURE";
    public static final String PARAMETER_ID2 = "ILLUMINATION";
    public static final String PARAMETER_ID3 = "SUPPLY_VOLTAGE";
    public static final String PARAMETER_ID4 = "MOVEMENT";

    private static Logger logger = LoggerFactory.getLogger(OccupancySensor.class);

    private LearnButtonState learnButton;

    private CalculationUtil calculationUtil = new CalculationUtil();

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket4BS) {
            RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;
            byte db0 = radioPacket4BS.getDb0();
            byte db1 = radioPacket4BS.getDb1();
            byte db2 = radioPacket4BS.getDb2();
            byte db3 = radioPacket4BS.getDb3();
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID3), new NumberWithUnit(Unit.VOLTAGE,
                    calculationUtil.calculateRangeValue(db3, 0, 5.0, 0, 250, 2)));

            // TODO: This is wrongly calculated. See EEP for details
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID2),
                    new NumberWithUnit(Unit.LUX, calculationUtil.calculateRangeValue(db2, 0, 1020, 0, 255, 4)));
            ContactState contact = ContactState.values()[(db0 & 0x80) >> 7];
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID4), contact);
            learnButton = LearnButtonState.values()[(db0 & 0x08) >> 3];
        }
        return map;
    }

}
