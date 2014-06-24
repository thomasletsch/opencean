package org.opencean.core.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.EEPId;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.data.PacketDataEEPA502;
import org.opencean.core.packets.data.PacketDataEEPA50205;
import org.opencean.core.packets.data.PacketDataEEPA50220;
import org.opencean.core.packets.data.PacketDataScaleValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperaturSensor extends RadioPacket4BSParser {

    private static final Logger logger = LoggerFactory.getLogger(TemperaturSensor.class);

    public TemperaturSensor(EEPId eep) {
        super(eep);
    }

    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        PacketDataEEPA502 eepA502;

        if (eep == EEPId.EEP_A5_02_05) {
            eepA502 = new PacketDataEEPA50205(packet.getEEPData());
        } else if (eep == EEPId.EEP_A5_02_20) {
            eepA502 = new PacketDataEEPA50220(packet.getEEPData());
        } else {
            logger.warn(String.format("Unknown EEP (%s).", eep.getId()));
            return;
        }

        try {
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.TEMPERATURE),
                       new NumberWithUnit(PacketDataEEPA502.TEMPERATURE_UNIT, new BigDecimal(eepA502.getTemperature(), new MathContext(3))));
        } catch (PacketDataScaleValueException ex) {
            logger.warn("Temperature failed", ex);
        }

    }
}
