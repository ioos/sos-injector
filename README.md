SOS Observation Injector
===========

The SOS Observation Injector is used to enter sensor observations into a SOS. 
To use the code one must create an instance of the StationUpdater with the URL 
of your SOS. Then call the method ‘update’ on this instance passing in Station 
objects. 

The Station class represents a location that has one or more sensors. In this 
class one has to provide: description of the station, latitude/longitude, 
procedure ID, feature of interest ID, feature of Interest description, and a 
list of phenomena. The description of the station can be anything for example 
one could state the name of the station and the source’s name that manages the 
station. The latitude and longitude must be in double format. The procedure ID 
is the ID for this station in the SOS. Therefore this ID must be unique and can 
only be one hundred or less characters. An example of an procedure ID 
is urn:ogc:object:feature:Sensor:11111. The feature of interest ID is an ID for 
the location of the station. Normally this would be the unique for each station. 
An example of an feature of interest ID is foi_11111. This ID also has a maximum 
of one hundred characters. The feature of interest description describes where 
the station is located for example states the station’s name and source’s name 
and that it is in a named bay. The list of phenomena provides information about 
what sensor reading this station provides. 

The getObservationCollection method on the Station class is used to provide 
sensor observations from the station. This method needs to be overridden to 
provide observations values for the station. The method is passed a phenomenon, 
startdate and enddate and needs to return a ObservationCollection. The 
ObservationCollection class will contain all the observation values and dates 
found from the station for that phenomenon between the passed in 
startdate and enddate. 

A phenomenon is one specific value type that is read from a sensor. For example 
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
that are collected. This object holds two list one for the numeric values and 
the other for the corresponding dates. The values and the dates are related by 
their position in the list. For example the first items in the lists are 
associated to each other then the second items …. The ObservationCollection 
also contains the phenomenon that these observations are associated to. 
