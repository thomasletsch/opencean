package org.opencean.core.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.ButtonState;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.OnOffState;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.BasicPacket;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.data.PacketDataEEPA50802;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightTempertureAndOccupancySensor implements EEPParser {

    private static final Logger logger = LoggerFactory.getLogger(LightTempertureAndOccupancySensor.class);

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket4BS) {
            RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;

            PacketDataEEPA50802 eepA50802 = new PacketDataEEPA50802(radioPacket4BS.getEEPData());

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.TEMPERATURE),
                    new NumberWithUnit(PacketDataEEPA50802.TEMPERATURE_UNIT, new BigDecimal(eepA50802.getTemperature(), new MathContext(3)))
            );

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.ILLUMINANCE),
                    new NumberWithUnit(PacketDataEEPA50802.ILLUMINATION_UNIT, new BigDecimal(eepA50802.getIllumination(), new MathContext(4)))
            );

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.POWER),
                    new NumberWithUnit(PacketDataEEPA50802.SUPPLY_VOLTAGE_UNIT, new BigDecimal(eepA50802.getSupplyVoltage(), new MathContext(2)))
            );

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.MOVEMENT),
                    eepA50802.isPIRStatusOn() ? OnOffState.ON : OnOffState.OFF);

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.LEARN_BUTTON),
                    eepA50802.isTeachIn() ? ButtonState.PRESSED : ButtonState.RELEASED);

        } else {
            logger.warn("Got something that differs from 4BS radio packet.");
        }

        return map;
    }
}
