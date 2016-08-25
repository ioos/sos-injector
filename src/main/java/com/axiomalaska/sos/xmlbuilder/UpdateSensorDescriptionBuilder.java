package com.axiomalaska.sos.xmlbuilder;

import net.opengis.swes.x20.UpdateSensorDescriptionDocument;
import net.opengis.swes.x20.UpdateSensorDescriptionType;

import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.exception.UnsupportedSosAssetTypeException;

public class UpdateSensorDescriptionBuilder extends AbstractSwesBuilder {
    // ---------------------------------------------------------------------------
    // Private Members
    // ---------------------------------------------------------------------------
    AbstractSosAsset asset;
    PublisherInfo publisherInfo;
    
    // ---------------------------------------------------------------------------
    // Public Members
    // ---------------------------------------------------------------------------
	
    public UpdateSensorDescriptionBuilder(AbstractSosAsset asset, PublisherInfo publisherInfo) {
        this.asset = asset;
        this.publisherInfo = publisherInfo;
    }

    /**
	 * Build the XML String
	 * 
    <?xml version="1.0" encoding="UTF-8"?>
    <swes:UpdateSensorDescription
        xmlns:swes="http://www.opengis.net/swes/2.0"
        xmlns:sos="http://www.opengis.net/sos/2.0"
        xmlns:swe="http://www.opengis.net/swe/1.0.1"
        xmlns:sml="http://www.opengis.net/sensorML/1.0.1"
        xmlns:gml="http://www.opengis.net/gml"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" service="SOS" version="2.0.0" xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sosInsertSensor.xsd    http://www.opengis.net/swes/2.0 http://schemas.opengis.net/swes/2.0/swes.xsd">
        <swes:procedure>urn:ioos:station:wmo:41001</swes:procedure>
        <swes:procedureDescriptionFormat>text/xml;subtype="sensorML/1.0.1"</swes:procedureDescriptionFormat>
        <swes:description>
            <swes:SensorDescription>
                <swes:data>
                    <sml:SensorML ... />
                </swes:data>
            </swes:SensorDescription>
        </swes:description>
    </swes:UpdateSensorDescription>           
     * @throws UnsupportedSosAssetTypeException 
     * @ 
	 */
	public UpdateSensorDescriptionDocument build() throws UnsupportedSosAssetTypeException  {
	    UpdateSensorDescriptionDocument xbUpdateSensorDescriptionDoc = 
	            UpdateSensorDescriptionDocument.Factory.newInstance();
	    UpdateSensorDescriptionType xbUpdateSensorDescription = 
	            xbUpdateSensorDescriptionDoc.addNewUpdateSensorDescription();
	    xbUpdateSensorDescription.setService(SosInjectorConstants.SOS_SERVICE);
	    xbUpdateSensorDescription.setVersion(SosInjectorConstants.SOS_V200);
	    xbUpdateSensorDescription.setProcedure(asset.getId());
	    xbUpdateSensorDescription.setProcedureDescriptionFormat(SosInjectorConstants.SML_FORMAT);

	    //add sensorml
	    xbUpdateSensorDescription.addNewDescription().addNewSensorDescription()
	        .addNewData().set(buildSensorMLDocument(asset, publisherInfo));
	    return xbUpdateSensorDescriptionDoc;
	}
}
