package com.axiomalaska.sos.xmlbuilder;

import net.opengis.gml.PointType;
import net.opengis.sensorML.x101.ClassificationDocument.Classification.ClassifierList;
import net.opengis.sensorML.x101.ContactDocument.Contact;
import net.opengis.sensorML.x101.ContactInfoDocument.ContactInfo;
import net.opengis.sensorML.x101.ContactInfoDocument.ContactInfo.Address;
import net.opengis.sensorML.x101.ContactListDocument.ContactList;
import net.opengis.sensorML.x101.DocumentDocument.Document;
import net.opengis.sensorML.x101.DocumentListDocument.DocumentList;
import net.opengis.sensorML.x101.EventDocument.Event;
import net.opengis.sensorML.x101.EventListDocument.EventList;
import net.opengis.sensorML.x101.IdentificationDocument.Identification.IdentifierList;
import net.opengis.sensorML.x101.ResponsiblePartyDocument.ResponsibleParty;
import net.opengis.sensorML.x101.SensorMLDocument;
import net.opengis.sensorML.x101.SmlLocation.SmlLocation2;

import com.axiomalaska.ioos.sos.IoosDefConstants;
import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.sos.data.DocumentMember;
import com.axiomalaska.sos.data.HistoryEvent;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosStation;

public class StationSensorMLBuilder extends AbstractSensorMLBuilder  {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

    private SosStation station;
    private PublisherInfo publisherInfo;
    
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

    public StationSensorMLBuilder(SosStation station, PublisherInfo publisherInfo){
        this.station = station;
        this.publisherInfo = publisherInfo;
    }
    
  // ---------------------------------------------------------------------------
  // Public Members
  // ---------------------------------------------------------------------------
    
    /**
     * Build the XML String
     */
    public SensorMLDocument build() {
        createDescription(station.getLongName());
        createName(station.getId());
        createIdentification();
        createClassification();     
        if(station.getNetworks().size() > 0){
            createParentProcedures(station.getNetworks());
        }
        createContacts();
        if (station.getDocumentation() != null && !station.getDocumentation().isEmpty()) {
            createDocumentation();
        }

        if (station.getHistory() != null && !station.getHistory().isEmpty()) { 
            createHistory();
        }

        createLocation();
        createOffering(station);        
        return xbSensorMLDocument;
    }
    
  // ---------------------------------------------------------------------------
  // Private Members
  // ---------------------------------------------------------------------------

