package org.opencean.core.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.EEPId;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.ButtonState;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.OnOffState;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.BasicPacket;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.data.PacketDataEEPA508;
import org.opencean.core.packets.data.PacketDataEEPA50801;
import org.opencean.core.packets.data.PacketDataEEPA50802;
import org.opencean.core.packets.data.PacketDataEEPA50803;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightTempertureAndOccupancySensor implements EEPParser {

    private static final Logger logger = LoggerFactory.getLogger(LightTempertureAndOccupancySensor.class);

    private final EEPId eep;

    public LightTempertureAndOccupancySensor(EEPId eep) {
        this.eep = eep;
    }

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket4BS) {
            RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;

            PacketDataEEPA508 eepA508;

            if (eep == EEPId.EEP_A5_08_01) {
                eepA508 = new PacketDataEEPA50801(radioPacket4BS.getEEPData());
            } else if (eep == EEPId.EEP_A5_08_02) {
                eepA508 = new PacketDataEEPA50802(radioPacket4BS.getEEPData());
            } else if (eep == EEPId.EEP_A5_08_03) {
                eepA508 = new PacketDataEEPA50803(radioPacket4BS.getEEPData());
            } else {
                logger.warn(String.format("Unknown EEP (%s).", eep.getId()));
                return map;
            }

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.TEMPERATURE),
                    new NumberWithUnit(PacketDataEEPA508.TEMPERATURE_UNIT, new BigDecimal(eepA508.getTemperature(), new MathContext(3)))
            );

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.ILLUMINANCE),
                    new NumberWithUnit(PacketDataEEPA508.ILLUMINATION_UNIT, new BigDecimal(eepA508.getIllumination(), new MathContext(4)))
            );

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.POWER),
                    new NumberWithUnit(PacketDataEEPA508.SUPPLY_VOLTAGE_UNIT, new BigDecimal(eepA508.getSupplyVoltage(), new MathContext(2)))
            );

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.MOVEMENT),
                    eepA508.isPIRStatusOn() ? OnOffState.ON : OnOffState.OFF);

            map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(), Parameter.LEARN_BUTTON),
                    eepA508.isTeachIn() ? ButtonState.PRESSED : ButtonState.RELEASED);

        } else {
            logger.warn("Got something that differs from 4BS radio packet.");
        }

        return map;
    }
}
