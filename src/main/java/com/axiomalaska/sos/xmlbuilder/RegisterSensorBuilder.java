package com.axiomalaska.sos.xmlbuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.axiomalaska.sos.data.Phenomenon;
import com.axiomalaska.sos.data.Station;
import com.axiomalaska.sos.tools.IdCreator;

/**
 * Builds a SOS RegisterSensor XML String used to add a station to the SOS server
 * 
 * @author Lance Finfrock
 */
public class RegisterSensorBuilder extends SosXmlBuilder  {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private Station station;
	private IdCreator idCreator;
	
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

	public RegisterSensorBuilder(Station station, IdCreator idCreator){
		this.station = station;
		this.idCreator = idCreator;
	}
	
  // ---------------------------------------------------------------------------
  // Public Members
  // ---------------------------------------------------------------------------
	
	/**
	 * Build the XML String
	 */
	public String build() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
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
			system.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			member.appendChild(system);
			
			system.appendChild(createIdentificationNode(doc, station));
			
			system.appendChild(createPositionNode(doc, station));
			
			List<Phenomenon> phenomena = station.getPhenomena();
			
			List<Phenomenon> filteredPhenomena = removeDuplicatePhenomena(phenomena);
			
			system.appendChild(createInputsNode(doc, filteredPhenomena));
			
			system.appendChild(createOutputsNode(doc, filteredPhenomena));
			
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
	 * A station can have more than one phenomenon with the same id, but the depth
	 * of the phenomenon must be different. 
	 * 
	 * @param phenomena - the list of phenomena to remove duplicates from
	 * @return a list of phenomena with the duplicate phenomena id removed. 
	 */
	private List<Phenomenon> removeDuplicatePhenomena(List<Phenomenon> phenomena) {
		List<Phenomenon> filteredPhenomena = new ArrayList<Phenomenon>();
		Set<String> set = new HashSet<String>();
		for(Phenomenon phenomenon : phenomena){
			if(!set.contains(phenomenon.getId())){
				filteredPhenomena.add(phenomenon);
				set.add(phenomenon.getId());
			}
		}
		
		return filteredPhenomena;
	}

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
	<sml:outputs>
		<sml:OutputList>
			<sml:output name="Air Temperature">
				<swe:Quantity definition="urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature">
					<gml:metaDataProperty>
						<offering>
							<id>network-All</id>
							<name>Includes all the sensors in the network</name>
						</offering>
					</gml:metaDataProperty>
					<swe:uom code="C"/>
				</swe:Quantity>
			</sml:output>
		</sml:OutputList>
	</sml:outputs>
	 */
	private Node createOutputsNode(Document doc, List<Phenomenon> phenomenons) {
		Element outputs = doc.createElement("sml:outputs");
		
		Element outputList = doc.createElement("sml:OutputList");
		outputs.appendChild(outputList);
		
		for(Phenomenon phenomenon : phenomenons){
			Element output = doc.createElement("sml:output");
			output.setAttribute("name", phenomenon.getName());
			outputList.appendChild(output);
			
			Element quantity = doc.createElement("swe:Quantity");
			
			quantity.setAttribute("definition", phenomenon.getId());
			
			output.appendChild(quantity);
			
			Element metaDataProperty = doc.createElement("gml:metaDataProperty");
			quantity.appendChild(metaDataProperty);
			
			Element offering = doc.createElement("offering");
			metaDataProperty.appendChild(offering);
			
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode("network-All"));
			offering.appendChild(id);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode("Includes all the sensors in the network"));
			offering.appendChild(name);
			