    /**
     * 
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
    private void createHistory() {
        EventList xbEventList = xbSystem.addNewHistory().addNewEventList();
        for(HistoryEvent historyEvent : station.getHistory()){
            EventList.Member xbMember = xbEventList.addNewMember();
            xbMember.setName(historyEvent.getName());
            Event xbEvent = xbMember.addNewEvent();
            String dateString = String.format("%1$04d-%2$02d-%3$02d", 
                    historyEvent.getYear(), historyEvent.getMonth(), historyEvent.getDay());            
            xbEvent.setDate(dateString);
            xbEvent.addNewDescription().setStringValue(historyEvent.getDescription());
            xbEvent.addNewDocumentation().setHref(historyEvent.getDocumentationUrl());
        }
    }
    
    /**
     * 
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
    private void createDocumentation() {
        DocumentList xbDocumentList = xbSystem.addNewDocumentation().addNewDocumentList();      
        for (DocumentMember documentMember : station.getDocumentation()) {
            DocumentList.Member xbMember = xbDocumentList.addNewMember();
            xbMember.setName(documentMember.getName());
            xbMember.setArcrole(documentMember.getArcrole());
            Document xbDocument = xbMember.addNewDocument();
            xbDocument.addNewDescription().setStringValue(documentMember.getDescription());
            xbDocument.setFormat(documentMember.getFormat());
            xbDocument.addNewOnlineResource().setHref(documentMember.getOnlineResource());
        }
    }
    
    /**
        <sml:contact xlink:role="http://mmisw.org/ont/ioos/definition/operator">
           <sml:ResponsibleParty>
              <sml:organizationName>OPERATOR ORGANIZATION</sml:organizationName>
              <sml:contactInfo>
                 <sml:address>
                    <sml:country>COUNTRY [USA, COUNTRY NAME, OR "NON-USA"]</sml:country>
                    <sml:electronicMailAddress>EMAIL</sml:electronicMailAddress>
                 </sml:address>
                 <sml:onlineResource xlink:href="http://pnw.buoyoperator.org"/>
              </sml:contactInfo>
           </sml:ResponsibleParty>
        </sml:contact>
     */
    private void createContacts(){
        if (station.getSource() != null || publisherInfo != null) {
            Contact xbContact = xbSystem.addNewContact();
            ContactList xbContactList = xbContact.addNewContactList();

            //operator
            if (station.getSource() != null) {
                ContactList.Member xbMember = xbContactList.addNewMember();
                xbMember.setRole("http://mmisw.org/ont/ioos/definition/operator");
                ResponsibleParty xbResponsibleParty = xbMember.addNewResponsibleParty();
                xbResponsibleParty.setOrganizationName(station.getSource().getName());
                ContactInfo xbContactInfo = xbResponsibleParty.addNewContactInfo();
                Address xbAddress = xbContactInfo.addNewAddress();
                if (station.getSource().getAddress() != null) {
                    xbAddress.addNewDeliveryPoint().setStringValue(station.getSource().getAddress());
                }
                if (station.getSource().getCity() != null) {
                    xbAddress.setCity(station.getSource().getCity());    
                }
                if (station.getSource().getState() != null) {
                    xbAddress.setAdministrativeArea(station.getSource().getState());    
                }
                if (station.getSource().getZipcode() != null) {
                    xbAddress.setPostalCode(station.getSource().getZipcode());    
                }
                if (station.getSource().getCountry() != null) {
                    xbAddress.setCountry(station.getSource().getCountry());    
                }
                if (station.getSource().getEmail() != null) {
                    xbAddress.setElectronicMailAddress(station.getSource().getEmail());    
                }
                if (station.getSource().getWebAddress() != null) {
                    xbContactInfo.addNewOnlineResource().setHref(station.getSource().getWebAddress());
                }
            }

           if (publisherInfo != null) {
                ContactList.Member xbMember = xbContactList.addNewMember();             
                xbMember.setRole("http://mmisw.org/ont/ioos/definition/publisher");
                ResponsibleParty xbResponsibleParty = xbMember.addNewResponsibleParty();
                xbResponsibleParty.setOrganizationName(publisherInfo.getName());
                ContactInfo xbContactInfo = xbResponsibleParty.addNewContactInfo();
                Address xbAddress = xbContactInfo.addNewAddress();
                if (publisherInfo.getCountry() != null) {
                    xbAddress.setCountry(publisherInfo.getCountry());
                }
                if (publisherInfo.getEmail() != null) {
                    xbAddress.setElectronicMailAddress(publisherInfo.getEmail());
                }
                if (publisherInfo.getWebAddress() != null) {
                    xbContactInfo.addNewOnlineResource().setHref(publisherInfo.getWebAddress());
                }
            }
        }       
    }

