package com.axiomalaska.sos.xmlbuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.tools.IdCreator;

/**
 * Builds a SOS GetObservation XML String for the newest observation from a station
 * and phenomenon
 * 
 * @author Lance Finfrock
 */
public class GetNewestObservationBuilder extends SosXmlBuilder {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private SosStation station;
	private SosSensor sensor;
	private IdCreator idCreator;
	private Phenomenon phenomenon;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public GetNewestObservationBuilder(SosStation station, SosSensor sensor, 
			Phenomenon phenomenon, IdCreator idCreator) {
		this.station = station;
		this.sensor = sensor;
		this.phenomenon = phenomenon;
		this.idCreator = idCreator;
	}

	// -------------------------------------------------------------------------
	// Public Member
	// -------------------------------------------------------------------------

	/**
	 * Build the GetObservation XML String to pull the newest observation
	 * 
	<GetObservation xmlns="http://www.opengis.net/sos/1.0"
	  xmlns:ows="http://www.opengis.net/ows/1.1"
	  xmlns:gml="http://www.opengis.net/gml"
	  xmlns:ogc="http://www.opengis.net/ogc"
	  xmlns:om="http://www.opengis.net/om/1.0"
	  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	  xsi:schemaLocation="http://www.opengis.net/sos/1.0
	  http://schemas.opengis.net/sos/1.0.0/sosGetObservation.xsd"
	  service="SOS" version="1.0.0" srsName="urn:ogc:def:crs:EPSG::4326">

	  <offering>network-all</offering>

	  <eventTime>
	    <ogc:TM_Equals>
	      <ogc:PropertyName>om:samplingTime</ogc:PropertyName>
	      <gml:TimeInstant>
	        <gml:timePosition>latest</gml:timePosition>
	      </gml:TimeInstant>
	    </ogc:TM_Equals>
	  </eventTime>
	  <procedure>urn:ogc:object:feature:Sensor:13774</procedure>
	  <observedProperty>urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature</observedProperty>
	  <featureOfInterest>
		  <ObjectID>foi_13774</ObjectID>
	  </featureOfInterest>
	  <responseFormat>text/xml;subtype="om/1.0.0"</responseFormat>   
	</GetObservation>
	 *
	 */
	public String build() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element getObservation = createGetObservation(doc);
			doc.appendChild(getObservation);

			getObservation.appendChild(createOffering(doc));
			
			getObservation.appendChild(createEventTime(doc));
			
			getObservation.appendChild(createProcedure(doc));
			
			getObservation.appendChild(createObservedProperty(doc));
			
			if (!station.isMoving()) {
				getObservation.appendChild(createFeatureOfInterest(doc));
			}
			
			getObservation.appendChild(createResponseFormat(doc));

			return getString(doc);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return null;
	}
	
	// -------------------------------------------------------------------------
	// Private Member
	// -------------------------------------------------------------------------
	
	/**
 	  <featureOfInterest>
		  <ObjectID>foi_13774</ObjectID>
	  </featureOfInterest>
	 */
	private Node createFeatureOfInterest(Document doc) {
		Element featureOfInterest = doc.createElement("featureOfInterest");
		
	    String featureOfInterestId = idCreator.createFeatureOfInterestId(
				station, sensor);
		
		Element offering = doc.createElement("ObjectID");
		offering.appendChild(doc.createTextNode(featureOfInterestId));
		featureOfInterest.appendChild(offering);
		
		return featureOfInterest;
	}

	/**
	 * 
		<GetObservation xmlns="http://www.opengis.net/sos/1.0"
		  xmlns:ows="http://www.opengis.net/ows/1.1"
		  xmlns:gml="http://www.opengis.net/gml"
		  xmlns:ogc="http://www.opengis.net/ogc"
		  xmlns:om="http://www.opengis.net/om/1.0"
		  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		  xsi:schemaLocation="http://www.opengis.net/sos/1.0
		  http://schemas.opengis.net/sos/1.0.0/sosGetObservation.xsd"
		  service="SOS" version="1.0.0" srsName="urn:ogc:def:crs:EPSG::4326">
		</GetObservation>
	 */
	private Element createGetObservation(Document doc){
		Element getObservation = doc.createElement("GetObservation");
		getObservation.setAttribute("xmlns", "http://www.opengis.net/sos/1.0");
		getObservation.setAttribute("xmlns:ows", "http://www.opengis.net/ows/1.1");
		getObservation.setAttribute("xmlns:gml", "http://www.opengis.net/gml");
		getObservation.setAttribute("xmlns:ogc", "http://www.opengis.net/ogc");
		getObservation.setAttribute("xmlns:om","http://www.opengis.net/om/1.0");
		getObservation.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		getObservation.setAttribute("xsi:schemaLocation", 
				"http://www.opengis.net/sos/1.0 http://schemas.opengis.net/sos/1.0.0/sosGetObservation.xsd");
		getObservation.setAttribute("service", "SOS");
		getObservation.setAttribute("version", "1.0.0");
		getObservation.setAttribute("srsName", "http://www.opengis.net/def/crs/EPSG/0/4326");
		
		return getObservation;
	}

	/**
	 * <offering>network-all</offering>
	 */
	private Node createOffering(Document doc) {
		Element offering = doc.createElement("offering");
		offering.appendChild(doc.createTextNode("network-all"));
		
		return offering;
	}

	/**
	 * <responseFormat>text/xml;subtype="om/1.0.0"</responseFormat> 
	 */
	private Node createResponseFormat(Document doc) {
		Element responseFormat = doc.createElement("responseFormat");
		responseFormat.appendChild(doc.createTextNode("text/xml;subtype=\"om/1.0.0\""));
		
		return responseFormat;
	}

	/**
	 * <observedProperty>urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature</observedProperty>
	 */
	private Node createObservedProperty(Document doc) {
		Element observedProperty = doc.createElement("observedProperty");

		observedProperty.appendChild(doc.createTextNode(phenomenon.getId()));
		
		return observedProperty;
	}

	/**
	 * <procedure>urn:ogc:object:feature:Sensor:13774</procedure>
	 */
	private Node createProcedure(Document doc) {
		Element procedure = doc.createElement("procedure");
		
		String procedureId = idCreator.createSensorId(station, sensor);
		procedure.appendChild(doc.createTextNode(procedureId));

		return procedure;
	}

	/**
	  <eventTime>
	    <ogc:TM_Equals>
	      <ogc:PropertyName>om:samplingTime</ogc:PropertyName>
	      <gml:TimeInstant>
	        <gml:timePosition>latest</gml:timePosition>
	      </gml:TimeInstant>
	    </ogc:TM_Equals>
	  </eventTime>
	 */
	private Node createEventTime(Document doc) {
		Element eventTime = doc.createElement("eventTime");
		
		Element ogcTMEquals = doc.createElement("ogc:TM_Equals");
		eventTime.appendChild(ogcTMEquals);
		
		Element ogcPropertyName = doc.createElement("ogc:PropertyName");
		ogcPropertyName.appendChild(doc.createTextNode("om:samplingTime"));
		ogcTMEquals.appendChild(ogcPropertyName);
		
		Element gmlTimeInstant = doc.createElement("gml:TimeInstant");
		ogcTMEquals.appendChild(gmlTimeInstant);
		
		Element gmlTimePosition = doc.createElement("gml:timePosition");
		gmlTimePosition.appendChild(doc.createTextNode("latest"));
		gmlTimeInstant.appendChild(gmlTimePosition);
		
		return eventTime;
	}
}
