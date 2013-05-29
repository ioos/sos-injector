package com.axiomalaska.sos.xmlbuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.opengis.sensorML.x101.CapabilitiesDocument.Capabilities;
import net.opengis.sensorML.x101.ClassificationDocument.Classification.ClassifierList;
import net.opengis.sensorML.x101.ClassificationDocument.Classification.ClassifierList.Classifier;
import net.opengis.sensorML.x101.IdentificationDocument.Identification.IdentifierList;
import net.opengis.sensorML.x101.IdentificationDocument.Identification.IdentifierList.Identifier;
import net.opengis.sensorML.x101.SensorMLDocument;
import net.opengis.sensorML.x101.SensorMLDocument.SensorML;
import net.opengis.sensorML.x101.SystemType;
import net.opengis.sensorML.x101.TermDocument.Term;
import net.opengis.swe.x101.AnyScalarPropertyType;
import net.opengis.swe.x101.CodeSpacePropertyType;
import net.opengis.swe.x101.SimpleDataRecordType;
import net.opengis.swe.x101.TextDocument.Text;

import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.AbstractSosAsset;

public abstract class AbstractSensorMLBuilder {
    protected SensorMLDocument xbSensorMLDocument = SensorMLDocument.Factory.newInstance();
    protected SystemType xbSystem = buildSystem();
    
    private SystemType buildSystem(){
        SensorML xbSensorML = xbSensorMLDocument.addNewSensorML();
        xbSensorML.setVersion(SosInjectorConstants.SML_V101);
        SystemType xbSystem = (SystemType) xbSensorML.addNewMember().addNewProcess()
                .substitute(SosInjectorConstants.QN_SYSTEM, SystemType.type);        
        return xbSystem;
    }
	
    public abstract SensorMLDocument build();
	
	protected void createIdentifier(IdentifierList xbIdentifierList, String name, 
			String definition, String valueString){
	    Identifier xbIdentifier = xbIdentifierList.addNewIdentifier();
	    xbIdentifier.setName(name);
	    Term xbTerm = xbIdentifier.addNewTerm();
	    xbTerm.setDefinition(definition);
	    xbTerm.setValue(valueString);
	}
	
	protected void createClassifier(ClassifierList xbClassifierList, String name,
			String definition, String codeSpaceHref, String value) {
	    if (value != null) {
    	    Classifier xbClassifier = xbClassifierList.addNewClassifier();
    	    xbClassifier.setName(name);
    	    Term xbTerm = xbClassifier.addNewTerm();
    	    xbTerm.setDefinition(definition);
    	    CodeSpacePropertyType xbCodeSpace = xbTerm.addNewCodeSpace();
    	    xbCodeSpace.setHref(codeSpaceHref);
    	    xbTerm.setValue(value);
	    }
	}

	protected void createDescription(String description) {
	    xbSystem.addNewDescription().setStringValue(description);
	}
	
	protected void createName(String name) {
	    xbSystem.addNewName().setStringValue(name);
	}
	
    /**
     * Produces the XML below
        <sml:capabilities name="parentProcedures">
            <swe:SimpleDataRecord>
                <swe:field name="network-all">
                    <swe:Text>
                        <swe:value>urn:ioos:network:ioos:all</swe:value>
                    </swe:Text>
                </swe:field>
            </swe:SimpleDataRecord>
        </sml:capabilities>
     */
    protected void createParentProcedures(List<? extends AbstractSosAsset> parents, String capabilitiesName,
            String fieldName, String definition ){
        Map<String,String> nameValueMap = new HashMap<String,String>();
        int counter = 0;
        for(AbstractSosAsset parent : parents){
            nameValueMap.put(fieldName + ++counter, parent.getId());
        }

        createSpecialCapabilities(capabilitiesName, definition, nameValueMap);
    }

    /**
     * Produces the XML below
        <sml:capabilities name="offerings">
            <swe:SimpleDataRecord>
                <swe:field name="Offering for WMO 41001 station">
                    <swe:Text>
                        <swe:value>urn:ioos:station:wmo:41001</swe:value>
                    </swe:Text>
                </swe:field>
            </swe:SimpleDataRecord>
        </sml:capabilities>
     */
    protected void createOffering(AbstractSosAsset asset){
        createSpecialCapabilities(IoosSosConstants.OFFERINGS, null,
                Collections.singletonMap("Offering for " + asset.getId(), asset.getId()));
    }
    
    /**
     * Produces the XML below
        <sml:capabilities name="capabilitesName">
            <swe:SimpleDataRecord definition="definition">
                <swe:field name="fieldName">
                    <swe:Text>
                        <swe:value>value</swe:value>
                    </swe:Text>
                </swe:field>
            </swe:SimpleDataRecord>
        </sml:capabilities>
     */    
    protected void createSpecialCapabilities(String capabilitiesName, String definition,
            Map<String,String> nameValueMap ){
        Capabilities xbCapabilities = xbSystem.addNewCapabilities();
        xbCapabilities.setName(capabilitiesName);
        SimpleDataRecordType xbSimpleDataRecord = (SimpleDataRecordType) xbCapabilities
                .addNewAbstractDataRecord().substitute(
                        SosInjectorConstants.QN_SIMPLEDATARECORD, SimpleDataRecordType.type);
        
        for(Entry<String,String> nameValuePair : nameValueMap.entrySet()){
            AnyScalarPropertyType xbField = xbSimpleDataRecord.addNewField();
            xbField.setName(nameValuePair.getKey());
            Text xbText = xbField.addNewText();
            if (definition != null) {
                xbText.setDefinition(definition);
            }
            xbText.setValue(nameValuePair.getValue());
        }
    }    
}
