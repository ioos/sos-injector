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

To use the code one must create an instance of the ObservationSubmitter with the URL 
of your SOS. There are two ways to add observations to the SOS. First is to push 
observations and second to pull observations. To push observations Call method 
update(ObservationCollection observationCollection) with a prebuilt 
ObservationCollection. This will only add the observations that have not 
already been added to the SOS. To pull observations call the methods
update(List<Station> stations, ObservationRetriever observationRetriever)
or update(Station station, ObservationRetriever observationRetriever).
These methods take one or more stations and a ObservationRetriever. 
Theses methods go through all the combinations of a Station’s Sensor and a 
Sensor’s Phenomena to pull observations from the ObservationRetriever object 
to be placed in the SOS. ObservationRetriever represents an interface to pull 
observations from a database or some data store. 

The SosStation class represents a location that has one or more sensors. In this 
class one has to provide: latitude/longitude, ID, source ID, feature of interest 
name, if the station is moving, and a list of Sensors. The latitude and 
longitude must be in double format. The ID combined with the Source Id needs 
to be unique. An example of an ID is 11111. the source name is the name of the 
source that manages the station (e.g. NOAA). The feature of interest description 
describes where the station is located for example states the station’s name and 
source’s name and that it is in a named bay.

An object must be created with the interface of ObservationRetriever to perform 
a observation pull request from the ObservationSubmitter. This interface has
one method called getObservationCollection that is passed a SosStation, 
SosSensor, SosPhenomenon, and a start date. With these passed in parameters a 
ObservationCollection should be returned containing observations older than 
the start date.

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

The ObservationCollection class is used to hold all the sensor observations 
that are collected. This object holds three lists one for the numeric values, 
dates and location. The values, dates, and locations are related by 
their position in the list. For example the first items in the lists are 
associated to each other then the second items …. The ObservationCollection 
also contains the SosPhenomenon, SosSensor, and SosStation that these 
observations are associated to. The location list is only used if the 
associated station is moving. 

There is a provided example that shows how to perform a pull update to an SOS. 
What the users needs to provides is an SOS of their own. This example is 
located in the source at com.axiomalaska.sos.cnfaic. To run the example:

CnfaicObservationUpdaterFactory factory = new CnfaicObservationUpdaterFactory();

ObservationUpdater observationUpdater 
=factory.buildCnfaicObservationUpdater(“http://localhost/sos/sos”);

observationUpdater.update();

This example contains 5 stations with 3 sensors each. One of the sensor contains 
3 phenomena, while the other only have one. When running this code for the first 
time, 5 stations, 15 sensors, 5 phenomena, 1 network, and around 10,000 observations 
will be added to the SOS. 
