package com.axiomalaska.sos.xmlbuilder;

import net.opengis.fes.x20.BinaryTemporalOpType;
import net.opengis.gml.x32.TimeInstantDocument;
import net.opengis.gml.x32.TimeInstantType;
import net.opengis.sos.x20.GetObservationDocument;
import net.opengis.sos.x20.GetObservationType;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.tools.XmlHelper;
import com.vividsolutions.jts.geom.Geometry;

public abstract class GetObservationBuilder {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private SosSensor sensor;
	private Phenomenon phenomenon;
	private Geometry geometry;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public GetObservationBuilder(SosSensor sensor, Phenomenon phenomenon, Geometry geometry) {
		this.sensor = sensor;
		this.phenomenon = phenomenon;
		this.geometry = geometry;
	}
	
	// -------------------------------------------------------------------------
	// Public Member
	// -------------------------------------------------------------------------

	/**
	 * Build the GetObservationDocument
	 * 
    <?xml version="1.0" encoding="UTF-8"?>
    <sos:GetObservation service="SOS" version="2.0.0"
        xmlns:sos="http://www.opengis.net/sos/2.0"
        xmlns:fes="http://www.opengis.net/fes/2.0"
        xmlns:gml="http://www.opengis.net/gml/3.2"
        xmlns:swe="http://www.opengis.net/swe/2.0"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        xmlns:swes="http://www.opengis.net/swes/2.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd">
        <sos:procedure>http://www.52north.org/test/procedure/1</sos:procedure>
        <sos:observedProperty>http://www.52north.org/test/observableProperty/1</sos:observedProperty>
        <sos:featureOfInterest>http://www.52north.org/test/featureOfInterest/1</sos:featureOfInterest>
    </sos:GetObservation>
	 * @throws UnsupportedGeometryTypeException 
	 *
	 */
	public GetObservationDocument build() throws UnsupportedGeometryTypeException {
	    GetObservationDocument xbGetObservationDoc = GetObservationDocument.Factory.newInstance();
	    GetObservationType xbGetObservation = xbGetObservationDoc.addNewGetObservation();
	    xbGetObservation.setService(SosInjectorConstants.SOS_SERVICE);
	    xbGetObservation.setVersion(SosInjectorConstants.SOS_V200);
	    xbGetObservation.addProcedure(sensor.getId());
	    xbGetObservation.addObservedProperty(phenomenon.getId());
	    xbGetObservation.addFeatureOfInterest(IdCreator.createObservationFeatureOfInterestId(sensor, geometry));	    
	    return xbGetObservationDoc;
	}

    protected void addTimeInstantFilter(GetObservationDocument xbGetObservationDoc, String timePosition) {
        BinaryTemporalOpType xbTEquals = (BinaryTemporalOpType) xbGetObservationDoc.getGetObservation().addNewTemporalFilter()
                .addNewTemporalOps().substitute(SosInjectorConstants.QN_TEQUALS, BinaryTemporalOpType.type);
        xbTEquals.setValueReference(SosInjectorConstants.PHENOMENON_TIME);
        TimeInstantDocument xbTimeInstantDoc = TimeInstantDocument.Factory.newInstance();
        TimeInstantType xbTimeInstant = xbTimeInstantDoc.addNewTimeInstant();
        xbTimeInstant.setId("timeInstant");
        xbTimeInstant.addNewTimePosition().setStringValue(timePosition);
        XmlHelper.append(xbTEquals, xbTimeInstantDoc);
    }	
}
