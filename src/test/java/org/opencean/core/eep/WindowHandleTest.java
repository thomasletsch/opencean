package org.opencean.core.eep;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.opencean.core.PacketFactory;
import org.opencean.core.address.EnoceanId;
import org.opencean.core.address.EnoceanParameterAddress;
import org.opencean.core.common.values.ButtonState;
import org.opencean.core.common.values.Value;
import org.opencean.core.common.values.WindowHandleState;
import org.opencean.core.eep.RockerSwitch;
import org.opencean.core.packets.BasicPacket;
import org.opencean.core.packets.Header;
import org.opencean.core.packets.Payload;
import org.opencean.core.packets.RadioPacket;
import org.opencean.core.packets.RadioPacketRPS;
import org.opencean.core.packets.RawPacket;
import org.opencean.core.utils.Bits;

public class WindowHandleTest {

    private WindowHandle windowHandle;

    @Before
    public void createWindowHandle() {
    	windowHandle = new WindowHandle();
    }

    @Test
    public void testWindowHandleUp() {
        Map<EnoceanParameterAddress, Value> values = windowHandleMoveMiddleToUp();
        assertEquals("size", 1, values.size());
        Entry<EnoceanParameterAddress, Value> value = values.entrySet().iterator().next();
        assertEquals(WindowHandleState.UP, value.getValue());
    }

    
    @Test
    public void testWindowHandleMiddle() {
  
        Map<EnoceanParameterAddress, Value> values = windowHandleMoveDownToMiddle_1();
        assertEquals("size", 1, values.size());
        Entry<EnoceanParameterAddress, Value> value = values.entrySet().iterator().next();
        assertEquals(WindowHandleState.MIDDLE, value.getValue());
        
        values = windowHandleMoveDownToMiddle_2();
        assertEquals("size", 1, values.size());
        value = values.entrySet().iterator().next();
        assertEquals(WindowHandleState.MIDDLE, value.getValue());
    }
    
    
    @Test
    public void testWindowHandleDown() {
        Map<EnoceanParameterAddress, Value> values = windowHandleMoveMiddleToDown();
        assertEquals("size", 1, values.size());
        Entry<EnoceanParameterAddress, Value> value = values.entrySet().iterator().next();
        assertEquals(WindowHandleState.DOWN, value.getValue());
    }
    

    
    private Map<EnoceanParameterAddress, Value> windowHandleMoveMiddleToUp() {
        byte dataByte = 0;
        // Bit-Sample for bits 7,6,5,4 to move from middle to up: 1:1:0:1
        dataByte = Bits.setBit(dataByte, 7, true); 
        dataByte = Bits.setBit(dataByte, 6, true);  
        dataByte = Bits.setBit(dataByte, 5, false); 
        dataByte = Bits.setBit(dataByte, 4, true); 
        byte statusByte = 0;
        statusByte = Bits.setBit(statusByte, 5, true); // T21
        statusByte = Bits.setBit(statusByte, 4, false); // NU
        RawPacket rawPacket = createRawPacket(dataByte, statusByte);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = windowHandle.parsePacket(basicPacket);
        return values;
    }
    
    private Map<EnoceanParameterAddress, Value> windowHandleMoveDownToMiddle_1() {
        byte dataByte = 0;
        // Bit-Sample for bits 7,6,5,4 to move from down to middle: 1:1:1:0 (1) or 1:1:0:0 (2)
        dataByte = Bits.setBit(dataByte, 7, true); 
        dataByte = Bits.setBit(dataByte, 6, true);  
        dataByte = Bits.setBit(dataByte, 5, true); 
        dataByte = Bits.setBit(dataByte, 4, false); 
        byte statusByte = 0;
        statusByte = Bits.setBit(statusByte, 5, true); // T21
        statusByte = Bits.setBit(statusByte, 4, false); // NU
        RawPacket rawPacket = createRawPacket(dataByte, statusByte);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = windowHandle.parsePacket(basicPacket);
        return values;
    }
    
    private Map<EnoceanParameterAddress, Value> windowHandleMoveDownToMiddle_2() {
        byte dataByte = 0;
        // Bit-Sample for bits 7,6,5,4 to move from down to middle: 1:1:1:0 (1) or 1:1:0:0 (2)
        dataByte = Bits.setBit(dataByte, 7, true); 
        dataByte = Bits.setBit(dataByte, 6, true);  
        dataByte = Bits.setBit(dataByte, 5, false); 
        dataByte = Bits.setBit(dataByte, 4, false); 
        byte statusByte = 0;
        statusByte = Bits.setBit(statusByte, 5, true); // T21
        statusByte = Bits.setBit(statusByte, 4, false); // NU
        RawPacket rawPacket = createRawPacket(dataByte, statusByte);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = windowHandle.parsePacket(basicPacket);
        return values;
    }
    
    private Map<EnoceanParameterAddress, Value> windowHandleMoveMiddleToDown() {
        byte dataByte = 0;
        // Bit-Sample for bits 7,6,5,4 to move from middle to down: 1:1:1:1
        dataByte = Bits.setBit(dataByte, 7, true); 
        dataByte = Bits.setBit(dataByte, 6, true);  
        dataByte = Bits.setBit(dataByte, 5, true); 
        dataByte = Bits.setBit(dataByte, 4, true); 
        byte statusByte = 0;
        statusByte = Bits.setBit(statusByte, 5, true); // T21
        statusByte = Bits.setBit(statusByte, 4, false); // NU
        RawPacket rawPacket = createRawPacket(dataByte, statusByte);
        BasicPacket basicPacket = PacketFactory.createFrom(rawPacket);
        Map<EnoceanParameterAddress, Value> values = windowHandle.parsePacket(basicPacket);
        return values;
    }

    
    
    private RawPacket createRawPacket(byte dataByte, byte statusByte) {
        Header header = new Header(RadioPacket.PACKET_TYPE, (short) 7, (byte) 0);
        Payload payload = new Payload();
        payload.setData(new byte[] { RadioPacketRPS.RADIO_TYPE, dataByte, 0, 0, 0, 0, statusByte });
        RawPacket rawPacket = new RawPacket(header, payload);
        return rawPacket;
    }

}
