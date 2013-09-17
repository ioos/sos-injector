package com.axiomalaska.sos.xmlbuilder;

import java.util.HashMap;
import java.util.Map;

import net.opengis.sos.x20.SosInsertionMetadataDocument;
import net.opengis.sos.x20.SosInsertionMetadataType;
import net.opengis.swes.x20.InsertSensorDocument;
import net.opengis.swes.x20.InsertSensorType;

import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.XmlNamespaceConstants;
import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.exception.UnsupportedSosAssetTypeException;
import com.axiomalaska.sos.tools.XmlHelper;

public class InsertSensorBuilder extends AbstractSwesBuilder {
    // ---------------------------------------------------------------------------
    // Private Members
    // ---------------------------------------------------------------------------
    AbstractSosAsset asset;
    PublisherInfo publisherInfo;
    
    // ---------------------------------------------------------------------------
    // Public Members
    // ---------------------------------------------------------------------------
	
    public InsertSensorBuilder(AbstractSosAsset asset, PublisherInfo publisherInfo) {
        this.asset = asset;
        this.publisherInfo = publisherInfo;
    }

    /**
	 * Build the XML String
	 * 
        <?xml version="1.0" encoding="UTF-8"?>
        <swes:InsertSensor
            xmlns:swes="http://www.opengis.net/swes/2.0"
            xmlns:sos="http://www.opengis.net/sos/2.0"
            xmlns:swe="http://www.opengis.net/swe/1.0.1"
            xmlns:sml="http://www.opengis.net/sensorML/1.0.1"
            xmlns:gml="http://www.opengis.net/gml"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" service="SOS" version="2.0.0" xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sosInsertSensor.xsd    http://www.opengis.net/swes/2.0 http://schemas.opengis.net/swes/2.0/swes.xsd">
            <swes:procedureDescriptionFormat>text/xml;subtype="sensorML/1.0.1/profiles/ioos_sos/1.0"</swes:procedureDescriptionFormat>
            <swes:procedureDescription>
               ...
            </swes:procedureDescription>
            <swes:observableProperty>???</swes:observableProperty>
            <swes:metadata>
                <sos:SosInsertionMetadata>
                    <sos:observationType>http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement</sos:observationType>
                    <sos:featureOfInterestType>http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingPoint</sos:featureOfInterestType>
                </sos:SosInsertionMetadata>
            </swes:metadata>
        </swes:InsertSensor>            
     * @throws UnsupportedSosAssetTypeException 
	 */
	public InsertSensorDocument build() throws UnsupportedSosAssetTypeException{
	    InsertSensorDocument xbInsertSensorDoc = InsertSensorDocument.Factory.newInstance();
	    InsertSensorType xbInsertSensor = xbInsertSensorDoc.addNewInsertSensor();
	    xbInsertSensor.setService(SosInjectorConstants.SOS_SERVICE);
	    xbInsertSensor.setVersion(SosInjectorConstants.SOS_V200);
	    xbInsertSensor.setProcedureDescriptionFormat(IoosSosConstants.SML_PROFILE_M10);

	    SosInsertionMetadataDocument xbSosInsertionMetadataDoc = SosInsertionMetadataDocument.Factory.newInstance();
	    SosInsertionMetadataType xbSosInsertionMetadata = xbSosInsertionMetadataDoc.addNewSosInsertionMetadata();
	    xbSosInsertionMetadata.addNewObservationType().setStringValue(SosInjectorConstants.MEASUREMENT_DEF);
	    xbSosInsertionMetadata.addFeatureOfInterestType(SosInjectorConstants.SAMPLING_POINT_DEF);
	    xbInsertSensor.addNewMetadata().set(xbSosInsertionMetadataDoc);
	    
	    //add obs props	   
	    if (asset instanceof SosSensor) {
	        SosSensor sensor = (SosSensor) asset;
	        for (Phenomenon phenomenon : sensor.getPhenomena()) {
	            xbInsertSensor.addNewObservableProperty().setStringValue(phenomenon.getId());
	        }	        
	    } else {
	        //value is required, use dummy
	        xbInsertSensor.addNewObservableProperty().setStringValue(SosInjectorConstants.NONE_OBS_PROP);
	    }
	    
	    //add sensorml
	    xbInsertSensor.addNewProcedureDescription().set(buildSensorMLDocument(asset, publisherInfo));
	    addInsertSensorSchemaLocations(xbInsertSensorDoc);
	    return xbInsertSensorDoc;
	}
	
	private void addInsertSensorSchemaLocations(InsertSensorDocument xbInsertSensorDocument) {
	    Map<String,String> locations = new HashMap<String,String>();
	    locations.put(XmlNamespaceConstants.NS_SOS_20, XmlNamespaceConstants.SCHEMA_LOCATION_SOS);
	    locations.put(XmlNamespaceConstants.NS_SWES_20, XmlNamespaceConstants.SCHEMA_LOCATION_SWES_200);
	    XmlHelper.addSchemaLocations(xbInsertSensorDocument,locations);
	}
}
