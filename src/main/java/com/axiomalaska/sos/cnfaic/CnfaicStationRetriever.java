package com.axiomalaska.sos.cnfaic;

import java.util.ArrayList;
import java.util.List;

import com.axiomalaska.sos.PhenomenaBuilder;
import com.axiomalaska.sos.StationRetriever;
import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.Phenomenon;
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

	private List<Phenomenon> getPhenomena() {
		PhenomenaBuilder phenomenaBuilder = new PhenomenaBuilder();
		List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		
		phenomena.add(phenomenaBuilder.createAirTemperature());
		phenomena.add(phenomenaBuilder.createRelativeHumidity());
		phenomena.add(phenomenaBuilder.createWindSpeed());
		phenomena.add(phenomenaBuilder.createWindfromDirection());
		phenomena.add(phenomenaBuilder.createWindSpeedofGust());

		return phenomena;
	}
	
	private Station createArcticValley() {
		Station marmot = new Station();

		marmot.setFeatureOfInterestName("At station:Arctic Valley Ridge of source: CNFAIC");
		marmot.setId("arctic_valley");
		Location location = new Location(61.24, -149.51);
		marmot.setLocation(location);
		marmot.setMoving(false);
		marmot.setSourceName("CNFAIC");
		marmot.setPhenomena(getPhenomena());

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
		marmot.setPhenomena(getPhenomena());

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
		fresnoRidge.setPhenomena(getPhenomena());

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
		sunburstRidge.setPhenomena(getPhenomena());

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
		seattleRidge.setPhenomena(getPhenomena());

		return seattleRidge;
	}
}
