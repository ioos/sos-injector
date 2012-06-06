package com.axiomalaska.sos.cnfaic;

import java.util.ArrayList;
import java.util.List;

import com.axiomalaska.sos.PhenomenaBuilder;
import com.axiomalaska.sos.StationRetriever;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.Sensor;
import com.axiomalaska.sos.data.Station;

public class CnfaicStationRetriever implements StationRetriever {

	// -------------------------------------------------------------------------
	// StationRetriever Members
	// -------------------------------------------------------------------------

	public List<Station> getStations() throws Exception {

		List<Station> stations = new ArrayList<Station>();

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
	
	private Station createArcticValley() {
		Station marmot = new Station();

		marmot.setFeatureOfInterestName("At station:Arctic Valley Ridge of source: CNFAIC");
		marmot.setId("arctic_valley");
		Location location = new Location(61.24, -149.51);
		marmot.setLocation(location);
		marmot.setMoving(false);
		marmot.setSourceName("CNFAIC");
		marmot.setSensors(getSensors());

		return marmot;
	}

	private Station createMarmotRidge() {
		Station marmot = new Station();

		marmot.setFeatureOfInterestName("At station: Marmot Ridge in Hatcher Pass of source: CNFAIC");
		marmot.setId("marmot");
		Location location = new Location(61.7804, -149.2582);
		marmot.setLocation(location);
		marmot.setMoving(false);
		marmot.setSourceName("CNFAIC");
		marmot.setSensors(getSensors());

		return marmot;
	}

	private Station createFresnoRidge() {
		Station fresnoRidge = new Station();

		fresnoRidge
				.setFeatureOfInterestName("At station: Fresno Ridge (Near Summit Lake) of source: CNFAIC");
		fresnoRidge.setId("fresno2");
		Location location = new Location(60.6869, -149.5095);
		fresnoRidge.setLocation(location);
		fresnoRidge.setMoving(false);
		fresnoRidge.setSourceName("CNFAIC");
		fresnoRidge.setSensors(getSensors());

		return fresnoRidge;
	}

	private Station createSunburstRidge() {
		Station sunburstRidge = new Station();

		sunburstRidge
				.setFeatureOfInterestName("At station: Sunburst Ridge of source: CNFAIC");
		sunburstRidge.setId("sunburst");
		Location location = new Location(60.7559, -149.1772);
		sunburstRidge.setLocation(location);
		sunburstRidge.setMoving(false);
		sunburstRidge.setSourceName("CNFAIC");
		sunburstRidge.setSensors(getSensors());

		return sunburstRidge;
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
}
