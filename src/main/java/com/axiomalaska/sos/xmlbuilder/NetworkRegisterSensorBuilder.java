package com.axiomalaska.sos.xmlbuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;

public class NetworkRegisterSensorBuilder extends SosXmlBuilder  {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private SosNetwork network;
	private PublisherInfo publisherInfo;
	
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

	public NetworkRegisterSensorBuilder(SosNetwork network, PublisherInfo publisherInfo){
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
		<RegisterSensor service="SOS" version="1.0.0"
		  xmlns="http://www.opengis.net/sos/1.0"
		  xmlns:swe="http://www.opengis.net/swe/1.0.1"
		  xmlns:ows="http://www.opengeospatial.net/ows"
		  xmlns:xlink="http://www.w3.org/1999/xlink"
		  xmlns:gml="http://www.opengis.net/gml"
		  xmlns:ogc="http://www.opengis.net/ogc"
		  xmlns:om="http://www.opengis.net/om/1.0"
		  xmlns:sml="http://www.opengis.net/sensorML/1.0.1"
		  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		  xsi:schemaLocation="http://www.opengis.net/sos/1.0
		  http://schemas.opengis.net/sos/1.0.0/sosRegisterSensor.xsd
		  http://www.opengis.net/om/1.0
		  http://schemas.opengis.net/om/1.0.0/extensions/observationSpecialization_override.xsd">
		  <SensorDescription>
		    <sml:SensorML version="1.0.1">
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
		  </SensorDescription>
		
		  <!-- ObservationTemplate parameter; this has to be an empty measurement at the moment, as the 52N SOS only supports Measurements to be inserted -->
		  <ObservationTemplate>
		    <om:Measurement>
		      <om:samplingTime/>
		      <om:procedure/>
		      <om:observedProperty/>
		      <om:featureOfInterest></om:featureOfInterest>
		      <om:result xsi:type="gml:MeasureType" uom="">0.0</om:result>
		    </om:Measurement>
		  </ObservationTemplate>
		</RegisterSensor>
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 */
	public String build(){
		try {
			DocumentBuilderFactory docFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element registerSensor = doc.createElement("RegisterSensor");
			registerSensor.setAttribute("service", "SOS");
			registerSensor.setAttribute("version", "1.0.0");
			registerSensor.setAttribute("xmlns", "http://www.opengis.net/sos/1.0");
			registerSensor.setAttribute("xmlns:swe", "http://www.opengis.net/swe/1.0.1");
			registerSensor.setAttribute("xmlns:ows", "http://www.opengeospatial.net/ows");
			registerSensor.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
			registerSensor.setAttribute("xmlns:gml", "http://www.opengis.net/gml");
			registerSensor.setAttribute("xmlns:ogc", "http://www.opengis.net/ogc");
			registerSensor.setAttribute("xmlns:om", "http://www.opengis.net/om/1.0");
			registerSensor.setAttribute("xmlns:sml", "http://www.opengis.net/sensorML/1.0.1");
			registerSensor.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			registerSensor.setAttribute("xsi:schemaLocation", "http://www.opengis.net/sos/1.0 http://schemas.opengis.net/sos/1.0.0/sosRegisterSensor.xsd http://www.opengis.net/om/1.0 http://schemas.opengis.net/om/1.0.0/extensions/observationSpecialization_override.xsd");
			doc.appendChild(registerSensor);

			Element sensorDescription = doc.createElement("SensorDescription");
			registerSensor.appendChild(sensorDescription);
			
			Element sensorML = doc.createElement("sml:SensorML");
			sensorML.setAttribute("version", "1.0.1");
			sensorDescription.appendChild(sensorML);
			
			Element member = doc.createElement("sml:member");
			sensorML.appendChild(member);
			
			Element system = doc.createElement("sml:System");
			member.appendChild(system);
			
			system.appendChild(createDescriptionNode(doc, network));
			
			system.appendChild(createNameNode(doc, network));
			
			system.appendChild(createIdentificationNode(doc));
			
			system.appendChild(createClassificationNode(doc));
			
			system.appendChild(createContactOperatorNode(doc));
			
			registerSensor.appendChild(createObservationTemplate(doc));
			
			String xmlString = getString(doc);
                        
			return xmlString;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return null;
	}
	
  // ---------------------------------------------------------------------------
  // Private Members
  // ---------------------------------------------------------------------------

	/**
	 *  <ObservationTemplate>
          <om:Measurement>
            <om:samplingTime/>
            <om:procedure/>
            <om:observedProperty/>
            <om:featureOfInterest></om:featureOfInterest>
            <om:result xsi:type="gml:MeasureType" uom="" >0.0</om:result>
          </om:Measurement>
        </ObservationTemplate>
	 */
	private Node createObservationTemplate(Document doc) {
		Element observationTemplate = doc.createElement("ObservationTemplate");
		
		Element measurement = doc.createElement("om:Measurement");
		observationTemplate.appendChild(measurement);
		
		Element samplingTime = doc.createElement("om:samplingTime");
		measurement.appendChild(samplingTime);
		
		Element procedure = doc.createElement("om:procedure");
		measurement.appendChild(procedure);
		
		Element observedProperty = doc.createElement("om:observedProperty");
		measurement.appendChild(observedProperty);
		
		Element featureOfInterest = doc.createElement("om:featureOfInterest");
		measurement.appendChild(featureOfInterest);
		
		Element result = doc.createElement("om:result");
		result.setAttribute("uom", "");
		result.setAttribute("xsi:type", "gml:MeasureType");
		result.appendChild(doc.createTextNode("0.0"));
		measurement.appendChild(result);
		
		return observationTemplate;
	}

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
	private Node createIdentificationNode(Document doc) {
		Element identification = doc.createElement("sml:identification");
	
		Element identifierList = doc.createElement("sml:IdentifierList");
		identification.appendChild(identifierList);
		
		//networkId
		identifierList.appendChild(createIdentifier(doc, "networkID", 
				"http://mmisw.org/ont/ioos/definition/networkID", network.getId()));
		
		//shortName
		if (network.getShortName() != null) {
    		identifierList.appendChild(createIdentifier(doc, "shortName", 
    				"http://mmisw.org/ont/ioos/definition/shortName", network.getShortName()));
		}

		//shortName
		if (network.getLongName() != null) {
    		identifierList.appendChild(createIdentifier(doc, "longName", 
    				"http://mmisw.org/ont/ioos/definition/longName", network.getLongName()));
		}

		return identification;
	}
	
	private Node createIdentifier(Document doc, String name, 
			String definition, String valueString){
		Element identifier = doc.createElement("sml:identifier");
		identifier.setAttribute("name", name);
		
		Element term = doc.createElement("sml:Term");
		term.setAttribute("definition", definition);
		identifier.appendChild(term);
		
		Element value = doc.createElement("sml:value");
		value.appendChild(doc.createTextNode(valueString));
		term.appendChild(value);
		
		return identifier;
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
	private Node createClassificationNode(Document doc) {
		Element classification = doc.createElement("sml:classification");
	
		Element classifierList = doc.createElement("sml:ClassifierList");
		classification.appendChild(classifierList);
		
		classifierList.appendChild(createClassifierNode(doc, "publisher", 
				"http://mmisw.org/ont/ioos/definition/publisher", "http://mmisw.org/ont/ioos/organization",
				publisherInfo.getName()));
		
		classifierList.appendChild(createClassifierNode(doc, "parentNetwork", 
				"http://mmisw.org/ont/ioos/definition/parentNetwork", 
				"http://mmisw.org/ont/ioos/organization",
				publisherInfo.getName()));
		
		return classification;
	}
	
	/**
	  <sml:classifier name="platformType">
	    <sml:Term definition="http://mmisw.org/ont/ioos/definition/platformType">
	      <sml:codeSpace xlink:href="http://mmisw.org/ont/ioos/platform"/>
	      <sml:value>buoy</sml:value>
	    </sml:Term>
	  </sml:classifier>
	 */
	private Node createClassifierNode(Document doc, String name,
			String definition, String codeSpaceXlinkHref, String value) {
		Element classifier = doc.createElement("sml:classifier");

		classifier.setAttribute("name", name);

		Element term = doc.createElement("sml:Term");
		term.setAttribute("definition", definition);
		classifier.appendChild(term);

		Element codeSpace = doc.createElement("sml:codeSpace");
		codeSpace.setAttribute("xlink:href", codeSpaceXlinkHref);
		term.appendChild(codeSpace);
		
		Element valueElement = doc.createElement("sml:value");
		valueElement.appendChild(doc.createTextNode(value));
		term.appendChild(valueElement);

		return classifier;
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
	private Node createContactOperatorNode(Document doc){
		Element contact = doc.createElement("sml:contact");
		contact.setAttribute("xlink:role", "http://mmisw.org/ont/ioos/definition/publisher");
	
		Element responsibleParty = doc.createElement("sml:ResponsibleParty");
		contact.appendChild(responsibleParty);
		
		Element organizationName = doc.createElement("sml:organizationName");
		responsibleParty.appendChild(organizationName);
		organizationName.appendChild(doc.createTextNode(publisherInfo.getName()));
	
		Element contactInfo = doc.createElement("sml:contactInfo");
		responsibleParty.appendChild(contactInfo);
		
		Element address = doc.createElement("sml:address");
		contactInfo.appendChild(address);
		
		Element country = doc.createElement("sml:country");
		country.appendChild(doc.createTextNode(publisherInfo.getCountry()));
		address.appendChild(country);
		
		Element electronicMailAddress = doc.createElement("sml:electronicMailAddress");
		electronicMailAddress.appendChild(doc.createTextNode(publisherInfo.getEmail()));
		address.appendChild(electronicMailAddress);
		
		Element onlineResource = doc.createElement("sml:onlineResource");
		onlineResource.setAttribute("xlink:href", publisherInfo.getWebAddress());
		contactInfo.appendChild(onlineResource);
	
		return contact;
	}
	
	/**
	 * <gml:description>STATION DESCRIPTION</gml:description>
	 */
	private Node createDescriptionNode(Document doc, SosNetwork network) {
		Element description = doc.createElement("gml:description");
		description.appendChild(doc.createTextNode(network.getLongName()));
		return description;
	}
	
	/**
	 * <gml:name>urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-90</gml:name>
	 */
	private Node createNameNode(Document doc, SosNetwork network) {
		Element name = doc.createElement("gml:name");
		name.appendChild(doc.createTextNode(network.getId()));
		return name;
	}
}
