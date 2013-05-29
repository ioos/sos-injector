package com.axiomalaska.sos;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.n52.sos.ioos.asset.SensorAsset;
import org.n52.sos.ioos.asset.StationAsset;

import com.axiomalaska.phenomena.Phenomena;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.phenomena.PhenomenonImp;
import com.axiomalaska.phenomena.UnitCreationException;
import com.axiomalaska.phenomena.UnitResolver;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;

public class AppTest {
	@Test
	public void test() {
		assertTrue(true);
	}
	
//	@Test
//	public void testCnfaic() {
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
//		rootNetwork.setShortName("All");
//		
//		CnfaicObservationUpdaterFactory factory = 
//				new CnfaicObservationUpdaterFactory();
//		
//		ObservationUpdater observationUpdater = factory.buildCnfaicObservationUpdater(
//				"http://staging1.axiom:8080/52n-sos-ioos-dev/sos", publisherInfo, rootNetwork);
//		
//		observationUpdater.update(rootNetwork);
//	}
//	
//	@Test
//	public void testWithDepth()  {
//		String sosUrl = "http://staging1.axiom:8080/52n-sos-ioos-dev/sos";
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
//		station.addNetwork(rootNetwork);
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
	
	private SosSensor createSensor() throws UnitCreationException  {
		SosSensor sensor = new SosSensor();
		sensor.setAsset(new SensorAsset("authority","station","ground_temp"));
		sensor.setLongName("description");
		
		ArrayList<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		
		phenomena.add(Phenomena.instance().SOIL_TEMPERATURE);
		
		sensor.setPhenomena(phenomena);
		
		return sensor;
	}
	
//	@Test
//	public void test3()  {
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
			public List<ObservationCollection> getObservationCollection(SosSensor sensor,
			        Phenomenon phenomenon, DateTime startDate){
				ObservationCollection valuesCollection = new ObservationCollection();
				
				valuesCollection.setSensor(sensor);
				valuesCollection.setPhenomenon(phenomenon);
				DateTime baseDate = DateTime.parse("20120507T11:11:11");				
				for (int count = 0; count < 3; count++) {
				    valuesCollection.addObservationValue(baseDate.minusHours(-4 * count), Math.random());
				}

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
		SosSensor airTem20Sensor = new SosSensor();
		airTem20Sensor.setAsset(new SensorAsset("authority","station","air_temperature_20"));
		airTem20Sensor.setLongName("Air Temperature at 20 meters");
		airTem20Sensor.setPhenomena(phenomena);
		
		sensors.add(airTem20Sensor);
		
		phenomena = new ArrayList<Phenomenon>();
		PhenomenonImp airTemPhenomenonDepth10 = new PhenomenonImp();
		airTemPhenomenonDepth10.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth10.setName("Air Temperature");
		airTemPhenomenonDepth10.setUnit(unitResolver.resolveUnit("C"));
		phenomena.add(airTemPhenomenonDepth10);
		SosSensor airTem10Sensor = new SosSensor();
		airTem20Sensor.setAsset(new SensorAsset("authority","station","air_temperature_10"));
		airTem10Sensor.setLongName("Air Temperature at 10 meters");
		airTem10Sensor.setPhenomena(phenomena);
		
		sensors.add(airTem10Sensor);
		
		SosStation station = new SosStation();
		station.setAsset(new StationAsset("authority","station6"));
		station.setLocation(new Location(63.0, -143.0));
		station.setFeatureOfInterestName("Sonoma House - AOOS");
		station.setSensors(sensors);
		
		return station;
	}

}
