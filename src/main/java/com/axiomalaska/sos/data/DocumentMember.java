package com.axiomalaska.sos.data;

/**
 * Used in creating the document element for the station sensorML
 * 
      <!-- DOCUMENTATION (OPTIONAL) -->
      <!-- COMMENTS:
           - Need to replace "qualityControlDocument with a MMI URI when it is available -->
      <sml:documentation>
        <sml:DocumentList>
          <sml:member name="qc" xlink:arcrole="qualityControlDocument">
            <sml:Document>
              <gml:description>Handbook of Automated Data Quality Control Checks and Procedures, National Data Buoy Center, August 2009</gml:description>
              <sml:format>pdf</sml:format>
              <sml:onlineResource xlink:href="http://www.ndbc.noaa.gov/NDBCHandbookofAutomatedDataQualityControl2009.pdf"/>
            </sml:Document>
          </sml:member>
          <sml:member name="wp1" xlink:arcrole="urn:ogc:def:role:webPage">
            <sml:Document>
              <gml:description>Station web page from provider</gml:description>
              <sml:format>text/html</sml:format>
              <sml:onlineResource xlink:href="STATION_WEBPAGE"/>
            </sml:Document>
          </sml:member>
          <sml:member name="wp2" xlink:arcrole="urn:ogc:def:role:webPage">
            <sml:Document>
              <gml:description>Station web page from operator</gml:description>
              <sml:format>text/html</sml:format>
              <sml:onlineResource xlink:href="STATION_WEBPAGE"/>
            </sml:Document>            
          </sml:member>
        </sml:DocumentList>
      </sml:documentation>
 */
public interface DocumentMember {

	/**
	 * A name of the document member
	 * example element: <sml:member name="qc" xlink:arcrole="qualityControlDocument">
	 */
	public String getName();
	
	/**
	 * 
	 * example element: <sml:member name="qc" xlink:arcrole="qualityControlDocument">
	 */
	public String getArcrole();
	
	/**
	 * A description of the document
	 * 
	 * example: Station web page from operator
	 * example element: <gml:description>Station web page from operator</gml:description>
	 */
	public String getDescription();
	
	/**
	 * The type of document the URL is pointing to
	 * example: "text/html"
	 * example element: <sml:format>text/html</sml:format>
	 */
	public String getFormat();
	
	/**
	 * An URL for this document item
	 * example element: <sml:onlineResource xlink:href="STATION_WEBPAGE"/>
	 */
	public String getOnlineResource();
}
