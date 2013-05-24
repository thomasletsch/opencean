package org.enocean.java;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.enocean.java.common.ProtocolConnector;
import org.enocean.java.utils.CircularByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EnOcean connector for serial port communication.
 * 
 * @author Evert van Es
 * @since 1.3.0
 */
public class EnoceanSerialConnector implements ProtocolConnector {

    private static final Logger logger = LoggerFactory.getLogger(EnoceanSerialConnector.class);

    private static final int READ_LIMIT = 2048;

    DataInputStream in = null;
    DataOutputStream out = null;
    SerialPort serialPort = null;
    Thread readerThread = null;

    private CircularByteBuffer buffer;

    public EnoceanSerialConnector() {
    }

    @Override
    public void connect(String device) {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(device);

            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            serialPort = (SerialPort) commPort;
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            in = new DataInputStream(serialPort.getInputStream());
            out = new DataOutputStream(serialPort.getOutputStream());

            out.flush();

            buffer = new CircularByteBuffer(2048);
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    while (true) {
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
            };
            new Thread(runnable).start();

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    logger.info("Closing serialPort");
                    serialPort.close();
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Could not init comm port", e);
        }
    }

    @Override
    public void disconnect() {
        logger.debug("Interrupt serial connection");
        readerThread.interrupt();

        logger.debug("Close serial stream");
        try {
            out.close();
        } catch (IOException e) {
        }

        // Evert: very frustrating, I cannot get the thread to gracefully
        // shutdown when copying a new jar on a running install.
        // somehow the serialport does not get released...

        // logger.debug("Close serial connection");
        // serialPort.removeEventListener();
        // serialPort.close();

        logger.debug("Ready");
    }

    @Override
    public byte get() {
        return buffer.get();
    }

    @Override
    public short getShort() {
        return buffer.getShort();
    }

    @Override
    public void get(byte[] data) {
        buffer.get(data);
    }

    @Override
    public void mark() {
        buffer.mark();
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    @Override
    public void write(byte[] data) {
        try {
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write", e);
        }
    }
}
