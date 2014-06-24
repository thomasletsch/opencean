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
import org.opencean.core.packets.data.PacketDataEEPA50904;
import org.opencean.core.packets.data.PacketDataScaleValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rathgeb
 */
public class CO2Sensor extends RadioPacket4BSParser {

    private static final Logger logger = LoggerFactory.getLogger(CO2Sensor.class);

    public CO2Sensor(EEPId eep) {
        super(eep);
    }

    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        PacketDataEEPA50904 msg;

        if (eep == EEPId.EEP_A5_09_04) {
            msg = new PacketDataEEPA50904(packet.getEEPData());
        } else {
            logger.warn(String.format("Unknown EEP (%s).", eep.getId()));
            return;
        }

        if (msg.isHumiditySensorAvailable()) {
            try {
                values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.HUMIDITY),
                           new NumberWithUnit(msg.getHumidityUnit(), new BigDecimal(msg.getHumidity(), new MathContext(3))));
            } catch (PacketDataScaleValueException ex) {
                logger.warn("Humidity failed", ex);
            }
        }

        try {
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.CO2_CONCENTRATION),
                       new NumberWithUnit(msg.getConcentrationUnit(), new BigDecimal(msg.getConcentration(), new MathContext(4))));
        } catch (PacketDataScaleValueException ex) {
            logger.warn("CO2 concentration failed", ex);
        }

        if (msg.isTemperatureSensorAvailable()) {
            try {
                values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.TEMPERATURE),
                           new NumberWithUnit(msg.getTemperatureUnit(), new BigDecimal(msg.getTemperature(), new MathContext(3))));
            } catch (PacketDataScaleValueException ex) {
                logger.warn("Temperature failed", ex);
            }
        }

    }

}
