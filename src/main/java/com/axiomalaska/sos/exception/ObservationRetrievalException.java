package com.axiomalaska.sos.exception;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.ObservationExtremaType;
import com.axiomalaska.sos.data.SosSensor;

public class ObservationRetrievalException extends Exception{
    private static final long serialVersionUID = 53834737593026324L;

    public ObservationRetrievalException(SosSensor sensor, Phenomenon phenomenon, Double height, ObservationExtremaType type){
        super("Error getting " + type.name() + " observation for" 
                + " sensor " + (sensor == null ? "null" : sensor.getId())
                + ", phenomenon " + (phenomenon == null ? "null" : phenomenon.getId())
                + ", height " + (height == null ? "null" : Double.toString(height)));
    }

}
