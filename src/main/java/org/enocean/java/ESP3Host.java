package org.enocean.java;

import java.util.ArrayList;
import java.util.List;

import org.enocean.java.common.ParameterValueChangeListener;
import org.enocean.java.common.ProtocolConnector;
import org.enocean.java.packets.BasicPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESP3Host {
    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    private List<EnoceanMessageListener> messageListeners = new ArrayList<EnoceanMessageListener>();

    final ProtocolConnector connector;

    private ParameterChangeNotifier parameterChangeNotifier;

    public ESP3Host(ProtocolConnector connector) {
        this.connector = connector;
        messageListeners.add(new LoggingListener());
        parameterChangeNotifier = new ParameterChangeNotifier();
        messageListeners.add(parameterChangeNotifier);
    }

    public void addParameterChangeListener(ParameterValueChangeListener listener) {
        parameterChangeNotifier.addParameterValueChangeListener(listener);
    }

    public void addListener(EnoceanMessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeListener(EnoceanMessageListener listener) {
        messageListeners.remove(listener);
    }

    public void sendRadio(BasicPacket packet) {
        connector.write(packet.toBytes());
    }

    public void receiveRadio() {
        logger.info("starting receiveRadio.. ");
        PacketReceiver receiver = new PacketReceiver(connector);
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
        for (EnoceanMessageListener listener : this.messageListeners) {
            listener.receivePacket(receivedPacket);
        }
    }

    public void sendRadioSubTel() {

    }

    public void receiveRadioSubTel() {

    }

}
