package org.enocean.java;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import org.enocean.java.packets.BasicPacket;
import org.enocean.java.utils.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESP3Host {
    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    private List<EnoceanMessageListener> listeners = new ArrayList<EnoceanMessageListener>();

    public void addListener(EnoceanMessageListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EnoceanMessageListener listener) {
        listeners.remove(listener);
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
                    logger.info("Sync byte received, but header not valid.");
                }
            } catch (Exception e) {
                logger.error("Error", e);
            }
        }
    }

    private void notifyListeners(BasicPacket receivedPacket) {
        for(EnoceanMessageListener listener : this.listeners) {
            listener.receivePacket(receivedPacket);
        }
    }

    public void sendRadioSubTel() {

    }

    public void receiveRadioSubTel() {

    }

}
