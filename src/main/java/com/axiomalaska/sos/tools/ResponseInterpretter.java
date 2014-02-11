package com.axiomalaska.sos.tools;

import java.io.IOException;
import java.util.Map;

import net.opengis.gml.x32.AbstractTimeObjectType;
import net.opengis.gml.x32.TimeInstantType;
import net.opengis.gml.x32.TimePeriodType;
import net.opengis.om.x20.OMObservationType;
import net.opengis.ows.x11.ExceptionReportDocument;
import net.opengis.sos.x20.GetObservationResponseDocument;
import net.opengis.sos.x20.GetObservationResponseType;
import net.opengis.sos.x20.GetObservationResponseType.ObservationData;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.joda.time.DateTime;

import com.axiomalaska.sos.DateExtremaType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseInterpretter {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    
    public static boolean isError(XmlObject xmlObject) {
        return xmlObject instanceof ExceptionReportDocument;
    }

    public static XmlObject getXmlObject(String string) throws XmlException, IOException {
        if (string == null || string.isEmpty()) {
            throw new IOException("Response was blank");
        }

        return XmlObject.Factory.parse(string);
    }
    
    public static boolean onlyExceptionContains(ExceptionReportDocument xbExceptionReportDoc, String text) {
        return xbExceptionReportDoc.getExceptionReport() != null
                && xbExceptionReportDoc.getExceptionReport().getExceptionArray() != null
                && xbExceptionReportDoc.getExceptionReport().getExceptionArray().length == 1
                && xbExceptionReportDoc.getExceptionReport().getExceptionArray(0).getExceptionTextArray() != null
                && xbExceptionReportDoc.getExceptionReport().getExceptionArray(0).getExceptionTextArray().length == 1
                && xbExceptionReportDoc.getExceptionReport().getExceptionArray(0).getExceptionTextArray(0)
                    .contains(text);
    }    
    
    public static DateTime parseMinDateFromGetObservationResponse(GetObservationResponseDocument xbGetObsResposeDoc) {
        return parseDateExtremaFromGetObservationResponse(xbGetObsResposeDoc, DateExtremaType.OLDEST);
    }
    
    public static DateTime parseMaxDateFromGetObservationResponse(GetObservationResponseDocument xbGetObsResposeDoc) {
        return parseDateExtremaFromGetObservationResponse(xbGetObsResposeDoc, DateExtremaType.NEWEST);
    }
    
    public static DateTime parseDateExtremaFromGetObservationResponse(GetObservationResponseDocument xbGetObsResposeDoc,
            DateExtremaType dateExtremaType) {
        GetObservationResponseType xbGetObsResponse = xbGetObsResposeDoc.getGetObservationResponse();
        DateTime mostExtremeDate = null;
        if(xbGetObsResponse.getObservationDataArray() != null) {
            for (ObservationData xbObsData : xbGetObsResponse.getObservationDataArray()) {
                if (xbObsData.getOMObservation() != null) {
                    OMObservationType xbOmObservation = xbObsData.getOMObservation();
                    if (xbOmObservation.getPhenomenonTime() != null) {
                        AbstractTimeObjectType xbPhenomenonTime = xbOmObservation.getPhenomenonTime().getAbstractTimeObject();
                        DateTime thisDate = null;
                        if (xbPhenomenonTime instanceof TimeInstantType) {
                            TimeInstantType xbTimeInstant = (TimeInstantType) xbPhenomenonTime;
                            thisDate = DateTime.parse(xbTimeInstant.getTimePosition().getStringValue());
                        } else if (xbPhenomenonTime instanceof TimePeriodType) {
                            TimePeriodType xbTimePeriod = (TimePeriodType) xbPhenomenonTime;
                            //TODO should this be the phenomenon end position instead of start?
                            thisDate = DateTime.parse(xbTimePeriod.getBeginPosition().getStringValue());                        
                        }
                        if(isMoreExtreme(mostExtremeDate, thisDate, dateExtremaType)) {
                            mostExtremeDate = thisDate;
                        }
                    }
                }
            }
        }
        //TODO should this throw an exception if null?
        return mostExtremeDate;
    }

    /**
     * Is date2 more extreme than date1?
     * @param date1 Date to compare against
     * @param date2 Date to compare
     * @param dateExtremaType The type of temporal extremity (newer or older)
     * @return The more extreme (newer or older) date
     */
    private static boolean isMoreExtreme(DateTime date1, DateTime date2, DateExtremaType dateExtremaType) {
        if (date1 == null) {
            return true;
        } else if (date2 == null) {
            return false;
        }
        //both are not null
        switch (dateExtremaType) {
            case NEWEST:
                return date2.isAfter(date1);
            case OLDEST:
                return date2.isBefore(date1);
            default:
                return false;
        }
    }
    
    public static boolean getExists(String jsonExistsResponse) throws JsonParseException, JsonMappingException, IOException{
        return (Boolean) jsonMapper.readValue(jsonExistsResponse, Map.class).get("exists");
    } 
}
