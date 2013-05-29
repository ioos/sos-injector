package com.axiomalaska.sos.exception;

import com.axiomalaska.sos.data.ObservationCollection;

public class InvalidObservationCollectionException extends Exception {
    private static final long serialVersionUID = -7547898353314268750L;

    public InvalidObservationCollectionException(ObservationCollection obsCol) {
        super(obsCol.toString() + " is invalid");
    }
}
