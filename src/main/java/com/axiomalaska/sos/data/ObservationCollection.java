package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class contains a collection of observations for one phenomenon
 * 
 * The dates and values of the observations are in the same order. Meaning that
 * the first value associated to the first date. 
 * 
 * @author Lance Finfrock
 */
public class ObservationCollection {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private Phenomenon phenomenon;
	private List<Double> observationValues = new ArrayList<Double>();
	private List<Calendar> observationDates = new ArrayList<Calendar>();
	
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

	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}

	public void setObservationValues(List<Double> dataValues) {
		this.observationValues = dataValues;
	}

	public void setObservationDates(List<Calendar> dateValues) {
		this.observationDates = dateValues;
	}
}
