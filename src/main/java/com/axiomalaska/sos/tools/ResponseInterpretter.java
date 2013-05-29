package com.axiomalaska.sos.tools;

import net.opengis.gml.x32.AbstractTimeObjectType;
import net.opengis.gml.x32.TimeInstantType;
import net.opengis.gml.x32.TimePeriodType;
import net.opengis.om.x20.OMObservationType;
import net.opengis.om.x20.TimeObjectPropertyType;
import net.opengis.ows.x11.ExceptionReportDocument;
import net.opengis.sos.x20.GetObservationResponseDocument;
import net.opengis.sos.x20.GetObservationResponseType;
import net.opengis.sos.x20.GetObservationResponseType.ObservationData;

import org.apache.xmlbeans.XmlObject;
import org.joda.time.DateTime;

public class ResponseInterpretter {
    public static boolean isError(XmlObject xmlObject) {
        return xmlObject instanceof ExceptionReportDocument;
    }

    public static String getFirstExceptionText(ExceptionReportDocument xbExceptionReportDoc) {
        try {
            return xbExceptionReportDoc.getExceptionReport().getExceptionArray(0).getExceptionTextArray(0);
        } catch (NullPointerException e) {
            return "[No exception text]";
        }
    }    
    
    public static DateTime parseDateFromGetObservationResponse(GetObservationResponseDocument xbGetObsResposeDoc) {
        GetObservationResponseType xbGetObsResponse = xbGetObsResposeDoc.getGetObservationResponse();
        if(xbGetObsResponse.getObservationDataArray() != null && xbGetObsResponse.getObservationDataArray().length == 1) {
            ObservationData xbObsDataArray = xbGetObsResponse.getObservationDataArray(0);
            if (xbObsDataArray.getOMObservation() != null) {
                OMObservationType xbOmObservation = xbObsDataArray.getOMObservation();
                if (xbOmObservation.getPhenomenonTime() != null) {
                    AbstractTimeObjectType xbPhenomenonTime = xbOmObservation.getPhenomenonTime().getAbstractTimeObject();
                    if (xbPhenomenonTime instanceof TimeInstantType) {
                        TimeInstantType xbTimeInstant = (TimeInstantType) xbPhenomenonTime;
                        return DateTime.parse(xbTimeInstant.getTimePosition().getStringValue());
                    } else if (xbPhenomenonTime instanceof TimePeriodType) {
                        TimePeriodType xbTimePeriod = (TimePeriodType) xbPhenomenonTime;
                        //TODO should this be the phenomenon end position instead of start?
                        return DateTime.parse(xbTimePeriod.getBeginPosition().getStringValue());                        
                    }
                }
            }
        }
        //TODO should this throw an exception instead of returning null?
        return null;
    }
}
