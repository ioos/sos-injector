package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.List;

public class SosStationImp implements SosStation {
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
	private Location location;
	private String featureOfInterestName = "";
	private String id = "";
	private String name = "";
	private String description = "";
	private SosSource source;
	private String platformType;
	private String wmoId;
	private String sponsor = "";
	private List<SosSensor> sensors = new ArrayList<SosSensor>();
	private List<SosNetwork> networks = new ArrayList<SosNetwork>();
	private List<DocumentMember> documents = new ArrayList<DocumentMember>();
	private List<HistoryEvent> historyItems = new ArrayList<HistoryEvent>();
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * A list of phenomena that this station has readings for
	 */
	public List<SosSensor> getSensors() {
		return sensors;
	}

	/**
	 * This ID should be unique for each station. For example '11111'
	 */
	public String getId() {
		return id;
	}
	
	@Override
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
	@Override
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

	public void setId(String id) {
		this.id = id;
	}
	
	public void setSensors(List<SosSensor> sensors) {
		this.sensors = sensors;
	}
	
	public String toString(){
		return "ID " + id + " " + featureOfInterestName;
	}
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks(){
		return networks;
	}
	
	public void setNetworks(List<SosNetwork> networks){
		this.networks = networks;
	}

	@Override
	public SosSource getSource() {
		return source;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setSponsor(String sponsor){
		this.sponsor = sponsor;
	}
	
	public void setSource(SosSource source) {
		this.source = source;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDocumentation(List<DocumentMember> documents){
		this.documents = documents;
	}
	
	public void setHistoryEvents(List<HistoryEvent> historyEvents){
		this.historyItems = historyEvents;
	}

	public void setName(String name) {
		this.name = name;
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
