package org.opencean.core.eep;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.EEPId;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.OnOffState;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.data.PacketDataEEPA50703;
import org.opencean.core.packets.data.PacketDataScaleValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccupancySensor extends RadioPacket4BSParser {

    private static final Logger logger = LoggerFactory.getLogger(OccupancySensor.class);

    public OccupancySensor(EEPId eep) {
        super(eep);
    }

    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        PacketDataEEPA50703 msg;

        if (eep == EEPId.EEP_A5_07_03) {
            msg = new PacketDataEEPA50703(packet.getEEPData());
        } else {
            logger.warn(String.format("Unknown EEP (%s).", eep.getId()));
            return;
        }

        try {
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.POWER),
                       new NumberWithUnit(msg.getSupplyVoltageUnit(), new BigDecimal(msg.getSupplyVoltage(), new MathContext(2))));
        } catch (PacketDataScaleValueException ex) {
            logger.warn("Supply voltage failed", ex);
        }

        try {
            values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.ILLUMINANCE),
                       new NumberWithUnit(msg.getIlluminationUnit(), new BigDecimal(msg.getIllumination(), new MathContext(4))));
        } catch (PacketDataScaleValueException ex) {
            logger.warn("Illumination failed", ex);
        }

        final OnOffState movement = msg.isPIRStatusOn() ? OnOffState.ON : OnOffState.OFF;
        values.put(new EnoceanParameterAddress(packet.getSenderId(), Parameter.MOVEMENT), movement);
    }

}
