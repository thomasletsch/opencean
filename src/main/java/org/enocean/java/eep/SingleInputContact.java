package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket1BS;

public class SingleInputContact implements EEPParser {

    public static final EEPId EEP_ID = new EEPId("D5:00:01");

    public static final String PARAMETER_ID = "CONTACT_STATE";

    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
        Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
        if (packet instanceof RadioPacket1BS) {
            RadioPacket1BS radioPacket1BS = (RadioPacket1BS) packet;
            ContactState contact = ContactState.values()[(radioPacket1BS.getDataByte() & 0x01)];
            map.put(new EnoceanParameterAddress(radioPacket1BS.getSenderId(), PARAMETER_ID), contact);
        }
        return map;
    }

}
