package com.axiomalaska.sos.xmlbuilder;

import net.opengis.sensorML.x101.ClassificationDocument.Classification.ClassifierList;
import net.opengis.sensorML.x101.ContactDocument.Contact;
import net.opengis.sensorML.x101.ContactInfoDocument.ContactInfo;
import net.opengis.sensorML.x101.ContactInfoDocument.ContactInfo.Address;
import net.opengis.sensorML.x101.IdentificationDocument.Identification.IdentifierList;
import net.opengis.sensorML.x101.ResponsiblePartyDocument.ResponsibleParty;
import net.opengis.sensorML.x101.SensorMLDocument;

import com.axiomalaska.ioos.sos.IoosDefConstants;
import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;

public class NetworkSensorMLBuilder extends AbstractSensorMLBuilder {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private SosNetwork network;
	private PublisherInfo publisherInfo;
	
	
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

	public NetworkSensorMLBuilder(SosNetwork network, PublisherInfo publisherInfo){
		this.network = network;
		this.publisherInfo = publisherInfo;
	}
	
  // ---------------------------------------------------------------------------
  // Public Members
  // ---------------------------------------------------------------------------
	
	/**
	 * Build the XML String
	 * 
		<?xml version="1.0" encoding="UTF-8"?>
	    <sml:SensorML
          xmlns:sml="http://www.opengis.net/sensorML/1.0.1"		    
          xmlns:gml="http://www.opengis.net/gml"		    
          version="1.0.1">   
		      <sml:member>
		        <sml:System>
		          <gml:description>All Air Temperature Sensors</gml:description>
		          <gml:name>urn:ioos:network:aoos:airtemp</gml:name>
		
		          <sml:identification>
		            <sml:IdentifierList>
		              <sml:identifier name="networkID">
		                <sml:Term definition="http://mmisw.org/ont/ioos/definition/networkID">
		                  <sml:value>urn:ioos:network:aoos:airtemp</sml:value>
		                </sml:Term>
		              </sml:identifier>
			          <sml:identifier name="shortName">
			            <sml:Term definition="http://mmisw.org/ont/ioos/definition/shortName">
			              <sml:value>NANOOS SOS station assets collection</sml:value>
			            </sml:Term>
			          </sml:identifier>
			          <sml:identifier name="longName">
			            <sml:Term definition="http://mmisw.org/ont/ioos/definition/longName">
			              <sml:value>urn:ioos:network:nanoos:all Collection of all station assets available via the NANOOS SOS service</sml:value>
			            </sml:Term>
			          </sml:identifier>
		            </sml:IdentifierList>
		          </sml:identification>
		
				  <!-- CONTACTS
			        MANDATORY: operator, publisher
			      10/25/2012. FOR A NETWORK PROCEDURE, operator DOESN'T MAKE SENSE. 
			      (unless it really does apply to every station asset) -->
			      <sml:contact xlink:role="http://mmisw.org/ont/ioos/definition/publisher">
			        <sml:ResponsibleParty>
			          <sml:organizationName>NANOOS</sml:organizationName>
			          <sml:contactInfo>
			            <sml:address>
			              <sml:country>USA</sml:country>
			              <sml:electronicMailAddress>mayorga@apl.washington.edu</sml:electronicMailAddress>
			            </sml:address>
			            <sml:onlineResource xlink:href="http://nanoos.org"/>
			          </sml:contactInfo>
			        </sml:ResponsibleParty>
			      </sml:contact>
		        </sml:System>
		      </sml:member>
		    </sml:SensorML>
	 */
	public SensorMLDocument build() {
	    createDescription(network.getLongName());
	    createName(network.getId());
	    createIdentification();
	    createClassification();
	    createContactOperator();
	    createOffering(network);
	    return xbSensorMLDocument;
	}
	
  // ---------------------------------------------------------------------------
  // Private Members
  // ---------------------------------------------------------------------------