			Element uom = doc.createElement("swe:uom");
			if(phenomenon.getUnits().length() > 30){
				String truncatedUnits = phenomenon.getUnits().substring(0, 30);
				uom.setAttribute("code", truncatedUnits);
			}
			else{
				uom.setAttribute("code", phenomenon.getUnits());
			}
			quantity.appendChild(uom);
		}
		
		return outputs;
	}

	/**
	<sml:inputs>
		<sml:InputList>
			<sml:input name="Air Temperature">
			<	swe:ObservableProperty definition="urn:x-ogc:def:phenomenon:IOOS:0.0.1:air_temperature"/>
			</sml:input>
		</sml:InputList>
	</sml:inputs>
	 */
	private Node createInputsNode(Document doc, List<Phenomenon> phenomenons) {
		Element inputs = doc.createElement("sml:inputs");
		
		Element inputList = doc.createElement("sml:InputList");
		inputs.appendChild(inputList);
		
		for(Phenomenon phenomenon : phenomenons){
			Element input = doc.createElement("sml:input");
			input.setAttribute("name", phenomenon.getName());
			inputList.appendChild(input);
			
			Element observableProperty = doc.createElement("swe:ObservableProperty");
			observableProperty.setAttribute("definition", phenomenon.getId());
			
			input.appendChild(observableProperty);
		}
		
		return inputs;
	}

	/**
	<sml:position name="sensorPosition">
		<swe:Position referenceFrame="urn:ogc:def:crs:EPSG::4326">
			<swe:location>
				<swe:Vector gml:id="STATION_LOCATION">
					<swe:coordinate name="easting">
						<swe:Quantity>
							<swe:uom code="degree"/>
							<swe:value>-143.0</swe:value>
						</swe:Quantity>
					</swe:coordinate>
					<swe:coordinate name="northing">
						<swe:Quantity>
							<swe:uom code="degree"/>
							<swe:value>63.0</swe:value>
						</swe:Quantity>
					</swe:coordinate>
					<swe:coordinate name="altitude">
						<swe:Quantity>
							<swe:uom code="m"/>
							<swe:value>0.0</swe:value>
						</swe:Quantity>
					</swe:coordinate>
				</swe:Vector>
			</swe:location>
		</swe:Position>
	</sml:position>
	 */
	private Node createPositionNode(Document doc, Station station) {
		Element position = doc.createElement("sml:position");
		position.setAttribute("name", "sensorPosition");
		
		Element swePosition = doc.createElement("swe:Position");
		swePosition.setAttribute("referenceFrame", "urn:ogc:def:crs:EPSG::4326");
		position.appendChild(swePosition);
		
		Element location = doc.createElement("swe:location");
		swePosition.appendChild(location);
		
		Element vector = doc.createElement("swe:Vector");
		vector.setAttribute("gml:id", "STATION_LOCATION");
		location.appendChild(vector);
		
		Element eastingCoordinate = doc.createElement("swe:coordinate");
		eastingCoordinate.setAttribute("name", "easting");
		vector.appendChild(eastingCoordinate);
		
		Element eastingQuantity = doc.createElement("swe:Quantity");
		eastingCoordinate.appendChild(eastingQuantity);
		
		Element eastingUom = doc.createElement("swe:uom");
		eastingUom.setAttribute("code", "degree");
		eastingQuantity.appendChild(eastingUom);
		
		Element eastingValue = doc.createElement("swe:value");
		eastingValue.appendChild(doc.createTextNode(station.getLocation().getLongitude() + ""));
		eastingQuantity.appendChild(eastingValue);
		
		Element northingCoordinate = doc.createElement("swe:coordinate");
		northingCoordinate.setAttribute("name", "northing");
		vector.appendChild(northingCoordinate);
		
		Element northingQuantity = doc.createElement("swe:Quantity");
		northingCoordinate.appendChild(northingQuantity);
		
		Element northingUom = doc.createElement("swe:uom");
		northingUom.setAttribute("code", "degree");
		northingQuantity.appendChild(northingUom);
		
		Element northingValue = doc.createElement("swe:value");
		northingValue.appendChild(doc.createTextNode(station.getLocation().getLatitude() + ""));
		northingQuantity.appendChild(northingValue);
		
		Element altitudeCoordinate = doc.createElement("swe:coordinate");
		altitudeCoordinate.setAttribute("name", "altitude");
		vector.appendChild(altitudeCoordinate);
		
		Element altitudeQuantity = doc.createElement("swe:Quantity");
		altitudeCoordinate.appendChild(altitudeQuantity);
		
		Element altitudeUom = doc.createElement("swe:uom");
		altitudeUom.setAttribute("code", "m");
		altitudeQuantity.appendChild(altitudeUom);
		
		Element altitudeValue = doc.createElement("swe:value");
		altitudeValue.appendChild(doc.createTextNode("0.0"));
		altitudeQuantity.appendChild(altitudeValue);
		
		return position;
	}

	/**
	 * Produces the XML below
          <sml:identification>
               <sml:IdentifierList>
                    <sml:identifier>
                         <sml:Term definition="urn:ogc:def:identifier:OGC:uniqueID">
                              <sml:value>urn:ogc:object:feature:Sensor:global_hawk_24</sml:value>
                         </sml:Term>
                    </sml:identifier>
               </sml:IdentifierList>
          </sml:identification>
	 */
	private Node createIdentificationNode(Document doc, Station station) {
		Element identification = doc.createElement("sml:identification");
	
		Element identifierList = doc.createElement("sml:IdentifierList");
		identification.appendChild(identifierList);
		
		Element identifier = doc.createElement("sml:identifier");
		identifierList.appendChild(identifier);
		
		Element term = doc.createElement("sml:Term");
		term.setAttribute("definition", "urn:ogc:def:identifier:OGC:uniqueID");
		identifier.appendChild(term);
		
		Element value = doc.createElement("sml:value");
		String procedureId = idCreator.createProcederId(station);
		value.appendChild(doc.createTextNode(procedureId));
		term.appendChild(value);
		
		return identification;
	}
}
