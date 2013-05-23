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

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.tools.IdCreator;

public class SensorRegisterSensorBuilder extends SosXmlBuilder  {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private SosSensor sensor;
	private Double depth;
	
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

	public SensorRegisterSensorBuilder(SosSensor sensor){
		this.sensor = sensor;
		this.depth = Double.NaN;
	}
        
    public SensorRegisterSensorBuilder(SosSensor sensor, Double depth){
		this.sensor = sensor;
		this.depth = depth;
	}
	
  // ---------------------------------------------------------------------------
  // Public Members
  // ---------------------------------------------------------------------------
	
	/**
	 * Build the XML String
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
			registerSensor.setAttribute("xmlns:sa", "http://www.opengis.net/sampling/1.0");
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
			
			system.appendChild(createDescriptionNode(doc, sensor));
			
			system.appendChild(createNameNode(doc, sensor));
			
			system.appendChild(createIdentificationNode(doc));
			
			system.appendChild(createParentProcedures(doc));
			
			List<Phenomenon> phenomena = sensor.getPhenomena();
			
			List<Phenomenon> filteredPhenomena = removeDuplicatePhenomena(phenomena);
			
			system.appendChild(createInputsNode(doc, filteredPhenomena));
                        
			system.appendChild(createOutputsNode(doc, phenomena, sensor.getStation().getNetworks()));
                        
//			system.appendChild(createOutputsNode(doc, filteredPhenomena));
			
			registerSensor.appendChild(createObservationTemplate(doc, sensor));
                        
			String xmlString = getString(doc);
                        
			return xmlString;
		} catch (Exception ex) {
			System.err.println("Error in register sensor build: - ");
                        ex.printStackTrace();
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
	private Node createObservationTemplate(Document doc, SosSensor sensor1) {
		Element observationTemplate = doc.createElement("ObservationTemplate");
		
		Element measurement = doc.createElement("om:Measurement");
		observationTemplate.appendChild(measurement);
		
		Element samplingTime = doc.createElement("om:samplingTime");
		measurement.appendChild(samplingTime);
		
		Element procedure = doc.createElement("om:procedure");
		measurement.appendChild(procedure);
		
		Element observedProperty = doc.createElement("om:observedProperty");
		measurement.appendChild(observedProperty);
                
                // need to create a featureOfInterest for the sensor
                Element featureOfInterest = doc.createElement("om:featureOfInterest");
		measurement.appendChild(featureOfInterest);
		
		Element samplingPoint = doc.createElement("sa:SamplingPoint");
		samplingPoint.setAttribute("gml:id", IdCreator.createObservationFeatureOfInterestId(sensor1, depth));
		featureOfInterest.appendChild(samplingPoint);
		
		Element description = doc.createElement("gml:description");
		description.appendChild(doc.createTextNode(sensor1.getLongName()));
		samplingPoint.appendChild(description);
		
		Element name = doc.createElement("gml:name");
		name.appendChild(doc.createTextNode(IdCreator.createObservationFeatureOfInterestName(sensor1, depth)));
		samplingPoint.appendChild(name);
		
		Element sampledFeature = doc.createElement("sa:sampledFeature");
		samplingPoint.appendChild(sampledFeature);
		
		Element position = doc.createElement("sa:position");
		samplingPoint.appendChild(position);
		
		Element point = doc.createElement("gml:Point");
		position.appendChild(point);
		
		Element pos = doc.createElement("gml:pos");
		pos.setAttribute("srsName", "http://www.opengis.net/def/crs/EPSG/0/4326");
		pos.appendChild(doc.createTextNode(sensor.getStation().getLocation().getLatitude() + 
				" " + sensor.getStation().getLocation().getLongitude()));
		point.appendChild(pos);
		
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
				<swe:Quantity definition="http://mmisw.org/ont/cf/parameter/air_temperature">
					<swe:uom code="C"/>
				</swe:Quantity>
			</sml:output>
		</sml:OutputList>
	</sml:outputs>
	 */
	private Node createOutputsNode(Document doc, List<Phenomenon> phenomena) {
		Element outputs = doc.createElement("sml:outputs");
		
		Element outputList = doc.createElement("sml:OutputList");
		outputs.appendChild(outputList);
		
		for(Phenomenon phenomenon : phenomena){
			Element output = doc.createElement("sml:output");
			output.setAttribute("name", phenomenon.getName());
			outputList.appendChild(output);
			
			Element quantity = doc.createElement("swe:Quantity");
			
			quantity.setAttribute("definition", phenomenon.getId());
			
			output.appendChild(quantity);
			
			String unitString = "";
			if(phenomenon.getUnit() != null){
                                // toString generates strings w/ whitespace which is against the pattern symbol for UomSymbol, just remove them??
				unitString = phenomenon.getUnit().toString().replaceAll("\\s+", "");
			}
			
			Element uom = doc.createElement("swe:uom");
		    uom.setAttribute("code", unitString);
			quantity.appendChild(uom);
		}
		
		return outputs;
	}

