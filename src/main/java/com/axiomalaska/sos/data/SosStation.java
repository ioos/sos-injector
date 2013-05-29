package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.List;

import org.n52.sos.ioos.asset.StationAsset;

public class SosStation extends AbstractSosAsset {
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
    private StationAsset asset;
	private Location location;
	private String featureOfInterestName;
	private SosSource source;
	private String platformType;
	private String wmoId;
	private String sponsor;
	private List<SosSensor> sensors = new ArrayList<SosSensor>();
	private List<SosNetwork> networks = new ArrayList<SosNetwork>();
	private List<DocumentMember> documents = new ArrayList<DocumentMember>();
	private List<HistoryEvent> historyItems = new ArrayList<HistoryEvent>();
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
    @Override
    public StationAsset getAsset() {
        return asset;
    }
	
    public void setAsset(StationAsset asset) {
        this.asset = asset;
    }

    /**
	 * A list of phenomena that this station has readings for
	 */
	public List<SosSensor> getSensors() {
		return sensors;
	}
	
	public String getWmoId(){
		return wmoId;
	}
	
	public void setWmoId(String wmoId){
		this.wmoId = wmoId;
	}
	
	/**
	 * The name of sponsoring parties (zero-many)
	 * @return
	 */
	public String getSponsor(){
		return sponsor;
	}
	
	public List<DocumentMember> getDocumentation(){
		return documents;
	}
	
	public List<HistoryEvent> getHistory(){
		return historyItems;
	}

	/**
	 * The default name of the location with which the station takes its 
	 * reading from.
	 * 
	 * Maximum characters 80
	 * 
	 * If characters are over 100 they will be truncated to 80
	 */
	public String getFeatureOfInterestName() {
		return featureOfInterestName;
	}

	/**
	 * The location of the station
	 */
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setFeatureOfInterestName(String featureOfInterestName) {
		this.featureOfInterestName = featureOfInterestName;
	}
	
	public void setSensors(List<SosSensor> sensors) {
		this.sensors = sensors;
	}
		
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks(){
		return networks;
	}
	
	public void setNetworks(List<SosNetwork> networks){
	    this.networks = new ArrayList<SosNetwork>();
	    for (SosNetwork net : networks) {
	        this.networks.add(net);
	    }
	}

	public SosSource getSource() {
		return source;
	}
	
	public void setSponsor(String sponsor){
		this.sponsor = sponsor;
	}
	
	public void setSource(SosSource source) {
		this.source = source;
	}
	
	public void setDocumentation(List<DocumentMember> documents){
		this.documents = documents;
	}
	
	public void setHistoryEvents(List<HistoryEvent> historyEvents){
		this.historyItems = historyEvents;
	}
	
	public String getPlatformType(){
		return platformType;
	}
	
	public void setPlatformType(String platformType){
		this.platformType = platformType;
	}
	
	public void addNetwork(SosNetwork network){
		networks.add(network);
	}
	
	public void addHistoryEvent(HistoryEvent historyEvent) {
		historyItems.add(historyEvent);
	}

	public void addDocumentMember(DocumentMember documentMember) {
		documents.add(documentMember);
	}
}
