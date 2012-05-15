package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Contains a collection of observations for one associated phenomenon and station
 * 
 * The dates, locations and values of the observations are in the same order. Meaning that
 * the first value is associated to the first date and location. 
 * 
 * The location list is only fill if the station is moving. Else the list is empty
 * 
 * @author Lance Finfrock
 */
public class ObservationCollection {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private Station station;
	private Phenomenon phenomenon;
	private List<Double> observationValues = new ArrayList<Double>();
	private List<Calendar> observationDates = new ArrayList<Calendar>();
	private List<Location> observationLocations = new ArrayList<Location>();
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * The phenomenon of the observations
	 */
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	
	/**
	 * The location of each observation
	 */
	public List<Location> getObservationLocations() {
		return observationLocations;
	}

	/**
	 * The associated station to the observations
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * The values of the observations
	 */
	public List<Double> getObservationValues() {
		return observationValues;
	}
	
	/**
	 * The dates of the observations
	 */
	public List<Calendar> getObservationDates() {
		return observationDates;
	}

	public void setObservationLocations(List<Location> observationLocations) {
		this.observationLocations = observationLocations;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}
	
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}

	public void setObservationValues(List<Double> dataValues) {
		this.observationValues = dataValues;
	}

	public void setObservationDates(List<Calendar> dateValues) {
		this.observationDates = dateValues;
	}
	
	public String toString(){
		return station + " " + phenomenon + " items: " + observationDates.size();
	}
}
