package org.enocean.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.enocean.java.address.EnoceanId;
import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.ParameterAddress;
import org.enocean.java.common.ParameterValueChangeListener;
import org.enocean.java.eep.EEPId;
import org.enocean.java.eep.EEPParser;
import org.enocean.java.eep.EEPParserFactory;
import org.enocean.java.eep.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket;

public class ParameterChangeNotifier implements EnoceanMessageListener {

    private List<ParameterValueChangeListener> valueChangeListeners = new ArrayList<ParameterValueChangeListener>();
    private Map<EnoceanId, EEPId> deviceToEEP = new HashMap<EnoceanId, EEPId>();
    private EEPParserFactory parserFactory = new EEPParserFactory();

    public void addDeviceProfile(EnoceanId id, EEPId epp) {
        deviceToEEP.put(id, epp);
    }

    public void addParameterValueChangeListener(ParameterValueChangeListener listener) {
        valueChangeListeners.add(listener);
    }

    public void removeParameterValueChangeListener(ParameterValueChangeListener listener) {
        valueChangeListeners.remove(listener);
    }

    @Override
    public void receivePacket(BasicPacket packet) {
        if (packet instanceof RadioPacket) {
            RadioPacket radioPacket = (RadioPacket) packet;
            Map<String, Value> values = retrieveValue(radioPacket);
            for (String parameterId : values.keySet()) {
                for (ParameterValueChangeListener listener : valueChangeListeners) {
                    ParameterAddress parameterAddress = new EnoceanParameterAddress(radioPacket.getSenderId(), parameterId);
                    listener.valueChanged(parameterAddress, values.get(parameterId));
                }
            }
        }
    }

    private Map<String, Value> retrieveValue(RadioPacket radioPacket) {
        if (deviceToEEP.containsKey(radioPacket.getSenderId())) {
            EEPId profile = deviceToEEP.get(radioPacket.getSenderId());
            EEPParser parser = parserFactory.getParserFor(profile);
            if (profile != null && parser != null) {
                return parser.parsePacket(radioPacket);
            }
        }
        return new HashMap<String, Value>();
    }

}
