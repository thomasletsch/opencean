package org.enocean.java.eep;

import java.util.HashMap;
import java.util.Map;

import org.enocean.java.address.EnoceanParameterAddress;
import org.enocean.java.common.values.NumberWithUnit;
import org.enocean.java.common.values.Unit;
import org.enocean.java.common.values.Value;
import org.enocean.java.packets.BasicPacket;
import org.enocean.java.packets.RadioPacket4BS;

/**
 * Parser for official implementation of a CO2 sensor
 * returns CO2, humidity and temp
 * 
 * @author Nicolas Bonnefond INRIA
 */
public class OfficialCO2Sensor implements EEPParser {

    public static final EEPId EEP_ID = new EEPId("A5:09:04");

    public static final String PARAMETER_CO2 = "CO2_CONCENTRATION";

    public static final String PARAMETER_HUMIDITY = "HUMIDITY_CONCENTRATION";

    public static final String PARAMETER_TEMPERATURE = "TEMPERATURE";

    public static float OFFICIAL_SCALE_MIN = 0;

    public static float OFFICIAL_SCALE_MAX_HUMIDITY = 100;

    public static float OFFICIAL_SCALE_MAX_C02 = 2250;

    public static float OFFICIAL_SCALE_MAX_TEMP = 51;

    private static final float OFFICIAL_BYTE_RANGE_MIN = 0;

    private static final float OFFICIAL_BYTE_RANGE_MAX_CO2_TEMP = 255f;

    private static final float OFFICIAL_BYTE_RANGE_MAX_HUMIDITY = 200f;

    private float rangeMinCO2;

    private float rangeMaxCO2;

    private float rangeMinTemp;

    private float rangeMaxTemp;

    private float rangeMinHumidity;

    private float rangeMaxHumidity;

    private float scaleMinCO2;

    private float scaleMaxCO2;

    private float scaleMinTemp;

    private float scaleMaxTemp;

    private float scaleMinHumidity;

    private float scaleMaxHumidity;

    private float currentValueC02;

    private float currentValueHumidity;

    private float currentValueTemperature;

    /**
     * Instantiates a new official co2 sensor with official enocean spec scales
     */
    public OfficialCO2Sensor() {
	this.rangeMaxCO2 = OFFICIAL_BYTE_RANGE_MAX_CO2_TEMP;
	this.rangeMinCO2 = OFFICIAL_BYTE_RANGE_MIN;
	this.rangeMaxHumidity = OFFICIAL_BYTE_RANGE_MAX_HUMIDITY;
	this.rangeMinHumidity = OFFICIAL_BYTE_RANGE_MIN;
	this.rangeMaxTemp = OFFICIAL_BYTE_RANGE_MAX_CO2_TEMP;
	this.rangeMinTemp = OFFICIAL_BYTE_RANGE_MIN;

	this.scaleMaxCO2 = OFFICIAL_SCALE_MAX_C02;
	this.scaleMinCO2 = OFFICIAL_SCALE_MIN;
	this.scaleMaxHumidity = OFFICIAL_SCALE_MAX_HUMIDITY;
	this.scaleMinHumidity = OFFICIAL_SCALE_MIN;
	this.scaleMaxTemp = OFFICIAL_SCALE_MAX_TEMP;
	this.scaleMinTemp = OFFICIAL_SCALE_MIN;
    }

    /**
     * Instantiates a new official c o2 sensor.
     * 
     * @param rangeMinCO2
     *            the range min c o2
     * @param rangeMaxCO2
     *            the range max c o2
     * @param rangeMinTemp
     *            the range min temp
     * @param rangeMaxTemp
     *            the range max temp
     * @param rangeMinHumidity
     *            the range min humidity
     * @param rangeMaxHumidity
     *            the range max humidity
     * @param scaleMinCO2
     *            the scale min c o2
     * @param scaleMaxCO2
     *            the scale max c o2
     * @param scaleMinTemp
     *            the scale min temp
     * @param scaleMaxTemp
     *            the scale max temp
     * @param scaleMinHumidity
     *            the scale min humidity
     * @param scaleMaxHumidity
     *            the scale max humidity
     */
    public OfficialCO2Sensor(float rangeMinCO2, float rangeMaxCO2,
	    float rangeMinTemp, float rangeMaxTemp, float rangeMinHumidity,
	    float rangeMaxHumidity, float scaleMinCO2, float scaleMaxCO2,
	    float scaleMinTemp, float scaleMaxTemp, float scaleMinHumidity,
	    float scaleMaxHumidity) {

	this.rangeMinCO2 = rangeMinCO2;
	this.rangeMaxCO2 = rangeMaxCO2;
	this.rangeMinTemp = rangeMinTemp;
	this.rangeMaxTemp = rangeMaxTemp;
	this.rangeMinHumidity = rangeMinHumidity;
	this.rangeMaxHumidity = rangeMaxHumidity;
	this.scaleMinCO2 = scaleMinCO2;
	this.scaleMaxCO2 = scaleMaxCO2;
	this.scaleMinTemp = scaleMinTemp;
	this.scaleMaxTemp = scaleMaxTemp;
	this.scaleMinHumidity = scaleMinHumidity;
	this.scaleMaxHumidity = scaleMaxHumidity;
    }

    /**
     * Calculates linear value of byte in the scale
     * 
     * @param source
     *            the source
     * @param scaleMin
     *            the scale min
     * @param scaleMax
     *            the scale max
     * @param byteRangeMin
     *            the byte range min
     * @param byteRangeMax
     *            the byte range max
     * @return the value in the scale
     */
    private float calculateValue(byte source, float scaleMin, float scaleMax,
	    float byteRangeMin, float byteRangeMax) {

	int rawValue = source & 0xFF;
	float multiplier = (scaleMax - scaleMin)
		/ (byteRangeMax - byteRangeMin);
	return multiplier * (rawValue - byteRangeMin) + scaleMin;
    }

    /**
     * Parses DB2 for CO2 , DB3 for Humidity , DB1 for Temperature
     */
    @Override
    public Map<EnoceanParameterAddress, Value> parsePacket(BasicPacket packet) {
	Map<EnoceanParameterAddress, Value> map = new HashMap<EnoceanParameterAddress, Value>();
	if (packet instanceof RadioPacket4BS) {
	    RadioPacket4BS radioPacket4BS = (RadioPacket4BS) packet;

	    byte source = radioPacket4BS.getDb2();
	    this.currentValueC02 = this.calculateValue(source,
		    this.scaleMinCO2, this.scaleMaxCO2, this.rangeMinCO2,
		    this.rangeMaxCO2);

	    source = radioPacket4BS.getDb1();
	    this.currentValueTemperature = this.calculateValue(source,
		    this.scaleMinTemp, this.scaleMaxTemp, this.rangeMinTemp,
		    this.rangeMaxTemp);

	    source = radioPacket4BS.getDb3();
	    this.currentValueHumidity = this.calculateValue(source,
		    this.scaleMinHumidity, this.scaleMaxHumidity,
		    this.rangeMinHumidity, this.rangeMaxHumidity);

	    map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(),
		    PARAMETER_CO2), new NumberWithUnit(Unit.PPM,
		    (int) this.currentValueC02));

	    map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(),
		    PARAMETER_TEMPERATURE), new NumberWithUnit(
		    Unit.DEGREE_CELSIUS, (int) this.currentValueTemperature));

	    map.put(new EnoceanParameterAddress(radioPacket4BS.getSenderId(),
		    PARAMETER_HUMIDITY), new NumberWithUnit(Unit.HUMIDITY,
		    (int) this.currentValueHumidity));
	}
	return map;
    }

}
