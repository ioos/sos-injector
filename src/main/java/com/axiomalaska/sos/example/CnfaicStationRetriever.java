package com.axiomalaska.sos.example;

import java.util.ArrayList;
import java.util.List;

import com.axiomalaska.phenomena.Phenomena;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.phenomena.UnitCreationException;
import com.axiomalaska.sos.StationRetriever;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosNetworkImp;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosSensorImp;
import com.axiomalaska.sos.data.SosSourceImp;
import com.axiomalaska.sos.data.SosStationImp;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.data.SosSource;

public class CnfaicStationRetriever implements StationRetriever {

	private final String STATION_PREFIX = "cnfaic:";
	private SosNetwork rootNetwork;
	
	public CnfaicStationRetriever(SosNetwork rootNetwork){
		this.rootNetwork = rootNetwork;
	}
	
	// -------------------------------------------------------------------------
	// StationRetriever Members
	// -------------------------------------------------------------------------

	public List<SosStation> getStations() throws Exception {

		SosSourceImp source = new SosSourceImp();
		source.setCountry("USA");
		source.setEmail("kevin@chugachavalanche.org");
		source.setName("Chugach Nation Forest Avalanche Information Center");
		source.setWebAddress("www.cnfaic.org");
		source.setOperatorSector("nonprofit");
		source.setAddress("P.O. Box 129");
		source.setCity("Girdwood");
		source.setState("AK");
		source.setZipcode("99587");
		
		
		List<SosStation> stations = new ArrayList<SosStation>();

		stations.add(createSeattleRidge(source));
		stations.add(createSunburstRidge(source));
		stations.add(createFresnoRidge(source));
		stations.add(createMarmotRidge(source));
		stations.add(createArcticValley(source));

		return stations;
	}

	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private SosNetwork getAirTemperatureNetwork(String sourceId){
		SosNetworkImp network = new SosNetworkImp();
		
		network.setId("air_temperature");
		network.setDescription("All Air Temperature Sensors");
		network.setSourceId(sourceId);
		network.setLongName("Network for source " + sourceId + " All air temperature values");
		network.setShortName(sourceId + " air temperature");
		
		return network;
	}
	
	private List<SosSensor> getSensors(SosStation station)throws UnitCreationException {
		SosNetwork airTemperatureNetwork = getAirTemperatureNetwork("cnfaic");
		
		List<SosSensor> sensors = new ArrayList<SosSensor>();
		
		SosSensorImp airTemperatureSensor = new SosSensorImp();
		List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		Phenomenon airTemperature = Phenomena.instance().AIR_TEMPERATURE;
		phenomena.add(airTemperature);
		airTemperatureSensor.setPhenomena(phenomena);
		airTemperatureSensor.setId(findPhenomenonTag(airTemperature));
		airTemperatureSensor.setDescription(airTemperature.getName());
		airTemperatureSensor.addNetwork(airTemperatureNetwork);
		sensors.add(airTemperatureSensor);
	
		SosSensorImp relativeHumiditySensor = new SosSensorImp();
		phenomena = new ArrayList<Phenomenon>();
		Phenomenon relativeHumidity = Phenomena.instance().RELATIVE_HUMIDITY;
		phenomena.add(relativeHumidity);
		relativeHumiditySensor.setPhenomena(phenomena);
		relativeHumiditySensor.setId(findPhenomenonTag(relativeHumidity));
		relativeHumiditySensor.setDescription(relativeHumidity.getName());
		sensors.add(relativeHumiditySensor);
		
		SosSensorImp windSensor = new SosSensorImp();
		phenomena = new ArrayList<Phenomenon>();
		phenomena.add(Phenomena.instance().WIND_SPEED);
		phenomena.add(Phenomena.instance().WIND_FROM_DIRECTION);
		phenomena.add(Phenomena.instance().WIND_SPEED_OF_GUST);
		windSensor.setPhenomena(phenomena);
		windSensor.setId("wind");
		windSensor.setDescription("Wind");
		sensors.add(windSensor);

		return sensors;
	}
	
	private String findPhenomenonTag(Phenomenon phenomenon) {
		int index = phenomenon.getId().lastIndexOf("/") + 1;
		return phenomenon.getId().substring(index);
	}
	
