package com.axiomalaska.sos.data;

public class Sensor {
	private Phenomenon phenomenon;
	private SensorDepth sensorDepth;
	
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	/**
	 * If there is a station with multiple of the same phenomena but different 
	 * depths one must get the depth to differentiate the phenomena. 
	 * @return
	 */
	public SensorDepth getSensorDepth() {
		return sensorDepth;
	}
	public void setSensorDepth(SensorDepth sensorDepth) {
		this.sensorDepth = sensorDepth;
	}
	
	public void setSensorDepth(double value, String units){
		this.sensorDepth = new SensorDepth(value, units);
	}
}