    /**
     * Produces the XML below
         <sml:classification>
            <sml:ClassifierList>
              <sml:classifier name="platformType">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/platformType">
                  <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/platform"/>
                  <sml:value>buoy</sml:value>
                </sml:Term>
              </sml:classifier>
              <sml:classifier name="operatorSector">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/operatorSector">
                  <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/sector"/>
                  <sml:value>academic</sml:value>
                </sml:Term>
              </sml:classifier>
              <sml:classifier name="publisher">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/publisher">
                  <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/organization"/>
                  <sml:value>RAWS</sml:value>
                </sml:Term>
              </sml:classifier>
              <sml:classifier name="sponsor">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/sponsor">
                  <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/organization"/>
                  <sml:value>ACE</sml:value>
                </sml:Term>
              </sml:classifier>
              <sml:classifier name="parentNetwork">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/parentNetwork">
                  <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/organization"/>
                  <sml:value>AOOS</sml:value>
                </sml:Term>
              </sml:classifier>
            </sml:ClassifierList>
         </sml:classification>
     */
    private void createClassification() {
        ClassifierList xbClassifierList = xbSystem.addNewClassification().addNewClassifierList();
        
        createClassifier(xbClassifierList, IoosDefConstants.PLATFORM_TYPE, 
                IoosDefConstants.PLATFORM_TYPE_DEF, IoosSosConstants.PLATFORM_ONTOLOGY,
                station.getPlatformType());
        
        createClassifier(xbClassifierList, IoosDefConstants.OPERATOR_SECTOR, 
                IoosDefConstants.OPERATOR_SECTOR_DEF, IoosSosConstants.SECTOR_ONTOLOGY,
                station.getSource().getOperatorSector());
        
        createClassifier(xbClassifierList, IoosDefConstants.PUBLISHER, 
                IoosDefConstants.PUBLISHER_DEF, IoosSosConstants.ORGANIZATION_ONTOLOGY,
                station.getSource().getName());
        
        createClassifier(xbClassifierList, IoosDefConstants.SPONSOR, 
                IoosDefConstants.SPONSOR_DEF, IoosSosConstants.ORGANIZATION_ONTOLOGY,
                station.getSponsor());
        
        if (publisherInfo != null) {
            createClassifier(xbClassifierList, IoosDefConstants.PARENT_NETWORK, 
                    IoosDefConstants.PARENT_NETWORK_DEF, IoosSosConstants.ORGANIZATION_ONTOLOGY,
                    publisherInfo.getName());
        }
    }

    /**
    <sml:location>
      <gml:Point srsName="http://www.opengis.net/def/crs/EPSG/0/4326">
        <gml:pos>34.7 -72.73</gml:pos>
      </gml:Point>
    </sml:location>                 
     */
    private void createLocation() {
        SmlLocation2 xbSmlLocation = xbSystem.addNewSmlLocation();
        PointType xbPoint = xbSmlLocation.addNewPoint();
        xbPoint.setSrsName(IoosSosConstants.EPSG_4326_DEF);
        xbPoint.addNewPos().setStringValue(station.getLocation().getY()
                + " " + station.getLocation().getX());
    }

    /**
     * Produces the XML below
        <sml:identification>
           <sml:IdentifierList>
                <sml:identifier name="stationID">
                   <sml:Term definition="http://mmisw.org/ont/ioos/definition/stationID">
                     <sml:value>urn:ogc:object:feature:Sensor:global_hawk_24</sml:value>
                   </sml:Term>
                </sml:identifier>
                <sml:identifier name="shortName">
                   <sml:Term definition="urn:ogc:def:identifier:OGC:shortName">
                     <sml:value>shortName</sml:value>
                   </sml:Term>
                </sml:identifier>
                <sml:identifier name="longName">
                   <sml:Term definition="urn:ogc:def:identifier:OGC:longName">
                     <sml:value>longName</sml:value>
                   </sml:Term>
                </sml:identifier>
              <sml:identifier name="wmoID">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/wmoID">
                  <sml:value>41001</sml:value>
                </sml:Term>
              </sml:identifier>
           </sml:IdentifierList>
        </sml:identification>
     */
    private void createIdentification() {
        IdentifierList xbIdentifierList = xbSystem.addNewIdentification().addNewIdentifierList();

        createIdentifier(xbIdentifierList, IoosDefConstants.STATION_ID, 
                IoosDefConstants.STATION_ID_DEF, station.getId());

        createIdentifier(xbIdentifierList, IoosDefConstants.SHORT_NAME, 
                IoosDefConstants.SHORT_NAME_DEF, station.getShortName()); 

        createIdentifier(xbIdentifierList, IoosDefConstants.LONG_NAME, 
                IoosDefConstants.LONG_NAME_DEF, station.getLongName());

        if(station.getWmoId() != null && station.getWmoId().length() > 0){
            createIdentifier(xbIdentifierList, IoosDefConstants.WMO_ID,
                    IoosDefConstants.WMO_ID_DEF, station.getWmoId());
        }
    }
}