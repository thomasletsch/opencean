package org.opencean.core.eep;

import java.util.Map;

import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.EEPId;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.data.PacketDataEEPA50905;
import org.opencean.core.packets.data.PacketDataScaleValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rathgeb
 */
public class VOCSensor extends RadioPacket4BSParser {

    private static final Logger logger = LoggerFactory.getLogger(VOCSensor.class);

    public VOCSensor(EEPId eep) {
        super(eep);
    }

    @Override
    protected void parsePacket(Map<EnoceanParameterAddress, Value> values, RadioPacket4BS packet) {
        PacketDataEEPA50905 msg;

        if (eep == EEPId.EEP_A5_09_05) {
            msg = new PacketDataEEPA50905(packet.getEEPData());
        } else {
            logger.warn(String.format("Unknown EEP (%s).", eep.getId()));
            return;
        }

        try {
            values.put(new EnoceanParameterAddress(packet.getSenderId(), msg.getVOCIdentification().getParameter()),
                       new NumberWithUnit(msg.getVOCConcentrationUnit(), msg.getVOCConcentration()));
        } catch (PacketDataScaleValueException ex) {
            logger.warn("VOC failed", ex);
        }
    }

}
