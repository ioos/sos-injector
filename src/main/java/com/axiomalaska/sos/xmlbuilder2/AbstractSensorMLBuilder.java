package com.axiomalaska.sos.xmlbuilder2;

import java.util.List;

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
	    Classifier xbClassifier = xbClassifierList.addNewClassifier();
	    xbClassifier.setName(name);
	    Term xbTerm = xbClassifier.addNewTerm();
	    xbTerm.setDefinition(definition);
	    CodeSpacePropertyType xbCodeSpace = xbTerm.addNewCodeSpace();
	    xbCodeSpace.setHref(codeSpaceHref);
	    xbTerm.setValue(value);
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
            <swe:SimpleDataRecord definition="urn:ogc:def:property:capabilities">
                <gml:metaDataProperty xlink:title="urn:ogc:object:feature:Station:IFGI:ifgi-station-1" />
                <gml:metaDataProperty xlink:title="urn:ogc:object:feature:Network:IFGI:ifgi-network-1" />
            </swe:SimpleDataRecord>
        </sml:capabilities>
     */
    protected void createParentProcedures(List<? extends AbstractSosAsset> parents, String capabilitiesName,
            String fieldName, String definition ){
        Capabilities xbCapabilities = xbSystem.addNewCapabilities();
        xbCapabilities.setName(capabilitiesName);
        SimpleDataRecordType xbSimpleDataRecord = (SimpleDataRecordType) xbCapabilities
                .addNewAbstractDataRecord().substitute(
                        SosInjectorConstants.QN_SIMPLEDATARECORD, SimpleDataRecordType.type);
        
        int counter = 0;
        for(AbstractSosAsset parent : parents){
            AnyScalarPropertyType xbField = xbSimpleDataRecord.addNewField();
            xbField.setName(fieldName + ++counter);
            Text xbText = xbField.addNewText();
            xbText.setDefinition(definition);
            xbText.setValue(parent.getId());
        }
    }	
}
