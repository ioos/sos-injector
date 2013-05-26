package com.axiomalaska.sos.xmlbuilder2;

import net.opengis.sos.x20.GetObservationDocument;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.SosSensor;

public class GetOldestObservationBuilder extends GetObservationBuilder {
    public GetOldestObservationBuilder(SosSensor sensor, Phenomenon phenomenon, Double height) {
        super(sensor, phenomenon, height);
    }

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
        <sos:temporalFilter>
            <fes:TEquals>
                <fes:ValueReference>phenomenonTime</fes:ValueReference>
                <gml:TimeInstant gml:id="ti">
                    <gml:timePosition>first</gml:timePosition>
                </gml:TimeInstant>
            </fes:TEquals>
        </sos:temporalFilter>
    </sos:GetObservation>
     *
     */
    public GetObservationDocument build() {
        GetObservationDocument xbGetObservationDoc = super.build();
        addTimeInstantFilter(xbGetObservationDoc, SosInjectorConstants.FIRST);
        return xbGetObservationDoc;
    }
}
