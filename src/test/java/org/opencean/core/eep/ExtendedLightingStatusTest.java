package org.opencean.core.eep;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.opencean.core.PacketFactory;
import org.opencean.core.address.EnoceanId;
import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.Parameter;
import org.opencean.core.common.values.Color;
import org.opencean.core.common.values.NumberWithUnit;
import org.opencean.core.common.values.OnOffState;
import org.opencean.core.common.values.Unit;
import org.opencean.core.common.values.Value;
import org.opencean.core.packets.BasicPacket;
import org.opencean.core.packets.Header;
import org.opencean.core.packets.Payload;
import org.opencean.core.packets.RadioPacket;
import org.opencean.core.packets.RadioPacket4BS;
import org.opencean.core.packets.RawPacket;

public class ExtendedLightingStatusTest {

    private ExtendedLightingStatus extendedLightingStatus;
    private EnoceanId sourceEnoceanId = EnoceanId.fromString("FF:21:B1:00");

    @Before
    public void createExtendedLightingStatusSwitch() {
    	extendedLightingStatus = new ExtendedLightingStatus();
    }

    // A5, 11, 86, AD, 2B
    @Test
    public void testRGBOn() {
        byte[] dataBytes = new byte[4];
        dataBytes[0] = (byte) 0x11; // 17  Red (0 .. 255)
        dataBytes[1] = (byte) 0x86; // 134 Green (0 .. 255)
        dataBytes[2] = (byte) 0xAD; // 173 Blue (0 .. 255)
        dataBytes[3] = (byte) 0x2B; // 00101011
                                    // Servcie Mode: Normal Mode
                                    //  Operation hours flag: not available
                                    //   Error State: internal Error
                                    //     Learn Bit: Data Telegram
                                    //      Parameter Mode: RGB
                                    //        Status: 1 ON
        
        Map<EnoceanParameterAddress, Value> values = createBasicPacket(dataBytes);
        
        Color color = (Color) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.COLOR));
        assertEquals(17, color.getRed());
        assertEquals(134, color.getGreen());
        assertEquals(173, color.getBlue());
        
        OnOffState status = (OnOffState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.POWER));
        assertEquals(OnOffState.ON, status);
        
        ExtendedLightingStatus.ErrorState errorState = (ExtendedLightingStatus.ErrorState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.ERROR_STATE));
        assertEquals(ExtendedLightingStatus.ErrorState.INTERNAL_FAILURE, errorState);
    }

    // A5, D2, 05, F3, 59
    @Test
    public void testDimmerOn() {
        byte[] dataBytes = new byte[4];
        dataBytes[0] = (byte) 0xD2; // Dimm-Value (0 .. 255)
        dataBytes[1] = (byte) 0x05; // MSB Lamp operating hours
        dataBytes[2] = (byte) 0xF3; // LSB Lamp operating hours
        dataBytes[3] = (byte) 0x59; // 01011001
                                    // Servcie Mode: Normal Mode
                                    //  Operation hours flag: available
                                    //   Error State: Lamp Error
                                    //     Learn Bit: Data Telegram
                                    //      Parameter Mode: Dimmer
                                    //        Status: 1 ON
        
        Map<EnoceanParameterAddress, Value> values = createBasicPacket(dataBytes);
        
        NumberWithUnit dimmValue = (NumberWithUnit) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.DIMM_VALUE));
        assertEquals(210, ((Number) dimmValue.getValue()).intValue());
        assertEquals(Unit.POINTS, dimmValue.getUnit());
        
        NumberWithUnit lampHoursValue = (NumberWithUnit) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.OPERATING_HOURS));
        assertEquals(1523, ((Number) lampHoursValue.getValue()).intValue());
        assertEquals(Unit.HOURS, lampHoursValue.getUnit());
        
        OnOffState status = (OnOffState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.POWER));
        assertEquals(OnOffState.ON, status);
        
        ExtendedLightingStatus.ErrorState errorState = (ExtendedLightingStatus.ErrorState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.ERROR_STATE));
        assertEquals(ExtendedLightingStatus.ErrorState.LAMP_FAILURE, errorState);
  
        ExtendedLightingStatus.OperationHoursFlag opHourFlag = (ExtendedLightingStatus.OperationHoursFlag) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.OPERATIONS_HOURS_FLAG));
        assertEquals(ExtendedLightingStatus.OperationHoursFlag.HOURS_AVAILABLE, opHourFlag);
    }    

    
    // A5, 01, 18, 04, 7C
    @Test
    public void testEnegeryWhOff() {
        byte[] dataBytes = new byte[4];
        dataBytes[0] = (byte) 0x01; // MSB of Energy Metring Value
        dataBytes[1] = (byte) 0x18; // LSB of Energy Metring Value Total, 280
        dataBytes[2] = (byte) 0x04; // Unit of Energy Value
        dataBytes[3] = (byte) 0x7C; // 01111100
                                    // Servcie Mode: Normal Mode
                                    //  Operation hours flag: lamp operating hours available
                                    //   Error State: Failure on the external periphery
                                    //     Learn Bit: Data Telegram
                                    //      Parameter Mode: Energy metering value
                                    //        Status: 0 Off
        
        Map<EnoceanParameterAddress, Value> values = createBasicPacket(dataBytes);
        
        NumberWithUnit energyValue = (NumberWithUnit) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.ENERGY_METERING_VALUE));
        assertEquals(280, ((Number) energyValue.getValue()).intValue());
        assertEquals(Unit.WATTHOURS, energyValue.getUnit());
        
        OnOffState status = (OnOffState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.POWER));
        assertEquals(OnOffState.OFF, status);
        
        ExtendedLightingStatus.ErrorState errorState = (ExtendedLightingStatus.ErrorState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.ERROR_STATE));
        assertEquals(ExtendedLightingStatus.ErrorState.EXTERNAL_FAILURE, errorState);
        
        ExtendedLightingStatus.OperationHoursFlag opHourFlag = (ExtendedLightingStatus.OperationHoursFlag) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.OPERATIONS_HOURS_FLAG));
        assertEquals(ExtendedLightingStatus.OperationHoursFlag.HOURS_AVAILABLE, opHourFlag);
    }    
    
    
	// Payload: A5, 6D, DG, 01, 0D
    @Test
    public void testEnegeryWOn() {
        byte[] dataBytes = new byte[4];
        dataBytes[0] = (byte) 0x6D; // MSB of Energy Metring Value
        dataBytes[1] = (byte) 0xD6; // LSB of Energy Metring Value Total, 28118
        dataBytes[2] = (byte) 0x01; // Unit of Energy Value
        dataBytes[3] = (byte) 0x0D; // 00001101
                                    // Servcie Mode: Normal Mode
                                    //  Operation hours flag: No lamp operating hours available
                                    //   Error State: No Error Present
                                    //     Learn Bit: Data Telegram
                                    //      Parameter Mode: Energy metering value
                                    //        Status: 1 ON
        
        Map<EnoceanParameterAddress, Value> values = createBasicPacket(dataBytes);
        
        NumberWithUnit energyValue = (NumberWithUnit) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.ENERGY_METERING_VALUE));
        assertEquals(28118, ((Number) energyValue.getValue()).intValue());
        assertEquals(Unit.WATT, energyValue.getUnit());
        
        OnOffState status = (OnOffState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.POWER));
        assertEquals(OnOffState.ON, status);
        
        ExtendedLightingStatus.ErrorState errorState = (ExtendedLightingStatus.ErrorState) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.ERROR_STATE));
        assertEquals(ExtendedLightingStatus.ErrorState.NO_ERROR, errorState);
        
        ExtendedLightingStatus.OperationHoursFlag opHourFlag = (ExtendedLightingStatus.OperationHoursFlag) values.get(new EnoceanParameterAddress(sourceEnoceanId, Parameter.OPERATIONS_HOURS_FLAG));
        assertEquals(ExtendedLightingStatus.OperationHoursFlag.HOURS_NOT_AVAILABLE, opHourFlag);
    }    
    
    private Map<EnoceanParameterAddress, Value> createBasicPacket(byte[] dataBytes) {
        byte statusByte = 0;
        Header header = new Header(RadioPacket.PACKET_TYPE, (short) 7, (byte) 0);
        Payload payload = new Payload();
        byte[] sourceId = sourceEnoceanId.toBytes();
        payload.setData(new byte[] { RadioPacket4BS.RADIO_TYPE, dataBytes[0], dataBytes[1], dataBytes[2], dataBytes[3], sourceId[0], sourceId[1], sourceId[2], sourceId[3], statusByte });
        RawPacket rawPacket = new RawPacket(header, payload);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = extendedLightingStatus.parsePacket(basicPacket);
        return values;
    }
}
