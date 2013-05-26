package com.axiomalaska.sos.xmlbuilder2;

import net.opengis.sos.x20.GetResultTemplateDocument;
import net.opengis.sos.x20.GetResultTemplateType;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.SosSensor;

public class GetResultTemplateBuilder {
    // ---------------------------------------------------------------------------
    // Private Members
    // ---------------------------------------------------------------------------
    SosSensor sensor;
    Phenomenon phenomenon;

    // ---------------------------------------------------------------------------
    // Public Members
    // ---------------------------------------------------------------------------
	
    public GetResultTemplateBuilder(SosSensor sensor, Phenomenon phenomenon) {
        this.sensor = sensor;
        this.phenomenon = phenomenon;
    }

    /**
	 * Build the XML String
	 * 
    <?xml version="1.0" encoding="UTF-8"?>
    <sos:GetResultTemplate xmlns:sos="http://www.opengis.net/sos/2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" service="SOS" version="2.0.0" xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd">
      <sos:offering>urn:ioos:sensor:wmo:41001:watertemp1</sos:offering>
      <sos:observedProperty>http://mmisw.org/ont/cf/parameter/sea_water_temperature</sos:observedProperty>
    </sos:GetResultTemplate> 
     * @throws Exception 
	 */
	public GetResultTemplateDocument build(){
	    GetResultTemplateDocument xbGetResultTemplateDoc = GetResultTemplateDocument.Factory.newInstance();
	    GetResultTemplateType xbGetResultTemplate = xbGetResultTemplateDoc.addNewGetResultTemplate();
	    xbGetResultTemplate.setService(SosInjectorConstants.SOS_SERVICE);
	    xbGetResultTemplate.setVersion(SosInjectorConstants.SOS_V20);
	    xbGetResultTemplate.setOffering(sensor.getId());
	    xbGetResultTemplate.setObservedProperty(phenomenon.getId());
	    return xbGetResultTemplateDoc;
	}
}
