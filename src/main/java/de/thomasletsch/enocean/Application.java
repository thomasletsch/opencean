package de.thomasletsch.enocean;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.DataInputStream;
import java.util.logging.Logger;

public class Application {

    private static Logger logger = Logger.getLogger("enocean");

    public static void main(String[] args) throws Exception {
        String port = "/dev/ttyUSB0";
        final SerialPort serialPort;
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        CommPort commPort = portIdentifier.open("org.openhab.binding.homematic", Constants.TIMEOUT_RESPONSE);
        serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(56700, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                serialPort.close();
            }
        });

        DataInputStream ins = new DataInputStream(serialPort.getInputStream());
        // DataOutputStream outs = new
        // DataOutputStream(serial.getOutputStream());
        // outs.write(b);
        while (true) {
            byte b = (byte) ins.read();
            if (b != -1) {
                logger.info("" + b);
            }
            Thread.sleep(100);
        }
    }
}
