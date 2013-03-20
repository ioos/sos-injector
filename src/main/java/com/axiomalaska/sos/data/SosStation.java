package com.axiomalaska.sos.data;

import java.util.List;

/**
 * A station represents a actual physical station. A station contains a location 
 * where one or more phenomena are measured. 
 * 
 * @author Lance Finfrock
 */
public interface SosStation {
	/**
	 * A list of phenomena that this station has readings for
	 */
	public List<SosSensor> getSensors();

	/**
	 * This ID should be unique for each station. For example '11111'
	 */
	public String getId();

	/**
	 * The default name of the location with which the station takes its 
	 * reading from.
	 * 
	 * Maximum characters 80
	 * 
	 * If characters are over 100 they will be truncated to 80
	 */
	public String getFeatureOfInterestName();

	/**
	 * The location of the station
	 */
	public Location getLocation() ;
	
	/**
	 * Name of the source for the station. 
	 * 
	 * @return
	 */
	public SosSource getSource();
	
	
	/**
	 * The name of sponsoring parties (zero-many)
	 * @return
	 */
	public String getSponsor();
	
	public List<DocumentMember> getDocumentation();
	
	public List<HistoryEvent> getHistory();
	
	
	/**
	 * Optional WMO and/or NDBC/CMAN? ID
	 * 
	 * @return if null or "" no WmoID will be created
	 */
	public String getWmoId();
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks();
        
        public void setNetworks(List<SosNetwork> networks);

	public String getDescription();

	public String getName();

	/**
	    BUOY
		WAVE BUOY
		HF RADAR
		DRIFTER
		VESSEL
		PIER
		TOWER
		AUV
		ROV
		GLIDER
		ADCP (SELF-CONTAINED)
		CABLED ARRAY
		FIXED BOTTOM STATION
		SUB-SURFACE MOORING
		AIRCRAFT
		SATELLITE IMAGERY DOWNLINK
		WATER LEVEL STATION
		FIXED SHORE STATION
		FIXED MET STATION
		FIXED WQ STATION
		FIXED TIDE GAGE
		OTHER
	 */
	public String getPlatformType();
}
