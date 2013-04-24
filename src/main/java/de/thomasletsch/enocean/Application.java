package de.thomasletsch.enocean;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.DataInputStream;
import java.util.logging.Logger;

public class Application {

    private static Logger logger = Logger.getLogger("enocean");

    public static void main(String[] args) throws Exception {
    	
    	logger.info( "starting..");
    	
        String port = "/dev/tty.usbserial-FTVQTKRM";
        final SerialPort serialPort;
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        CommPort commPort = portIdentifier.open("java-enocean-library", Constants.TIMEOUT_RESPONSE);
        serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

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
        
        ESP3Host loMessageHost = new ESP3Host();
        
        loMessageHost.receiveRadio(ins);
        
    }
}
