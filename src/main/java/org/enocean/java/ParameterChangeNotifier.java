package org.enocean.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.ParameterAddress;
import org.enocean.java.common.ParameterValueChangeListener;
import org.enocean.java.eep.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket;

public class ParameterChangeNotifier implements EnoceanMessageListener {

    private List<ParameterValueChangeListener> valueChangeListeners = new ArrayList<ParameterValueChangeListener>();
    private ValueRetriever valueRetriever = new ValueRetriever();

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
            Map<String, Value> values = valueRetriever.retrieveValue(radioPacket);
            for (String parameterId : values.keySet()) {
                for (ParameterValueChangeListener listener : valueChangeListeners) {
                    ParameterAddress parameterAddress = new EnoceanParameterAddress(radioPacket.getSenderId(), parameterId);
                    listener.valueChanged(parameterAddress, values.get(parameterId));
                }
            }
        }
    }

}
