package com.axiomalaska.sos.xmlbuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.opengis.sensorML.x101.IdentificationDocument.Identification.IdentifierList;
import net.opengis.sensorML.x101.InputsDocument.Inputs.InputList;
import net.opengis.sensorML.x101.IoComponentPropertyType;
import net.opengis.sensorML.x101.OutputsDocument.Outputs.OutputList;
import net.opengis.sensorML.x101.SensorMLDocument;
import net.opengis.swe.x101.QuantityDocument.Quantity;

import com.axiomalaska.ioos.sos.IoosDefConstants;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.tools.IdCreator;

public class SensorSensorMLBuilder extends AbstractSensorMLBuilder  {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private SosSensor sensor;
	
  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------

	public SensorSensorMLBuilder(SosSensor sensor){
		this.sensor = sensor;
	}
	
  // ---------------------------------------------------------------------------
  // Public Members
  // ---------------------------------------------------------------------------
	
	/**
	 * Build the XML String
	 */
	public SensorMLDocument build() {
	    createDescription(sensor.getStation().getLongName() + ", " + sensor.getLongName());
	    createName(sensor.getId());
	    createIdentification();
	    createParentProcedures(Collections.singletonList(sensor.getStation()));	    
	    List<Phenomenon> phenomena = removeDuplicatePhenomena(sensor.getPhenomena());      
	    createInputs(phenomena);
	    createOutputs(phenomena);
	    //dont create offerings, sensor will use offerings from procedure parents (station and networks)
	    return xbSensorMLDocument;
	}
	
  // ---------------------------------------------------------------------------
  // Private Members
  // ---------------------------------------------------------------------------

	/**
	 * A station can have more than one phenomenon with the same id, but the height
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
	private void createOutputs(List<Phenomenon> phenomena) {
	    OutputList xbOutputList = xbSystem.addNewOutputs().addNewOutputList();
		for(Phenomenon phenomenon : phenomena){
		    IoComponentPropertyType xbOutput = xbOutputList.addNewOutput();
		    xbOutput.setName(phenomenon.getName());
		    Quantity xbQuantity = xbOutput.addNewQuantity();
		    xbQuantity.setDefinition(phenomenon.getId());		    
            xbQuantity.addNewUom().setHref(IdCreator.createUnitHref(phenomenon.getUnit()));
		}
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
	private void createInputs(List<Phenomenon> phenomena) {
	    InputList xbInputList = xbSystem.addNewInputs().addNewInputList();
		for(Phenomenon phenomenon : phenomena){
		    IoComponentPropertyType xbInput = xbInputList.addNewInput();
		    xbInput.setName(phenomenon.getName());
		    xbInput.addNewObservableProperty().setDefinition(phenomenon.getId());
		}
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
	private void createIdentification() {
	    IdentifierList xbIdentifierList = xbSystem.addNewIdentification().addNewIdentifierList();
	    createIdentifier(xbIdentifierList, IoosDefConstants.SENSOR_ID,
	            IoosDefConstants.SENSOR_ID_DEF, sensor.getId());	    
	}
}
