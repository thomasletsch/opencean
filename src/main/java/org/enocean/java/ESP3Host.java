package org.enocean.java;

import java.util.ArrayList;
import java.util.List;

import org.enocean.java.address.EnoceanId;
import org.enocean.java.common.ParameterValueChangeListener;
import org.enocean.java.common.ProtocolConnector;
import org.enocean.java.eep.EEPId;
import org.enocean.java.packets.BasicPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESP3Host extends Thread {
    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    private List<EnoceanReceiver> receivers = new ArrayList<EnoceanReceiver>();

    final ProtocolConnector connector;

    private ParameterChangeNotifier parameterChangeNotifier;

    public ESP3Host(ProtocolConnector connector) {
        this.connector = connector;
        parameterChangeNotifier = new ParameterChangeNotifier();
        parameterChangeNotifier.addParameterValueChangeListener(new LoggingListener());
        receivers.add(parameterChangeNotifier);
    }

    public void addDeviceProfile(EnoceanId id, EEPId epp) {
        parameterChangeNotifier.addDeviceProfile(id, epp);
    }

    public void addParameterChangeListener(ParameterValueChangeListener listener) {
        parameterChangeNotifier.addParameterValueChangeListener(listener);
    }

    public void addListener(EnoceanReceiver receiver) {
        this.receivers.add(receiver);
    }

    public void removeListener(EnoceanReceiver receiver) {
        receivers.remove(receiver);
    }

    public void sendRadio(BasicPacket packet) {
        connector.write(packet.toBytes());
    }

    private void notifyReceivers(BasicPacket receivedPacket) {
        for (EnoceanReceiver receiver : this.receivers) {
            receiver.receivePacket(receivedPacket);
        }
    }

    public void sendRadioSubTel() {

    }

    public void receiveRadioSubTel() {

    }

    @Override
    public void run() {
        logger.info("starting receiveRadio.. ");
        PacketStreamReader receiver = new PacketStreamReader(connector);
        while (true) {
            try {
                BasicPacket receivedPacket = receiver.read();
                if (receivedPacket != null) {
                    logger.info(receivedPacket.toString());
                    notifyReceivers(receivedPacket);
                } else {
                    logger.debug("Sync byte received, but header not valid.");
                }
            } catch (Exception e) {
                logger.error("Error", e);
            }
        }
    }

}
