package com.axiomalaska.sos.xmlbuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.tools.IdCreator;

public class NetworkRegisterSensorBuilder extends SosXmlBuilder  {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private SosNetwork network;
	private IdCreator idCreator;
	
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

	public NetworkRegisterSensorBuilder(SosNetwork network, IdCreator idCreator){
		this.network = network;
		this.idCreator = idCreator;
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
		            </sml:IdentifierList>
		          </sml:identification>
		
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
	 */
	public String build() {
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
            </sml:IdentifierList>
          </sml:identification>
	 */
	private Node createIdentificationNode(Document doc) {
		Element identification = doc.createElement("sml:identification");
	
		Element identifierList = doc.createElement("sml:IdentifierList");
		identification.appendChild(identifierList);
		
		Element identifier = doc.createElement("sml:identifier");
		identifier.setAttribute("name", "networkID");
		identifierList.appendChild(identifier);
		
		Element term = doc.createElement("sml:Term");
		term.setAttribute("definition", "http://mmisw.org/ont/ioos/definition/networkID");
		identifier.appendChild(term);
		
		Element value = doc.createElement("sml:value");
		String procedureId = idCreator.createNetworkId(network);
		value.appendChild(doc.createTextNode(procedureId));
		term.appendChild(value);
		
		return identification;
	}
	
	/**
	 * <gml:description>STATION DESCRIPTION</gml:description>
	 */
	private Node createDescriptionNode(Document doc, SosNetwork network) {
		Element description = doc.createElement("gml:description");
		description.appendChild(doc.createTextNode(network.getDescription()));
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
