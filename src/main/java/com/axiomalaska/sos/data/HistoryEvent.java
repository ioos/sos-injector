package com.axiomalaska.sos.data;

import java.util.Calendar;

/**
 * Used in creating the history element for the station sensorML
 *
 *    <!-- HISTORY (OPTIONAL)
        WE HAVEN'T DISCUSSED THIS EXTENSIVELY. Discuss with group and decide 
        what recommendation to make (conventions, etc) -->
      <!-- Per discussion at IOOS DMAC meeting, from comment by Lisa H., 9/11/2012:
        Use this scheme to include "status" messages (in the <gml:description> tag?), 
        like NVS' status updates!! This is doable! Maybe we'd want to implement a set 
        of standardized "event types" vocabulary, for sml:member@name? -->
      <sml:history>
        <sml:EventList>
          <sml:member name="deployment_start">
            <sml:Event>
              <sml:date>2010-01-12</sml:date>
              <gml:description>Deployment start event</gml:description>
              <sml:documentation xlink:href="http://sdftest.ndbc.noaa.gov/sos/server.php?service=SOS&amp;request=DescribeSensor&amp;version=1.0.0&amp;outputformat=text/xml;subtype=&quot;sensorML/1.0.1&quot;&amp;procedure=urn:ioos:station:wmo:41001:20100112"/>
            </sml:Event>
          </sml:member>
          <sml:member name="deployment_stop">
            <sml:Event>
              <sml:date>2011-02-06</sml:date>
              <gml:description>Deployment stop event</gml:description>
              <sml:documentation xlink:href="http://sdftest.ndbc.noaa.gov/sos/server.php?service=SOS&amp;request=DescribeSensor&amp;version=1.0.0&amp;outputformat=text/xml;subtype=&quot;sensorML/1.0.1&quot;&amp;procedure=urn:ioos:station:wmo:41001:20100112"/>
            </sml:Event>
          </sml:member>
          <sml:member name="deployment_start">
            <sml:Event>
              <sml:date>2011-02-07</sml:date>
              <gml:description>Deployment start event</gml:description>
              <sml:documentation xlink:href="http://sdftest.ndbc.noaa.gov/sos/server.php?service=SOS&amp;request=DescribeSensor&amp;version=1.0.0&amp;outputformat=text/xml;subtype=&quot;sensorML/1.0.1&quot;&amp;procedure=urn:ioos:station:wmo:41001:20110207"/>
            </sml:Event>
          </sml:member>
        </sml:EventList>
      </sml:history>
 */
public interface HistoryEvent {

	/**
	 * The name of the member element
	 * 
	 * example: "deployment_start"
	 * example element: <sml:member name="deployment_start">
	 */
	public String getName();
	
	/**
	 * The date of the History Item
	 * Only the year month and day will be used. 
	 * 
	 * example element: <sml:date>2011-02-07</sml:date>
	 */
	public int getYear();
	public int getMonth();
	public int getDay();
	
	/**
	 * A description of the History event
	 * 
	 * example: Deployment start event
	 * example element: <gml:description>Deployment start event</gml:description>
	 */
	public String getDescription();
	
	
	/**
	 * If there is an URL of the event place it here. 
	 * 
	 * example: <sml:documentation xlink:href="http://sdftest.ndbc.noaa.gov/sos/server.php?service=SOS&amp;request=DescribeSensor&amp;version=1.0.0&amp;outputformat=text/xml;subtype=&quot;sensorML/1.0.1&quot;&amp;procedure=urn:ioos:station:wmo:41001:20110207"/>
	 */
	public String getDocumentationUrl();
}
