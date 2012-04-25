package com.axiomalaska.sos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

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
//		StationUpdater sosSensorBuilder = 
//				new StationUpdater("http://svc.axiomalaska.com:8080/sos/sos");
//		
//		sosSensorBuilder.update(createStation());
	}
	
	private String formatDate(Calendar date) {
		Calendar localDate = (Calendar) date.clone();

		// localDate.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))

//		localDate.setTimeZone(TimeZone.getTimeZone("US/Alaska"));
		String text = localDate.get(Calendar.YEAR) + "/"
				+ (localDate.get(Calendar.MONTH) + 1) + "/"
				+ localDate.get(Calendar.DAY_OF_MONTH) + " "
				+ localDate.get(Calendar.HOUR_OF_DAY) + ":"
				+ localDate.get(Calendar.MINUTE) + " "
				+ localDate.getTimeZone().getID();
		return text;
	}
	
	private Station createStation(){
		Station station = new Station(){
			public ObservationCollection getObservationCollection(Phenomenon phenomenon, 
					Calendar startDate, Calendar endDate) {
				ObservationCollection valuesCollection = new ObservationCollection();
				
				valuesCollection.setPhenomenon(phenomenon);
				
				List<Double> values = new ArrayList<Double>();
				values.add(10.0);
				values.add(11.0);
				values.add(12.0);
				valuesCollection.setObservationValues(values);
				
				List<Calendar> dateValues = new ArrayList<Calendar>();
				
				for (int count = 0; count < 3; count++) {
					Calendar date = Calendar.getInstance();
					date.add(Calendar.HOUR_OF_DAY, -4 * count);
					dateValues.add(date);
				}
				
				valuesCollection.setObservationDates(dateValues);
				
				return valuesCollection;
			}
		};
		
		List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
		Phenomenon airTemPhenomenon = new Phenomenon();
		airTemPhenomenon.setTag("urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature");
		airTemPhenomenon.setName("Air Temperature");
		airTemPhenomenon.setUnits("C");
		phenomena.add(airTemPhenomenon);
		
		station.setFeatureOfInterestId("foi_0");
		station.setLatitude(63.0);
		station.setLongitude(-143.0);
		station.setFoiDescrition("The sampling point at station: Sonoma House - AOOS");
		station.setProcedureId("urn:ogc:object:feature:Sensor:0");
		station.setDescription("Sonoma House");
		station.setPhenomena(phenomena);
		
		return station;
	}

}
