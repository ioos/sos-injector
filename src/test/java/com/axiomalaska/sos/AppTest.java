package com.axiomalaska.sos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import retriever.ObservationRetriever;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.Phenomenon;
import com.axiomalaska.sos.data.Station;
import com.axiomalaska.sos.data.ObservationCollection;

public class AppTest {

	@Test
	public void test() {
		assertTrue(true);
	}
	
	@Test
	public void test3() throws Exception{
//		ObservationRetriever observationRetriever = createObservationRetriever();
//		
//		ObservationUpdater sosSensorBuilder = 
//				new ObservationUpdater("http://sos.axiomalaska.com/sos");
//		
//		Station station = createStation();
//		sosSensorBuilder.update(station, observationRetriever);
	}
	
//	private String formatDate(Calendar date) {
//		Calendar localDate = (Calendar) date.clone();
//
//		// localDate.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
//
////		localDate.setTimeZone(TimeZone.getTimeZone("US/Alaska"));
//		String text = localDate.get(Calendar.YEAR) + "/"
//				+ (localDate.get(Calendar.MONTH) + 1) + "/"
//				+ localDate.get(Calendar.DAY_OF_MONTH) + " "
//				+ localDate.get(Calendar.HOUR_OF_DAY) + ":"
//				+ localDate.get(Calendar.MINUTE) + " "
//				+ localDate.getTimeZone().getID();
//		return text;
//	}
	
	private ObservationRetriever createObservationRetriever(){
		ObservationRetriever observationRetriever = new ObservationRetriever(){
			public ObservationCollection getObservationCollection(Station station, 
					Phenomenon phenomenon, 	Calendar startDate){
				ObservationCollection valuesCollection = new ObservationCollection();
				
				valuesCollection.setPhenomenon(phenomenon);
				valuesCollection.setStation(station);
				
				List<Double> values = new ArrayList<Double>();
				values.add(10.0);
				values.add(11.0);
				values.add(12.0);
				valuesCollection.setObservationValues(values);
				
				List<Calendar> dateValues = new ArrayList<Calendar>();
				
				Calendar baseDate = Calendar.getInstance();
				baseDate.set(2012, Calendar.MAY, 7, 11, 11, 11);
				baseDate.getTime();
				
				for (int count = 0; count < 3; count++) {
					Calendar date = (Calendar)baseDate.clone();
					date.add(Calendar.HOUR_OF_DAY, -4 * count);
					dateValues.add(date);
				}
				
				valuesCollection.setObservationDates(dateValues);
				
				return valuesCollection;
			}
		};
		
		return observationRetriever;
	}
	
	private Station createStation(){
		
		List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		Phenomenon airTemPhenomenonDepth20 = new Phenomenon();
		airTemPhenomenonDepth20.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth20.setName("Air Temperature");
		airTemPhenomenonDepth20.setUnits("C");
		airTemPhenomenonDepth20.setDepth(-20.339, "m");
		phenomena.add(airTemPhenomenonDepth20);
		
		Phenomenon airTemPhenomenonDepth10 = new Phenomenon();
		airTemPhenomenonDepth10.setId("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenonDepth10.setName("Air Temperature");
		airTemPhenomenonDepth10.setUnits("C");
		airTemPhenomenonDepth10.setDepth(-10.5392, "m");
		phenomena.add(airTemPhenomenonDepth10);
		
		Station station = new Station();
		
		station.setLocation(new Location(63.0, -143.0));
		station.setFeatureOfInterestName("Sonoma House - AOOS");
		station.setId("6");
		station.setPhenomena(phenomena);
		
		return station;
	}

}
