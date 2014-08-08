package com.axiomalaska.sos.exception;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.DateExtremaType;
import com.axiomalaska.sos.data.SosSensor;
import com.vividsolutions.jts.geom.Geometry;

public class ObservationRetrievalException extends Exception{
    private static final long serialVersionUID = 53834737593026324L;

    public ObservationRetrievalException(SosSensor sensor, Phenomenon phenomenon, Geometry geometry, DateExtremaType type,
            String message){
        super("Error getting " + type.name() + " observation for" 
                + " sensor " + (sensor == null ? "null" : sensor.getId())
                + ", phenomenon " + (phenomenon == null ? "null" : phenomenon.getId())
                + ", geometry " + (geometry == null ? "null" : geometry.toString())
                + ", message " + (message == null ? "null" : message));
    }
}
