#SOS Observation Injector#
===========

### NOTE: THIS SOFTWARE IS IN A DRAFT STATE AND SUBJECT TO SIGNIFICANT CHANGE. PLEASE
DO NOT INTERPRET THE CURRENT CODE AS AN INDICATION OF FINAL INTENDED FORMATS OR DATA
STRUCTURES.###

The SOS Observation Injector is used to enter sensor observations into a SOS. 
To use the code one must create an instance of the ObservationUpdater with the URL 
of your SOS. 

There are two ways to add observations to the SOS. First is to push 
observations and second to pull observations. To push observations Call method 
update(ObservationCollection observationCollection) with a prebuilt 
ObservationCollection. This will only add the observations that have not 
already been added to the SOS. To pull observations call the methods
update(List<Station> stations, ObservationRetriever observationRetriever)
update(Station station, ObservationRetriever observationRetriever)
updateObservations(Station station, Phenomenon phenomenon, ObservationRetriever observationRetriever)
Methods take a one or more stations with option of a phenomenon and a ObservationRetriever. 
All the combinations or the stations and phenomena are used to pull 
observations from the ObservationRetriever object. ObservationRetriever 
represents an interface to pull observations from a database or some data store. 

The Station class represents a location that has one or more sensors. In this 
class one has to provide: latitude/longitude, ID, source name, feature of interest name, if the
station is moving, and a list of phenomena. The latitude and longitude must be in 
double format. The ID is used for a portion of the ID for this station in the SOS. 
Therefore this ID must be unique paired with the source name and can. An example of an 
ID is 11111. the source name is the name of the source that manages the station. 
The feature of interest description describes where the station is located for 
example states the station’s name and source’s name 
and that it is in a named bay. The list of phenomena provides information about 
what sensor reading this station provides. 

An object must be created with the interface of ObservationRetriever to perform 
a observation pull request from the ObservationUpdater. This interface has
one method called getObservationCollection that is passed a station, phenomenon, and 
a start date. With these passed in parameters a ObservationCollection should be returned 
containing observations older than the start date.

A phenomenon is one specific value type that is read from a sensor. For example 
for a wind sensor there would be three phenomena: Wind speed, Wind direction, 
and Wind Gust. For an air temperature there would only be one phenomenon: 
Air Temperature. A phenomenon contains the name (e.g. Air Temperature), 
tag (e.g urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature ), and units 
(e.g. C). Across all the stations the phenomena must be the same. For example 
one can not have a phenomenon from one station for air temperature with units 
of “C” and another with the units of “F”. Even the spelling must be the same, 
so for those two stations with air temperature phenomenon one can not have 
units of “C” and the other of “Celsius”. In the case that a station reads 
observations with the same phenomenon but at different depths the setDepth 
method must be set with the depth of each of these phenomena. 

The ObservationCollection class is used to hold all the sensor observations 
that are collected. This object holds three lists one for the numeric values, 
dates and location. The values, dates, and locations are related by 
their position in the list. For example the first items in the lists are 
associated to each other then the second items …. The ObservationCollection 
also contains the phenomenon and station that these observations are associated to. 
The location list is only used if the assoicated station is moving. 