	/**
	 * Produces the XML below
          <sml:identification>
            <sml:IdentifierList>
              <sml:identifier name="networkID">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/networkID">
                  <sml:value>urn:ioos:network:aoos:airtemp</sml:value>
                </sml:Term>
              </sml:identifier>
              
	          <sml:identifier name="shortName">
	            <sml:Term definition="http://mmisw.org/ont/ioos/definition/shortName">
	              <sml:value>NANOOS SOS station assets collection</sml:value>
	            </sml:Term>
	          </sml:identifier>
	          <sml:identifier name="longName">
	            <sml:Term definition="http://mmisw.org/ont/ioos/definition/longName">
	              <sml:value>urn:ioos:network:nanoos:all Collection of all station assets available via the NANOOS SOS service</sml:value>
	            </sml:Term>
	          </sml:identifier>
	          
            </sml:IdentifierList>
          </sml:identification>
	 */
	private void createIdentification() {
	    IdentifierList xbIdentifierList = xbSystem.addNewIdentification().addNewIdentifierList();
		
		//networkId
	    createIdentifier(xbIdentifierList, IoosDefConstants.NETWORK_ID,
	            IoosDefConstants.NETWORK_ID_DEF, network.getId());

		//shortName
        createIdentifier(xbIdentifierList, IoosDefConstants.SHORT_NAME,
                IoosDefConstants.SHORT_NAME_DEF, network.getShortName());	    
		
		//longName
        createIdentifier(xbIdentifierList, IoosDefConstants.LONG_NAME, 
                IoosDefConstants.LONG_NAME_DEF, network.getLongName());      
	}
		
	/**
	 * Produces the XML below
      <!-- ===============================================================
        NETWORK PROCEDURE CLASSIFIERS
        ================================================================== -->
      <sml:classification>
        <sml:ClassifierList>
          <!-- MANDATORY: platformType, operatorSector, publisher -->
          <!-- 10/25/2012: EXCEPT, platformType AND operatorSector MAKE NO SENSE 
               FOR NETWORK PROCEDURES! SO, THEY CAN'T BE MANDATORY FOR NETWORK REQUESTS 
               (unless they really do apply to every station asset) -->
          <sml:classifier name="publisher">
            <sml:Term definition="http://mmisw.org/ont/ioos/definition/publisher">
              <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/organization"/>
              <sml:value>NANOOS</sml:value>
            </sml:Term>
          </sml:classifier>
          
          <!-- One or more parentNetwork elements are allowed.  
            At least one MUST reference an IOOS codespace and list the RA Acronym 
            COMMENT: THE USE OF THE LABEL "NETWORK" BOTH IN THIS ORGANIZATIONAL SENSE
                     AND IN THE SENSE OF NETWORK OFFERING MAY BE CONFUSING! -->
          <sml:classifier name="parentNetwork">
            <sml:Term definition="http://mmisw.org/ont/ioos/definition/parentNetwork">
              <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/organization"/>
              <sml:value>NANOOS</sml:value>
            </sml:Term>
          </sml:classifier>
        </sml:ClassifierList>
      </sml:classification>
	 */
	private void createClassification() {
	    if (publisherInfo != null) {
    	    ClassifierList xbClassifierList = xbSystem.addNewClassification().addNewClassifierList();
    		
    		createClassifier(xbClassifierList, IoosDefConstants.PUBLISHER, 
    				IoosDefConstants.PUBLISHER_DEF, IoosSosConstants.ORGANIZATION_ONTOLOGY,
    				publisherInfo.getName());
    		
    		createClassifier(xbClassifierList, IoosDefConstants.PARENT_NETWORK, 
    		        IoosDefConstants.PARENT_NETWORK_DEF, IoosSosConstants.ORGANIZATION_ONTOLOGY,
    		        publisherInfo.getName());
	    }
	}
	
	/**
      <!-- CONTACTS
        MANDATORY: operator, publisher
      10/25/2012. FOR A NETWORK PROCEDURE, operator DOESN'T MAKE SENSE. 
      (unless it really does apply to every station asset) -->
      <sml:contact xlink:role="http://mmisw.org/ont/ioos/definition/publisher">
        <sml:ResponsibleParty>
          <sml:organizationName>NANOOS</sml:organizationName>
          <sml:contactInfo>
            <sml:address>
              <sml:country>USA</sml:country>
              <sml:electronicMailAddress>mayorga@apl.washington.edu</sml:electronicMailAddress>
            </sml:address>
            <sml:onlineResource xlink:href="http://nanoos.org"/>
          </sml:contactInfo>
        </sml:ResponsibleParty>
      </sml:contact>
	 */
	private void createContactOperator(){
	    if (publisherInfo != null) {
    	    Contact xbContact = xbSystem.addNewContact();
    	    xbContact.setRole("http://mmisw.org/ont/ioos/definition/publisher");
    	    ResponsibleParty xbResponsibleParty = xbContact.addNewResponsibleParty();
    	    xbResponsibleParty.setOrganizationName(publisherInfo.getName());
    	    ContactInfo xbContactInfo = xbResponsibleParty.addNewContactInfo();
    	    Address xbAddress = xbContactInfo.addNewAddress();
    	    xbAddress.setCountry(publisherInfo.getCountry());
    	    xbAddress.setElectronicMailAddress(publisherInfo.getEmail());
    	    xbContactInfo.addNewOnlineResource().setHref(publisherInfo.getWebAddress());
	    }
    }
}