	private SosStation createArcticValley(SosSource source) throws UnitCreationException{
		SosStationImp arcticValley = new SosStationImp();

		arcticValley.setFeatureOfInterestName("At station:Arctic Valley Ridge of source: CNFAIC");
		arcticValley.setId(STATION_PREFIX + "arctic_valley");
		Location location = new Location(61.24, -149.51);
		arcticValley.setLocation(location);
		arcticValley.setSource(source);
		arcticValley.setSensors(getSensors(arcticValley));
		arcticValley.setDescription("http://www.cnfaic.org/wx/wx_arctic.php");
		arcticValley.setName("Arctic Valley Ridge");
		arcticValley.setPlatformType("FIXED MET STATION");
		arcticValley.addNetwork(rootNetwork);

		return arcticValley;
	}

	private SosStation createMarmotRidge(SosSource source) throws UnitCreationException{
		SosStationImp marmot = new SosStationImp();

		marmot.setFeatureOfInterestName("At station: Marmot Ridge in Hatcher Pass of source: CNFAIC");
		marmot.setId(STATION_PREFIX + "marmot");
		Location location = new Location(61.7804, -149.2582);
		marmot.setLocation(location);
		marmot.setSource(source);
		marmot.setSensors(getSensors(marmot));
		marmot.setDescription("http://www.cnfaic.org/wx/wx_marmot.php");
		marmot.setName("Marmot Ridge in Hatcher Pass");
		marmot.setPlatformType("FIXED MET STATION");
		marmot.addNetwork(rootNetwork);

		return marmot;
	}

	private SosStation createFresnoRidge(SosSource source) throws UnitCreationException{
		SosStationImp fresnoRidge = new SosStationImp();

		fresnoRidge
				.setFeatureOfInterestName("At station: Fresno Ridge (Near Summit Lake) of source: CNFAIC");
		fresnoRidge.setId(STATION_PREFIX + "fresno2");
		Location location = new Location(60.6869, -149.5095);
		fresnoRidge.setLocation(location);
		fresnoRidge.setSource(source);
		fresnoRidge.setSensors(getSensors(fresnoRidge));
		fresnoRidge.setDescription("http://www.cnfaic.org/wx/wx_summit.php");
		fresnoRidge.setName("Fresno Ridge (Near Summit Lake)");
		fresnoRidge.setPlatformType("FIXED MET STATION");
		fresnoRidge.addNetwork(rootNetwork);
		
		return fresnoRidge;
	}

	private SosStation createSunburstRidge(SosSource source) throws UnitCreationException{
		SosStationImp sunburstRidge = new SosStationImp();

		sunburstRidge
				.setFeatureOfInterestName("At station: Sunburst Ridge of source: CNFAIC");
		sunburstRidge.setId(STATION_PREFIX + "sunburst");
		Location location = new Location(60.7559, -149.1772);
		sunburstRidge.setLocation(location);
		sunburstRidge.setSource(source);
		sunburstRidge.setSensors(getSensors(sunburstRidge));
		sunburstRidge.setDescription("http://www.cnfaic.org/wx/wx_sunburst.php");
		sunburstRidge.setName("Sunburst Ridge");
		sunburstRidge.setPlatformType("FIXED MET STATION");
		sunburstRidge.addNetwork(rootNetwork);
		
		return sunburstRidge;
	}

	private SosStation createSeattleRidge(SosSource source) throws UnitCreationException{
		SosStationImp seattleRidge = new SosStationImp();

		seattleRidge
				.setFeatureOfInterestName("At station: Seattle Ridge of source: CNFAIC");
		seattleRidge.setId(STATION_PREFIX + "seattle");
		Location location = new Location(60.8338, -149.1593);
		seattleRidge.setLocation(location);
		seattleRidge.setSource(source);
		seattleRidge.setSensors(getSensors(seattleRidge));
		seattleRidge.setDescription("http://www.cnfaic.org/wx/wx_seattle.php");
		seattleRidge.setName("Seattle Ridge");
		seattleRidge.setPlatformType("FIXED MET STATION");
		seattleRidge.addNetwork(rootNetwork);
		
		return seattleRidge;
	}
}
