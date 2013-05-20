package org.enocean.java;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.DataInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        logger.info("starting..");

        String port = args[0];
        final SerialPort serialPort;
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        CommPort commPort = portIdentifier.open("java-enocean-library", Constants.TIMEOUT_RESPONSE);
        serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                logger.info("Closing serialPort");
                serialPort.close();
            }
        });

        DataInputStream ins = new DataInputStream(serialPort.getInputStream());
        // DataOutputStream outs = new
        // DataOutputStream(serial.getOutputStream());
        // outs.write(b);

        ESP3Host loMessageHost = new ESP3Host();

        loMessageHost.receiveRadio(ins);

    }
}
