package org.enocean.java;

import java.io.DataInputStream;

import org.enocean.java.struct.BasicPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESP3Host {
    private static Logger logger = LoggerFactory.getLogger(ESP3Host.class);

    public void sendRadio() {

    }

    enum ENOCEAN_MSG_STATE {
        GET_SYNC_STATE, GET_HEADER_STATE, CHECK_CRC8H_STATE, GET_DATA_STATE, CHECK_CRC8D_STATE
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
                        logger.info("Received " + readByte);
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
                } else {
                    logger.info("Sync byte received, but header not valid.");
                }
            } catch (Exception e) {
                logger.error("Error", e);
            }
        }
    }

    public void sendRadioSubTel() {

    }

    public void receiveRadioSubTel() {

    }

}
