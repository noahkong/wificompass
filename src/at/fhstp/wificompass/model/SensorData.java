/*
 * Created on Dec 31, 2011
 * Author: Paul Woelfel
 * Email: frig@frig.at
 */
package at.fhstp.wificompass.model;

import android.hardware.SensorEvent;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sensordata")
public class SensorData extends BaseDaoEnabled<SensorData,Integer>{
	
	@DatabaseField(generatedId = true)
	protected int id;
	
	@DatabaseField
	protected String sensorName;
	
	@DatabaseField
	protected int sensorType;
	
	@DatabaseField
	protected int accuracy;
	
	@DatabaseField
	protected long timestamp;
	
	@DatabaseField
	protected int length;
	
	// not more than 4 values are expected
	@DatabaseField
	protected float value0,value1,value2,value3;
	
	@DatabaseField
	protected float normalizedValue;
	
	public static final String FIELD_TYPE = "sensorType", FIELD_NAME = "sensorName", FIELD_TIMESTAMP = "timestamp";

	
	public SensorData(){
		
	}

	public SensorData(SensorEvent event){
		sensorName=event.sensor.getName();
		sensorType=(event.sensor.getType());
		timestamp=(event.timestamp);
		accuracy=(event.accuracy);

		if (event.values.length >= 1)
			value0=(event.values[0]);
		if (event.values.length >= 2)
			value1=(event.values[1]);
		if (event.values.length >= 3)
			value2=(event.values[2]);
		if (event.values.length >= 4)
			value3=(event.values[3]);
		
		normalizedValue=(value0<0?value0*-1:value0)+(value1<0?value1*-1:value1)+(value2<0?value2*-1:value2)+(value3<0?value3*-1:value3);
		length=event.values.length;
		
		
	}

	/**
	 * @return the sensorName
	 */
	public String getSensorName() {
		return sensorName;
	}



	/**
	 * @param sensorName the sensorName to set
	 */
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}



	/**
	 * @return the sensorType
	 */
	public int getSensorType() {
		return sensorType;
	}



	/**
	 * @param sensorType the sensorType to set
	 */
	public void setSensorType(int sensorType) {
		this.sensorType = sensorType;
	}



	/**
	 * @return the accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}



	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}



	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}



	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}



	/**
	 * @return the value0
	 */
	public float getValue0() {
		return value0;
	}



	/**
	 * @param value0 the value0 to set
	 */
	public void setValue0(float value0) {
		this.value0 = value0;
	}



	/**
	 * @return the value1
	 */
	public float getValue1() {
		return value1;
	}



	/**
	 * @param value1 the value1 to set
	 */
	public void setValue1(float value1) {
		this.value1 = value1;
	}



	/**
	 * @return the value2
	 */
	public float getValue2() {
		return value2;
	}



	/**
	 * @param value2 the value2 to set
	 */
	public void setValue2(float value2) {
		this.value2 = value2;
	}



	/**
	 * @return the value3
	 */
	public float getValue3() {
		return value3;
	}



	/**
	 * @param value3 the value3 to set
	 */
	public void setValue3(float value3) {
		this.value3 = value3;
	}



	/**
	 * @return the normalizedValue
	 */
	public float getNormalizedValue() {
		return normalizedValue;
	}



	/**
	 * @param normalizedValue the normalizedValue to set
	 */
	public void setNormalizedValue(float normalizedValue) {
		this.normalizedValue = normalizedValue;
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return sensorName+" ("+sensorType+") "+timestamp+": "+value0+" "+value1+" "+value2+" "+value3+" "+accuracy+" "+normalizedValue;
	}



	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}



	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	

}
