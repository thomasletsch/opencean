package org.enocean.java;

import java.io.DataInputStream;
import java.io.IOException;

import org.enocean.java.utils.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnoceanByteStreamPipe implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(EnoceanByteStreamPipe.class);

    private boolean running = true;
    private DataInputStream in = null;
    private CircularByteBuffer buffer;

    public EnoceanByteStreamPipe(DataInputStream in, CircularByteBuffer buffer) {
        this.in = in;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte readByte = in.readByte();
                logger.debug("Received " + readByte);
                buffer.put(readByte);
            } catch (Exception e) {
                logger.error("Error while reading from COM port. Stopping.", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        running = false;
        try {
            in.close();
        } catch (IOException e) {
            logger.error("Error while closing COM port.", e);
        }
    }

}
