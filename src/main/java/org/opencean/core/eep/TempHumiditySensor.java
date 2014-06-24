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
import org.opencean.core.packets.data.PacketDataEEPA504;
import org.opencean.core.packets.data.PacketDataEEPA50401;
import org.opencean.core.packets.data.PacketDataScaleValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempHumiditySensor extends RadioPacket4BSParser {

    private static final Logger logger = LoggerFactory.getLogger(TempHumiditySensor.class);

    public TempHumiditySensor(EEPId eep) {
        super(eep);
    }

    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        if (packet.isTeachInMode()) {
            return;
        }

        PacketDataEEPA504 msg;

        if (eep == EEPId.EEP_A5_04_01) {
            msg = new PacketDataEEPA50401(packet.getEEPData());
        } else {
            logger.warn(String.format("Unknown EEP (%s).", eep.getId()));
            return;
        }

        try {
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.HUMIDITY),
                       new NumberWithUnit(msg.getHumidityUnit(), new BigDecimal(msg.getHumidity(), new MathContext(3))));
        } catch (PacketDataScaleValueException ex) {
            logger.warn("Humidity failed", ex);
        }

        if (msg.isTemperatureAvailable()) {
            try {
                values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.TEMPERATURE),
                           new NumberWithUnit(msg.getTemperatureUnit(), new BigDecimal(msg.getTemperature(), new MathContext(3))));
            } catch (PacketDataScaleValueException ex) {
                logger.warn("Temperature failed", ex);
            }
        }
    }
}
