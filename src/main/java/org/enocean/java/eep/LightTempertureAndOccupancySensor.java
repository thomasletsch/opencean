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

public class LightTempertureAndOccupancySensor implements EEPParser {

    public static final EEPId EEP_ID = new EEPId("A5:08:02");

    public static final String PARAMETER_ID = "TEMPERATURE";
    public static final String PARAMETER_ID2 = "ILLUMINATION";
    public static final String PARAMETER_ID3 = "SUPPLY_VOLTAGE";
    public static final String PARAMETER_ID4 = "MOVEMENT";

    private static Logger logger = LoggerFactory.getLogger(LightTempertureAndOccupancySensor.class);

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

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID), new NumberWithUnit(Unit.DEGREE_CELSIUS,
                    calculationUtil.calculateRangeValue(db1, 0, 51, 0, 255, 3)));
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID2),
                    new NumberWithUnit(Unit.LUX, calculationUtil.calculateRangeValue(db2, 0, 1020, 0, 255, 4)));
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID3), new NumberWithUnit(Unit.VOLTAGE,
                    calculationUtil.calculateRangeValue(db3, 0, 5.1, 0, 255, 2)));
            ContactState contact = ContactState.values()[(db0 & 0x02) >> 1];
            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), PARAMETER_ID4), contact);
            learnButton = LearnButtonState.values()[(db0 & 0x08) >> 3];
        }
        return map;
    }

}
