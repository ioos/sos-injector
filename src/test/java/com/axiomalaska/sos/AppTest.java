package com.axiomalaska.sos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


import com.axiomalaska.sos.cnfaic.CnfaicObservationRetriever;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.Phenomenon;
import com.axiomalaska.sos.data.Sensor;
import com.axiomalaska.sos.data.Station;
import com.axiomalaska.sos.data.ObservationCollection;

public class AppTest {

	@Test
	public void test() {
		assertTrue(true);
	}
	
	@Test
	public void test4() {
		Pattern pattern = Pattern.compile(".*/(\\w+)");
		
		Matcher matcher = pattern.matcher("lkjasdfljasdflkj/matches");
		
		while(matcher.find()){
			System.out.println(matcher.group(1));
		}
	}
	
	@Test
	public void testCnfaic(){
		PhenomenaBuilder phenomenaBuilder = new PhenomenaBuilder();
		CnfaicObservationRetriever observationRetriever = 
				new CnfaicObservationRetriever();
		
		Station seattle = createSeattleRidge();
		Phenomenon airTemperature = phenomenaBuilder.createAirTemperature();
		
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -1);
		
		Sensor sensor = new Sensor();
		
		sensor.setPhenomenon(airTemperature);
		
		observationRetriever.getObservationCollection(
				seattle, sensor, startDate);
	}
	
	private List<Sensor> getSensors() {
		PhenomenaBuilder phenomenaBuilder = new PhenomenaBuilder();
		List<Sensor> sensors = new ArrayList<Sensor>();
		
		Sensor airTemperatureSensor = new Sensor();
		airTemperatureSensor.setPhenomenon(phenomenaBuilder.createAirTemperature());
		sensors.add(airTemperatureSensor);
	
		Sensor relativeHumiditySensor = new Sensor();
		relativeHumiditySensor.setPhenomenon(phenomenaBuilder.createRelativeHumidity());
		sensors.add(relativeHumiditySensor);
		
		Sensor windSpeedSensor = new Sensor();
		windSpeedSensor.setPhenomenon(phenomenaBuilder.createWindSpeed());
		sensors.add(windSpeedSensor);
		
		Sensor windDirectionSensor = new Sensor();
		windDirectionSensor.setPhenomenon(phenomenaBuilder.createWindfromDirection());
		sensors.add(windDirectionSensor);
		
		Sensor windGustSensor = new Sensor();
		windGustSensor.setPhenomenon(phenomenaBuilder.createWindSpeedofGust());
		sensors.add(windGustSensor);

		return sensors;
	}
	
	private Station createSeattleRidge() {
		Station seattleRidge = new Station();

		seattleRidge
				.setFeatureOfInterestName("At station: Seattle Ridge of source: CNFAIC");
		seattleRidge.setId("seattle");
		Location location = new Location(60.8338, -149.1593);
		seattleRidge.setLocation(location);
		seattleRidge.setMoving(false);
		seattleRidge.setSourceName("CNFAIC");
		seattleRidge.setSensors(getSensors());

		return seattleRidge;
	}
	
	@Test
	public void test3() throws Exception {
//		ObservationRetriever observationRetriever = createObservationRetriever();
//		
//		ObservationUpdater sosSensorBuilder = 
//				new ObservationUpdater("http://sos.axiomalaska.com/sos");
//		
//		Station station = createStation();
//		sosSensorBuilder.update(station, observationRetriever);
	}
	
//	private String formatDate(Calendar date) {
//		Calendar localDate = (Calendar) date.clone();
//
//		// localDate.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
//
////		localDate.setTimeZone(TimeZone.getTimeZone("US/Alaska"));
//		String text = localDate.get(Calendar.YEAR) + "/"
//				+ (localDate.get(Calendar.MONTH) + 1) + "/"
//				+ localDate.get(Calendar.DAY_OF_MONTH) + " "
//				+ localDate.get(Calendar.HOUR_OF_DAY) + ":"
//				+ localDate.get(Calendar.MINUTE) + " "
//				+ localDate.getTimeZone().getID();
//		return text;
//	}
	
	private ObservationRetriever createObservationRetriever(){
		ObservationRetriever observationRetriever = new ObservationRetriever(){
			public ObservationCollection getObservationCollection(Station station, 
					Sensor sensor, 	Calendar startDate){
				ObservationCollection valuesCollection = new ObservationCollection();
				
				valuesCollection.setSensor(sensor);
				valuesCollection.setStation(station);
				
				List<Double> values = new ArrayList<Double>();
				values.add(10.0);
				values.add(11.0);
				values.add(12.0);
				valuesCollection.setObservationValues(values);
				
				List<Calendar> dateValues = new ArrayList<Calendar>();
				
				Calendar baseDate = Calendar.getInstance();
				baseDate.set(2012, Calendar.MAY, 7, 11, 11, 11);
				baseDate.getTime();
				
				for (int count = 0; count < 3; count++) {
					Calendar date = (Calendar)baseDate.clone();
					date.add(Calendar.HOUR_OF_DAY, -4 * count);
					dateValues.add(date);
				}
				
				valuesCollection.setObservationDates(dateValues);
				
				return valuesCollection;
			}
		};
		
		return observationRetriever;
	}
	
	private Station createStation(){
		
		List<Sensor> sensors = new ArrayList<Sensor>();
		Phenomenon airTemPhenomenonDepth20 = new Phenomenon();
		airTemPhenomenonDepth20.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth20.setName("Air Temperature");
		airTemPhenomenonDepth20.setUnits("C");
		Sensor airTem20Sensor = new Sensor();
		airTem20Sensor.setSensorDepth(-20.339, "m");
		
		sensors.add(airTem20Sensor);
		
		Phenomenon airTemPhenomenonDepth10 = new Phenomenon();
		airTemPhenomenonDepth10.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth10.setName("Air Temperature");
		airTemPhenomenonDepth10.setUnits("C");
		
		Sensor airTem10Sensor = new Sensor();
		airTem10Sensor.setSensorDepth(-10.5392, "m");
		
		sensors.add(airTem10Sensor);
		
		Station station = new Station();
		
		station.setLocation(new Location(63.0, -143.0));
		station.setFeatureOfInterestName("Sonoma House - AOOS");
		station.setId("6");
		station.setSensors(sensors);
		
		return station;
	}

}
