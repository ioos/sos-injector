package com.axiomalaska.sos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.axiomalaska.sos.cnfaic.CnfaicObservationRetriever;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosPhenomenon;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosPhenomenonImp;
import com.axiomalaska.sos.data.SosSensorImp;
import com.axiomalaska.sos.data.SosStationImp;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.data.ObservationCollection;

public class AppTest {

	@Test
	public void test() {
		assertTrue(true);
	}
	
	@Test
	public void testCnfaic2() throws Exception{
		ObservationUpdaterFactory factory = new ObservationUpdaterFactory();
		
		ObservationUpdater observationUpdater = 
				factory.buildCnfaicObservationUpdater("http://192.168.8.15:8080/sos/sos");
		
		observationUpdater.update();
	}
	
	@Test
	public void testCnfaic(){
		PhenomenaBuilder phenomenaBuilder = new PhenomenaBuilder();
		CnfaicObservationRetriever observationRetriever = 
				new CnfaicObservationRetriever();
		
		SosStation seattle = createSeattleRidge();
		SosPhenomenon airTemperature = phenomenaBuilder.createAirTemperature();
		
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -1);
		
		SosSensorImp sensor = new SosSensorImp();
		List<SosPhenomenon> phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(airTemperature);
		sensor.setPhenomena(phenomena);
		
		observationRetriever.getObservationCollection(
				seattle, sensor, airTemperature, startDate);
	}
	
	private List<SosSensor> getSensors() {
		PhenomenaBuilder phenomenaBuilder = new PhenomenaBuilder();
		List<SosSensor> sensors = new ArrayList<SosSensor>();
		
		SosSensorImp airTemperatureSensor = new SosSensorImp();
		List<SosPhenomenon> phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createAirTemperature());
		airTemperatureSensor.setPhenomena(phenomena);
		airTemperatureSensor.setId("Air Temperature");
		sensors.add(airTemperatureSensor);
	
		SosSensorImp relativeHumiditySensor = new SosSensorImp();
		phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createRelativeHumidity());
		relativeHumiditySensor.setPhenomena(phenomena);
		relativeHumiditySensor.setId("Relative Humidity");
		sensors.add(relativeHumiditySensor);
		
		SosSensorImp windSpeedSensor = new SosSensorImp();
		phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createWindSpeed());
		windSpeedSensor.setPhenomena(phenomena);
		windSpeedSensor.setId("Speed Speed");
		sensors.add(windSpeedSensor);
		
		SosSensorImp windDirectionSensor = new SosSensorImp();
		phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createWindfromDirection());
		windDirectionSensor.setPhenomena(phenomena);
		windDirectionSensor.setId("Wind Direction");
		sensors.add(windDirectionSensor);
		
		SosSensorImp windGustSensor = new SosSensorImp();
		phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createWindSpeedofGust());
		windGustSensor.setPhenomena(phenomena);
		windGustSensor.setId("Wind Gust");
		sensors.add(windGustSensor);

		return sensors;
	}
	
	private SosStation createSeattleRidge() {
		SosStationImp seattleRidge = new SosStationImp();

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
			public ObservationCollection getObservationCollection(SosStation station, 
					SosSensor sensor, SosPhenomenon phenomenon, Calendar startDate){
				ObservationCollection valuesCollection = new ObservationCollection();
				
				valuesCollection.setSensor(sensor);
				valuesCollection.setStation(station);
				valuesCollection.setPhenomenon(phenomenon);
				
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
	
	private SosStation createStation(){
		
		List<SosSensor> sensors = new ArrayList<SosSensor>();
		List<SosPhenomenon> phenomena = new ArrayList<SosPhenomenon>();
		SosPhenomenonImp airTemPhenomenonDepth20 = new SosPhenomenonImp();
		airTemPhenomenonDepth20.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth20.setName("Air Temperature");
		airTemPhenomenonDepth20.setUnits("C");
		phenomena.add(airTemPhenomenonDepth20);
		SosSensorImp airTem20Sensor = new SosSensorImp();
		airTem20Sensor.setId("Air Temperature");
		airTem20Sensor.setDescription("Air Temperature at 20 meters");
		airTem20Sensor.setPhenomena(phenomena);
		
		sensors.add(airTem20Sensor);
		
		phenomena = new ArrayList<SosPhenomenon>();
		SosPhenomenonImp airTemPhenomenonDepth10 = new SosPhenomenonImp();
		airTemPhenomenonDepth10.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth10.setName("Air Temperature");
		airTemPhenomenonDepth10.setUnits("C");
		phenomena.add(airTemPhenomenonDepth10);
		SosSensorImp airTem10Sensor = new SosSensorImp();
		airTem10Sensor.setId("Air Temperature");
		airTem10Sensor.setDescription("Air Temperature at 10 meters");
		airTem10Sensor.setPhenomena(phenomena);
		
		sensors.add(airTem10Sensor);
		
		SosStationImp station = new SosStationImp();
		
		station.setLocation(new Location(63.0, -143.0));
		station.setFeatureOfInterestName("Sonoma House - AOOS");
		station.setId("6");
		station.setSensors(sensors);
		
		return station;
	}

}
