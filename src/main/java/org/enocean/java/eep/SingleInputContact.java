package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.Parameter;
import org.enocean.java.common.values.ButtonState;
import org.enocean.java.common.values.ContactState;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket1BS;
import org.enocean.java.utils.Bits;

public class SingleInputContact implements EEPParser {

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket1BS) {
            RadioPacket1BS radioPacket1BS = (RadioPacket1BS) packet;
            ContactState contact = ContactState.values()[Bits.getBit(radioPacket1BS.getDataByte(), 0)];
            map.put(new EnoceanParameterAddress(radioPacket1BS.getSenderId(), Parameter.CONTACT_STATE), contact);
            ButtonState learnButton = Bits.getBool(radioPacket1BS.getDataByte(), 4) ? ButtonState.PRESSED : ButtonState.RELEASED;
            map.put(new EnoceanParameterAddress(radioPacket1BS.getSenderId(), Parameter.LEARN_BUTTON), learnButton);
        }
        return map;
    }
}
