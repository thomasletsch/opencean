package org.enocean.java;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import org.enocean.java.common.ParameterAddress;
import org.enocean.java.common.ParameterValueChangeListener;
import org.enocean.java.common.StandardParameterAddress;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.ParameterMap;
import org.enocean.java.packets.RadioPacket;
import org.enocean.java.utils.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESP3Host {
    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    private List<EnoceanMessageListener> messageListeners = new ArrayList<EnoceanMessageListener>();
    private List<ParameterValueChangeListener> valueChangeListeners = new ArrayList<ParameterValueChangeListener>();

    public void addListener(EnoceanMessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeListener(EnoceanMessageListener listener) {
        messageListeners.remove(listener);
    }

    public void sendRadio() {

    }

    public void receiveRadio(final DataInputStream inputStream) {
        logger.info("starting receiveRadio.. ");
        final CircularByteBuffer buffer = new CircularByteBuffer(2048);
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        byte readByte = inputStream.readByte();
                        logger.debug("Received " + readByte);
                        buffer.put(readByte);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        };
        new Thread(runnable).start();
        PacketReceiver receiver = new PacketReceiver(buffer);
        while (true) {
            try {
                BasicPacket receivedPacket = receiver.receive();
                if (receivedPacket != null) {
                    logger.info(receivedPacket.toString());
                    notifyListeners(receivedPacket);
                } else {
                    logger.debug("Sync byte received, but header not valid.");
                }
            } catch (Exception e) {
                logger.error("Error", e);
            }
        }
    }

    private void notifyListeners(BasicPacket receivedPacket) {
        for(EnoceanMessageListener listener : this.messageListeners) {
            listener.receivePacket(receivedPacket);
        }
        if(receivedPacket instanceof RadioPacket) {
            RadioPacket radioPacket = (RadioPacket) receivedPacket;
            ParameterMap values = radioPacket.getAllParameterValues();
            for(String parameterId : values.keySet()) {
                for(ParameterValueChangeListener listener : valueChangeListeners) {
                    ParameterAddress parameterAddress = new StandardParameterAddress(radioPacket.getSenderId(), parameterId);
                    listener.valueChanged(parameterAddress, values.get(parameterId));
                }
            }
        }
    }

    public void sendRadioSubTel() {

    }

    public void receiveRadioSubTel() {

    }

}