	/**
	<sml:inputs>
		<sml:InputList>
			<sml:input name="Air Temperature">
			<swe:ObservableProperty definition="http://mmisw.org/ont/cf/parameter/air_temperature"/>
			</sml:input>
		</sml:InputList>
	</sml:inputs>
	 */
	private Node createInputsNode(Document doc, List<Phenomenon> phenomena) {
		Element inputs = doc.createElement("sml:inputs");
		
		Element inputList = doc.createElement("sml:InputList");
		inputs.appendChild(inputList);
		
		for(Phenomenon phenomenon : phenomena){
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
	 * Produces the XML below
          <sml:identification>
            <sml:IdentifierList>
              <sml:identifier name="sensorID">
                <sml:Term definition="http://mmisw.org/ont/ioos/definition/sensorID">
                  <sml:value>urn:ioos:sensor:aoos:pilotrock:airtemp</sml:value>
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
		identifier.setAttribute("name", "sensorID");
		identifierList.appendChild(identifier);
		
		Element term = doc.createElement("sml:Term");
		term.setAttribute("definition", "http://mmisw.org/ont/ioos/definition/sensorID");
		identifier.appendChild(term);
		
		Element value = doc.createElement("sml:value");
		value.appendChild(doc.createTextNode(sensor.getId()));
		term.appendChild(value);
		
		return identification;
	}
	
	/**
	 * <gml:description>STATION DESCRIPTION</gml:description>
	 */
	private Node createDescriptionNode(Document doc, SosSensor sensor) {
		Element description = doc.createElement("gml:description");
		description.appendChild(doc.createTextNode(sensor.getStation().getLongName() + ", " + sensor.getLongName()));
		return description;
	}
	
	/**
	 * <gml:name>urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-90</gml:name>
	 */
	private Node createNameNode(Document doc, SosSensor sensor) {
		Element name = doc.createElement("gml:name");
		name.appendChild(doc.createTextNode(sensor.getId()));
		return name;
	}
	
	/**
	 * Produces the XML below
        <sml:capabilities name="parentProcedures">
            <swe:SimpleDataRecord definition="urn:ogc:def:property:capabilities">
                <gml:metaDataProperty xlink:title="urn:ogc:object:feature:Station:IFGI:ifgi-station-1" />
                <gml:metaDataProperty xlink:title="urn:ogc:object:feature:Network:IFGI:ifgi-network-1" />
            </swe:SimpleDataRecord>
        </sml:capabilities>
	 */
	private Node createParentProcedures(Document doc){
		
		Element capabilities = doc.createElement("sml:capabilities");
		capabilities.setAttribute("name", "parentProcedures");
		
		Element simpleDataRecord = doc.createElement("swe:SimpleDataRecord");
		simpleDataRecord.setAttribute("definition", "urn:ogc:def:property:capabilities");
		capabilities.appendChild(simpleDataRecord);
		
		Element metaDataProperty = doc.createElement("gml:metaDataProperty");
		metaDataProperty.setAttribute("xlink:title", sensor.getStation().getId());
		simpleDataRecord.appendChild(metaDataProperty);
		
		for(SosNetwork network : sensor.getNetworks()){
			metaDataProperty = doc.createElement("gml:metaDataProperty");
			metaDataProperty.setAttribute("xlink:title", network.getId());
			simpleDataRecord.appendChild(metaDataProperty);
		}
		
		return capabilities;
	}

        /**
         * Altered outputs node to include the actual networks listed for the station (rather than just network-all)
         * @param doc
         * @param filteredPhenomena
         * @param id
         * @param description
         * @return 
         */
    private Node createOutputsNode(Document doc, List<Phenomenon> filteredPhenomena, List<SosNetwork> networks) {
        Element outputs = doc.createElement("sml:outputs");
		
		Element outputList = doc.createElement("sml:OutputList");
		outputs.appendChild(outputList);
		
                for(Phenomenon phenomenon : filteredPhenomena){
                    Element output = doc.createElement("sml:output");
                    output.setAttribute("name", phenomenon.getName());
                    outputList.appendChild(output);

                    Element quantity = doc.createElement("swe:Quantity");

                    quantity.setAttribute("definition", phenomenon.getId());

                    output.appendChild(quantity);
                    
                    for (SosNetwork network : networks) {
                        Element metaDataProperty = doc.createElement("gml:metaDataProperty");
                        quantity.appendChild(metaDataProperty);

                        Element offering = doc.createElement("offering");
                        metaDataProperty.appendChild(offering);

                        Element idNode = doc.createElement("id");
                        idNode.appendChild(doc.createTextNode(network.getId()));
                        offering.appendChild(idNode);

                        Element name = doc.createElement("name");
                        name.appendChild(doc.createTextNode(network.getShortName()));
                        offering.appendChild(name);
                    }

                    String unitString = "";
                    if(phenomenon.getUnit() != null){
                            // toString generates strings w/ whitespace which is against the pattern symbol for UomSymbol, just remove them??
                            unitString = phenomenon.getUnit().toString().replaceAll("\\s+", "");
                    }

                    Element uom = doc.createElement("swe:uom");
                uom.setAttribute("code", unitString);
                    quantity.appendChild(uom);
                }
		
		return outputs;
    }
}
