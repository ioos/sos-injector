package com.axiomalaska.sos.tools;

import java.util.Calendar;

import org.apache.xmlbeans.XmlObject;
import org.joda.time.DateTime;

import net.opengis.gml.x32.TimeInstantType;
import net.opengis.gml.x32.TimePeriodType;
import net.opengis.om.x20.OMObservationType;
import net.opengis.om.x20.TimeObjectPropertyType;
import net.opengis.ows.x11.ExceptionReportDocument;
import net.opengis.sos.x20.GetObservationResponseDocument;
import net.opengis.sos.x20.GetObservationResponseType;
import net.opengis.sos.x20.GetObservationResponseType.ObservationData;

public class ResponseInterpretter {
    public static boolean isError(XmlObject xmlObject) {
        return xmlObject instanceof ExceptionReportDocument;
    }

    public static Calendar parseDateFromGetObservationResponse(GetObservationResponseDocument xbGetObsResposeDoc) {
        GetObservationResponseType xbGetObsResponse = xbGetObsResposeDoc.getGetObservationResponse();
        if(xbGetObsResponse.getObservationDataArray() != null && xbGetObsResponse.getObservationDataArray().length == 1) {
            ObservationData xbObsDataArray = xbGetObsResponse.getObservationDataArray(0);
            if (xbObsDataArray.getOMObservation() != null) {
                OMObservationType xbOmObservation = xbObsDataArray.getOMObservation();
                if (xbOmObservation.getPhenomenonTime() != null) {
                    TimeObjectPropertyType xbPhenomenonTime = xbOmObservation.getPhenomenonTime();
                    if (xbPhenomenonTime instanceof TimeInstantType) {
                        TimeInstantType xbTimeInstant = (TimeInstantType) xbPhenomenonTime;
                        DateTime date = DateTime.parse(xbTimeInstant.getTimePosition().getStringValue());
                        return date.toGregorianCalendar();
                    } else if (xbPhenomenonTime instanceof TimePeriodType) {
                        TimePeriodType xbTimePeriod = (TimePeriodType) xbPhenomenonTime;
                        //TODO should this be the phenomenon end position instead of start?
                        DateTime date = DateTime.parse(xbTimePeriod.getBeginPosition().getStringValue());
                        return date.toGregorianCalendar();                        
                    }
                }
            }
        }
        //TODO should this throw an exception instead of returning null?
        return null;
    }
}
