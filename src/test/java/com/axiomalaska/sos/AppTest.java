package com.axiomalaska.sos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.axiomalaska.phenomena.Phenomena;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.phenomena.PhenomenonImp;
import com.axiomalaska.phenomena.UnitCreationException;
import com.axiomalaska.phenomena.UnitResolver;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.PublisherInfoImp;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosNetworkImp;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosSensorImp;
import com.axiomalaska.sos.data.SosSourceImp;
import com.axiomalaska.sos.data.SosStationImp;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.example.CnfaicObservationUpdaterFactory;
import com.axiomalaska.sos.tools.IdCreator;

public class AppTest {
	@Test
	public void test() {
		assertTrue(true);
	}
	
//	@Test
//	public void testCnfaic() throws Exception{
//		PublisherInfoImp publisherInfo = new PublisherInfoImp();
//		publisherInfo.setCountry("USA");
//		publisherInfo.setEmail("john.doe@gmail.com");
//		publisherInfo.setName("NOAA");
//		publisherInfo.setWebAddress("www.noaa.gov");
//		
//		SosNetworkImp rootNetwork = new SosNetworkImp();
//		rootNetwork.setId("all");
//		rootNetwork.setSourceId("aoos");
//		rootNetwork.setDescription("All inclusive sensor network");
//		rootNetwork.setLongName("All observations");
//		
//		CnfaicObservationUpdaterFactory factory = 
//				new CnfaicObservationUpdaterFactory();
//		
//		ObservationUpdater observationUpdater = factory.buildCnfaicObservationUpdater(
//				"http://ghidorah:8080/52n-sos-ioos-dev/sos", publisherInfo);
//		
//		observationUpdater.update(rootNetwork);
//	}
//	
//	@Test
//	public void testWithDepth() throws Exception {
//		String sosUrl = "http://ghidorah:8080/52n-sos-ioos-dev/sos";
//		ObservationSubmitter observationSubmitter = new ObservationSubmitter(sosUrl);
//		
//		PublisherInfoImp publisherInfo = new PublisherInfoImp();
//		publisherInfo.setCountry("USA");
//		publisherInfo.setEmail("john.doe@gmail.com");
//		publisherInfo.setName("NOAA");
//		publisherInfo.setWebAddress("www.noaa.gov");
//		
//		SosNetworkImp rootNetwork = new SosNetworkImp();
//		rootNetwork.setId("all");
//		rootNetwork.setSourceId("aoos");
//		rootNetwork.setDescription("All inclusive sensor network");
//		rootNetwork.setLongName("All observations");
//		
//		SosSourceImp source = new SosSourceImp();
//		source.setAddress("address");
//		source.setCity("city");
//		source.setCountry("country");
//		source.setEmail("email");
//		source.setId("aoos");
//		source.setName("aoos");
//		source.setOperatorSector("operatorSector");
//		source.setState("state");
//		source.setWebAddress("webAddress");
//		source.setZipcode("zipcode");
//
//		SosStationImp station = new SosStationImp();
//		station.setDescription("description");
//		station.setFeatureOfInterestName("featureOfInterestName");
//		station.setId("aoos:1234");
//		station.setLocation(new Location(68, -144));
//		station.setName("Anchorage");
//		station.setPlatformType("buoy");
//		station.setSource(source);
//
//		ArrayList<SosSensor> sensors = new ArrayList<SosSensor>();
//		sensors.add(createSensor());
//		station.setSensors(sensors);
//		
//		List<Calendar> dates = getDates();
//		for (int count = 0; count < 4; count++) {
//			ObservationCollection observationCollection = new ObservationCollection();
//
//			List<Double> values = getValues(count);
//			
//			observationCollection.setStation(station);
//			observationCollection.setDepth(count*-10.0);
//			observationCollection.setObservationDates(dates);
//			observationCollection.setObservationValues(values);
//			observationCollection
//					.setPhenomenon(Phenomena.instance().SOIL_TEMPERATURE);
//			observationCollection.setSensor(createSensor());
//
//			
//			observationSubmitter.update(rootNetwork, observationCollection,
//					publisherInfo);
//		}
//	}
	
	private List<Calendar> getDates(){
		ArrayList<Calendar> dates = new ArrayList<Calendar>();
		
		Calendar baseDate = Calendar.getInstance();
		for(int count = 0; count < 10 ; count++){
			Calendar date = (Calendar)baseDate.clone();
			date.add(Calendar.DAY_OF_MONTH, count);
			dates.add(date);
		}
		
		return dates;
	}
	
	private List<Double> getValues(int times){
		ArrayList<Double> values = new ArrayList<Double>();
		
		for(int count = 0; count < 10 ; count++){
			values.add((double)count * times);
		}
		
		return values;
	}
	
	private SosSensor createSensor() throws Exception {
		SosSensorImp sensor = new SosSensorImp();
		sensor.setDescription("description");
		sensor.setId("ground_temp");
		
		ArrayList<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		
		phenomena.add(Phenomena.instance().SOIL_TEMPERATURE);
		
		sensor.setPhenomena(phenomena);
		
		return sensor;
	}
	
//	@Test
//	public void test3() throws Exception {
//		ObservationRetriever observationRetriever = createObservationRetriever();
//		
//		ObservationUpdater sosSensorBuilder = 
//				new ObservationUpdater("http://sos.axiomalaska.com/sos");
//		
//		Station station = createStation();
//		sosSensorBuilder.update(station, observationRetriever);
//	}
	
	private String formatDate(Calendar date) {
		Calendar localDate = (Calendar) date.clone();

		// localDate.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))

//		localDate.setTimeZone(TimeZone.getTimeZone("US/Alaska"));
		String text = localDate.get(Calendar.YEAR) + "/"
				+ (localDate.get(Calendar.MONTH) + 1) + "/"
				+ localDate.get(Calendar.DAY_OF_MONTH) + " "
				+ localDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ localDate.get(Calendar.MINUTE) + " "
				+ localDate.getTimeZone().getID();
		return text;
	}
	
	private ObservationRetriever createObservationRetriever(){
		ObservationRetriever observationRetriever = new ObservationRetriever(){
			public List<ObservationCollection> getObservationCollection(SosStation station, 
					SosSensor sensor, Phenomenon phenomenon, Calendar startDate){
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
				
				ArrayList<ObservationCollection> observationCollections = 
						new ArrayList<ObservationCollection>();
				
				observationCollections.add(valuesCollection);
				
				return observationCollections;
			}
		};
		
		return observationRetriever;
	}
	
	private SosStation createStation()  throws UnitCreationException {
		UnitResolver unitResolver = UnitResolver.instance();
		
		List<SosSensor> sensors = new ArrayList<SosSensor>();
		List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		PhenomenonImp airTemPhenomenonDepth20 = new PhenomenonImp();
		airTemPhenomenonDepth20.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth20.setName("Air Temperature");
		airTemPhenomenonDepth20.setUnit(unitResolver.resolveUnit("C"));
		phenomena.add(airTemPhenomenonDepth20);
		SosSensorImp airTem20Sensor = new SosSensorImp();
		airTem20Sensor.setId("Air Temperature");
		airTem20Sensor.setDescription("Air Temperature at 20 meters");
		airTem20Sensor.setPhenomena(phenomena);
		
		sensors.add(airTem20Sensor);
		
		phenomena = new ArrayList<Phenomenon>();
		PhenomenonImp airTemPhenomenonDepth10 = new PhenomenonImp();
		airTemPhenomenonDepth10.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth10.setName("Air Temperature");
		airTemPhenomenonDepth10.setUnit(unitResolver.resolveUnit("C"));
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
