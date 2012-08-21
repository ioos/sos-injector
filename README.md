#SOS Observation Injector#
===========

###NOTE: THIS SOFTWARE IS IN A DRAFT STATE AND SUBJECT TO SIGNIFICANT CHANGE.###
###PLEASE DO NOT INTERPRET THE CURRENT CODE AS AN INDICATION OF FINAL INTENDED###
###FORMATS OR DATA STRUCTURES.###

The SOS Observation Injector is used to enter sensor observations into a SOS. 
This code is an adaptor from the model of how most groups store their data 
(Source/Station/Sensor) to allow observations to be placed in an SOS. 
The model I am referring to is one where there is a Source that contains many 
Stations. An example source would be NOAA. Each of those Stations contain one 
or more Sensors. And each of those Sensors reads one or more Phenomena. This 
code allows one to inject data into a SOS by implementing some interfaces that 
are geared towards the common Source/Station/Sensor model. 

Example
-------
There is a provided example in the source code that shows how to perform a pull update to an SOS. 
What the users needs to provides is an SOS of their own. This example is 
located in the source at com.axiomalaska.sos.cnfaic. To run the example use the below code:

import com.axiomalaska.sos.ObservationUpdater;
import com.axiomalaska.sos.cnfaic.CnfaicObservationUpdaterFactory;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.PublisherInfoImp;

		public class Main{
			public static void main(String[] args){

				CnfaicObservationUpdaterFactory factory = new CnfaicObservationUpdaterFactory();

				PublisherInfo publisherInfo = new PublisherInfoImp();

				ObservationUpdater observationUpdater = factory.buildCnfaicObservationUpdater(“http://localhost/sos/sos”, publisherInfo);

				observationUpdater.update();
			}
		}

This example contains 5 stations with 3 sensors each. One of the sensor contains 
3 phenomena, while the other only have one. When running this code for the first 
time, 5 stations, 15 sensors, 5 phenomena, 1 network, and around 10,000 observations 
will be added to the SOS.

This example will be referenced below in explaining to to use this project. 

Open up the CnfaicObservationUpdaterFactory.java file and look at the buildCnfaicObservationUpdater method. This method builds an ObservationUpdater object with a CnfaicStationRetriever (StationRetriever)  and a CnfaicObservationRetriever (ObservationRetriever) object. 

ObservationUpdater
------------------
The ObservationUpdater has one method called update where it uses all the SosStations pulled from the StationRetriever to request observations from the ObservationRetriever object to update the SOS. All the work is done with the use of an ObservationSubmitter object. 

StationRetriever
----------------
The StationRetriever object is used to fetch all the SosStations that are needed to update the SOS. The interface for a StationRetriever is one method called getStations that returns a List of SosStations. An example of a StationRetriever is located at com.axiomalaska.sos.cnfaic.CnfaicStationRetriever. Looking at the CnfaicStationRetriever class, one can see that each SosStation is hard coded and build in this file. The typical use of this interface would be to pull this station data from a database and build the SosStations from it. 

ObservationRetriever
--------------------
This interface has one method called getObservationCollection that is passed a SosStation, 
SosSensor, SosPhenomenon, and a start date. With these passed in parameters an 
ObservationCollection should be returned containing observations older than 
the start date. An example of an ObservationRetriever is located at com.axiomalaska.sos.cnfaic.CnfaicObservationRetriever.java. This class pulls it observations from the www.cnfaic.org website. 

SosStation
----------
The SosStation interface represents a location that has one or more SosSensors. In this 
interface one has to provide: Location, ID, Source, feature of interest 
name, if the station is moving, and a list of Sensors. The Location object stores a latitude and 
longitude in double format. The ID combined with the Source Id needs 
to be unique. An example of an ID is 11111. The feature of interest description 
describes where the station is located for example one could the name of the bay the station is in.

SosSensor
---------
The SosSensor contains a collection of SosPhenomenon and a Id. In the getSensors method in the com.axiomalaska.sos.cnfaic.CnfaicStationRetriever class their are three example SosSensors Air Temperature, Relative Humidity, and Wind. A SosSensor can have more than one Phenomena for example the  Wind sensor has three phenomena: Wind Speed, Wind Direction, and Wind Gust. A SosSensor can also have only one phenomenon like the Air Temperature SosSensor.

SosSource
---------
The SosSource contains information about the ower of a collection of SosStations. Look at the com.axiomalaska.sos.cnfaic.CnfaicStationRetriever getStations method to see an example of a SosSource. 

SosPhenomenon
-------------
A SosPhenomenon is one specific value type that is read from a sensor. For example 
for a wind sensor there would be three phenomena: Wind speed, Wind direction, 
and Wind Gust. For an air temperature there would only be one phenomenon: 
Air Temperature. A phenomenon contains the name (e.g. Air Temperature), 
tag (e.g urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature ), and units 
(e.g. C). Across all the stations the phenomena must be the same. For example 
one can not have a phenomenon from one station for air temperature with units 
of “C” and another with the units of “F”. Even the spelling must be the same, 
so for those two stations with air temperature phenomenon one can not have 
units of “C” and the other of “Celsius”. 

ObservationCollection
---------------------
The ObservationCollection class is used to hold all the sensor observations 
that are collected. This object holds three lists, one for the numeric values, 
dates and location. The values, dates, and locations are related by 
their position in the list. For example the first items in the lists are 
associated to each other then the second items and so on. The ObservationCollection 
also contains the SosPhenomenon, SosSensor, and SosStation that these 
observations are associated to. The location list is only used if the 
associated station is moving. If the station is not move the location list is empty 

ObservationSubmitter
--------------------
This class is the core manager of inputting observations into an SOS. There are two ways to add observations to the SOS. First is to push observations and second to pull observations. To push observations, call the method update(ObservationCollection observationCollection, PublisherInfo publisherInfo) with a prebuilt ObservationCollection. This will only add the observations that have not 
already been added to the SOS. To pull observations call the methods update(List<Station> stations, ObservationRetriever observationRetriever, PublisherInfo publisherInfo)
or update(Station station, ObservationRetriever observationRetriever, PublisherInfo publisherInfo). Which is used by the ObservationUpdater in the CNFAIC example. These methods take one or more stations and a ObservationRetriever. Theses methods go through all the combinations of a Station’s Sensor and a 
Sensor’s Phenomena to pull observations from the ObservationRetriever object 
to be placed in the SOS.
