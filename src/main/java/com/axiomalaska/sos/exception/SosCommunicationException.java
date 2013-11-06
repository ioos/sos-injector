package com.axiomalaska.sos.exception;

public class SosCommunicationException extends Exception {
    private static final long serialVersionUID = 4272584971706592951L;

    public SosCommunicationException(Throwable t){
        super("Error communicating with SOS: " + t.getMessage(), t);
    }
}
