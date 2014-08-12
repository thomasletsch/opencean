package org.opencean.core;

import java.util.Map;

import org.opencean.core.common.EEPId;
import org.opencean.core.common.ProtocolConnector;
import org.opencean.core.eep.EEPParserFactory;
import org.opencean.core.eep.ExtendedLightingStatus;
import org.opencean.core.eep.RockerSwitch;
import org.opencean.core.eep.TemperaturSensor;
import org.opencean.core.packets.BasicPacket;
import org.opencean.core.packets.RadioPacket;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.RadioPacketRPS;
import org.opencean.core.packets.RockerSwitchCommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stand-alone Application for reading EnOcean packages
 * 
 * @author Thomas Letsch (contact@thomas-letsch.de)
 * 
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static EEPParserFactory eepParserFactory = new EEPParserFactory();

    public static void main(String[] args) throws Exception {
        logger.info("starting..");
        String port = args[0];
        ProtocolConnector connector = new EnoceanSerialConnector();
        connector.connect(port);
        ESP3Host esp3Host = new ESP3Host(connector);
//        BasicPacket packet = new QueryIdCommand();
        
        EnoceanReceiver receiver =  new TestReceiver();
        esp3Host.addListener(receiver);

        RockerSwitchCommandFactory rockerSwitchCommandFactory = new RockerSwitchCommandFactory();
        BasicPacket packet = rockerSwitchCommandFactory.getPacket();
        
        RockerSwitch rockerSwitch = (RockerSwitch) eepParserFactory.getParserFor(EEPId.EEP_F6_02_01);        	
        Map map = rockerSwitch.parsePacket(packet);
        logger.info("RockerSwitch: " + map.toString());
        
        esp3Host.start();
        esp3Host.sendRadio(packet);
    }
}


class TestReceiver implements EnoceanReceiver {

    private static Logger logger = LoggerFactory.getLogger(TestReceiver.class);
    private EEPParserFactory eepParserFactory = new EEPParserFactory();

    @Override
	public void receivePacket(BasicPacket packet) {
        if (packet instanceof RadioPacket) {

        	RadioPacket radioPacket = (RadioPacket) packet;
            
        	if (radioPacket.getSenderId().toString().contentEquals("01:82:0D:02")) {
        		TemperaturSensor temperaturSensor = (TemperaturSensor) eepParserFactory.getParserFor(EEPId.EEP_A5_02_05);        	
                Map map = temperaturSensor.parsePacket(packet);
                logger.info("TemperaturSensor: " + map.toString());
            }
        	  	
        	else if (radioPacket instanceof RadioPacketRPS) {
                RockerSwitch rockerSwitch = (RockerSwitch) eepParserFactory.getParserFor(EEPId.EEP_F6_02_01);        	
                Map map = rockerSwitch.parsePacket(packet);
                logger.info(map.toString());
            }

            else if (radioPacket instanceof RadioPacket4BS) {
            	ExtendedLightingStatus extendedLightingStatus = (ExtendedLightingStatus) eepParserFactory.getParserFor(EEPId.EEP_A5_11_04);        	
                Map map = extendedLightingStatus.parsePacket(packet);
            }
        }
    }
}