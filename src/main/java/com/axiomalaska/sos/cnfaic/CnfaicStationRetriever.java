package com.axiomalaska.sos.cnfaic;

import java.util.ArrayList;
import java.util.List;

import com.axiomalaska.sos.PhenomenaBuilder;
import com.axiomalaska.sos.StationRetriever;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosNetworkImp;
import com.axiomalaska.sos.data.SosPhenomenon;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosSensorImp;
import com.axiomalaska.sos.data.SosStationImp;
import com.axiomalaska.sos.data.SosStation;

public class CnfaicStationRetriever implements StationRetriever {

	// -------------------------------------------------------------------------
	// StationRetriever Members
	// -------------------------------------------------------------------------

	public List<SosStation> getStations() throws Exception {

		List<SosStation> stations = new ArrayList<SosStation>();

		stations.add(createSeattleRidge());
		stations.add(createSunburstRidge());
		stations.add(createFresnoRidge());
		stations.add(createMarmotRidge());
		stations.add(createArcticValley());

		return stations;
	}

	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private SosNetwork getAirTemperatureNetwork(String sourceId){
		SosNetworkImp network = new SosNetworkImp();
		
		network.setId("air_temperature");
		network.setSourceId(sourceId);
		
		return network;
	}
	
	private List<SosSensor> getSensors(SosStation station) {
		PhenomenaBuilder phenomenaBuilder = new PhenomenaBuilder();
		SosNetwork airTemperatureNetwork = getAirTemperatureNetwork(station.getSourceId());
		
		List<SosSensor> sensors = new ArrayList<SosSensor>();
		
		SosSensorImp airTemperatureSensor = new SosSensorImp();
		List<SosPhenomenon> phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createAirTemperature());
		airTemperatureSensor.setPhenomena(phenomena);
		airTemperatureSensor.setId("Air_Temperature");
		airTemperatureSensor.setDescription("Air Temperature");
		airTemperatureSensor.setStationId(station.getId());
		airTemperatureSensor.setSourceId(station.getSourceId());
		airTemperatureSensor.addNetwork(airTemperatureNetwork);
		sensors.add(airTemperatureSensor);
	
		SosSensorImp relativeHumiditySensor = new SosSensorImp();
		phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createRelativeHumidity());
		relativeHumiditySensor.setPhenomena(phenomena);
		relativeHumiditySensor.setId("Relative_Humidity");
		relativeHumiditySensor.setDescription("Relative Humidity");
		relativeHumiditySensor.setStationId(station.getId());
		relativeHumiditySensor.setSourceId(station.getSourceId());
		sensors.add(relativeHumiditySensor);
		
		SosSensorImp windSensor = new SosSensorImp();
		phenomena = new ArrayList<SosPhenomenon>();
		phenomena.add(phenomenaBuilder.createWindSpeed());
		phenomena.add(phenomenaBuilder.createWindfromDirection());
		phenomena.add(phenomenaBuilder.createWindSpeedofGust());
		windSensor.setPhenomena(phenomena);
		windSensor.setId("Wind");
		windSensor.setDescription("Wind");
		windSensor.setStationId(station.getId());
		windSensor.setSourceId(station.getSourceId());
		sensors.add(windSensor);

		return sensors;
	}
	
	private SosStation createArcticValley() {
		SosStationImp arcticValley = new SosStationImp();

		arcticValley.setFeatureOfInterestName("At station:Arctic Valley Ridge of source: CNFAIC");
		arcticValley.setId("arctic_valley");
		Location location = new Location(61.24, -149.51);
		arcticValley.setLocation(location);
		arcticValley.setMoving(false);
		arcticValley.setSourceName("CNFAIC");
		arcticValley.setSensors(getSensors(arcticValley));

		return arcticValley;
	}

	private SosStation createMarmotRidge() {
		SosStationImp marmot = new SosStationImp();

		marmot.setFeatureOfInterestName("At station: Marmot Ridge in Hatcher Pass of source: CNFAIC");
		marmot.setId("marmot");
		Location location = new Location(61.7804, -149.2582);
		marmot.setLocation(location);
		marmot.setMoving(false);
		marmot.setSourceName("CNFAIC");
		marmot.setSensors(getSensors(marmot));

		return marmot;
	}

	private SosStation createFresnoRidge() {
		SosStationImp fresnoRidge = new SosStationImp();

		fresnoRidge
				.setFeatureOfInterestName("At station: Fresno Ridge (Near Summit Lake) of source: CNFAIC");
		fresnoRidge.setId("fresno2");
		Location location = new Location(60.6869, -149.5095);
		fresnoRidge.setLocation(location);
		fresnoRidge.setMoving(false);
		fresnoRidge.setSourceName("CNFAIC");
		fresnoRidge.setSensors(getSensors(fresnoRidge));

		return fresnoRidge;
	}

	private SosStation createSunburstRidge() {
		SosStationImp sunburstRidge = new SosStationImp();

		sunburstRidge
				.setFeatureOfInterestName("At station: Sunburst Ridge of source: CNFAIC");
		sunburstRidge.setId("sunburst");
		Location location = new Location(60.7559, -149.1772);
		sunburstRidge.setLocation(location);
		sunburstRidge.setMoving(false);
		sunburstRidge.setSourceName("CNFAIC");
		sunburstRidge.setSensors(getSensors(sunburstRidge));

		return sunburstRidge;
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
		seattleRidge.setSensors(getSensors(seattleRidge));

		return seattleRidge;
	}
}
