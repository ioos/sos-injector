package com.axiomalaska.sos.xmlbuilder2;

import net.opengis.sos.x20.InsertResultDocument;
import net.opengis.sos.x20.InsertResultType;

import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.tools.IdCreator;

public class InsertResultBuilder {
    // ---------------------------------------------------------------------------
    // Private Members
    // ---------------------------------------------------------------------------
    ObservationCollection obsCollection;
    
    // ---------------------------------------------------------------------------
    // Public Members
    // ---------------------------------------------------------------------------
	
    public InsertResultBuilder(ObservationCollection obsCollection) {
        this.obsCollection = obsCollection;
    }

    /**
	 * Build the XML String
	 * 
    <?xml version="1.0" encoding="UTF-8"?>
    <sos:InsertResult service="SOS" version="2.0.0"
        xmlns:sos="http://www.opengis.net/sos/2.0" 
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd">
        <sos:template>urn:ioos:sensor:wmo:41001:watertemp1:sea_water_temperature_template</sos:template>
        <sos:resultValues>15.12@2012-11-19T13:30:00+02:00#15.15@2012-11-19T13:31:00+02:00#15.15@2012-11-19T13:32:00+02:00#15.85@2012-11-19T13:33:00+02:00#16.5@2012-11-19T13:34:00+02:00#16.9@2012-11-19T13:35:00+02:00#16.7@2012-11-19T13:36:00+02:00#16.5@2012-11-19T13:37:00+02:00#16.6@2012-11-19T13:38:00+02:00#16.5@2012-11-19T13:39:00+02:00#16.4@2012-11-19T13:40:00+02:00#16.34@2012-11-19T13:41:00+02:00#16.25@2012-11-19T13:42:00+02:00#15.79@2012-11-19T13:43:00+02:00#15.56@2012-11-19T13:44:00+02:00#15.25@</sos:resultValues>
    </sos:InsertResult>

     * @throws Exception 
	 */
	public InsertResultDocument build(){
	    InsertResultDocument xbInsertResultDoc = InsertResultDocument.Factory.newInstance();
	    InsertResultType xbInsertResult = xbInsertResultDoc.addNewInsertResult();
	    xbInsertResult.setService(SosInjectorConstants.SOS_SERVICE);
	    xbInsertResult.setVersion(SosInjectorConstants.SOS_V20);
	    
	    
	    xbInsertResult.setTemplate(IdCreator.createResultTemplateId(obsCollection.getSensor(),
	            obsCollection.getPhenomenon(), obsCollection.getHeight()));
	    xbInsertResult.addNewResultValues().newCursor().setTextValue(encodeResultValues(obsCollection));
	    return xbInsertResultDoc;
	}

	private String encodeResultValues(ObservationCollection obsCollection) {
	    StringBuilder str = new StringBuilder();
	    for (int i = 0; i < obsCollection.getObservationDates().size(); i++) {
	        if (i > 0) {
	            str.append(SosInjectorConstants.RESULT_TEMPLATE_BLOCK_SEPARATOR);
	        }
	        str.append(obsCollection.getObservationDates().get(i));
	        str.append(SosInjectorConstants.RESULT_TEMPLATE_TOKEN_SEPARATOR);
            str.append(obsCollection.getObservationValues().get(i));
	    }
	    return str.toString();
	}
}
