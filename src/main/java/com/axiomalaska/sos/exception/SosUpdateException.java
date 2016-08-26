package com.axiomalaska.sos.exception;

public class SosUpdateException extends Exception {
    private static final long serialVersionUID = 8153844598922304331L;

    public SosUpdateException(Throwable t){
        super("Error updating with SOS: " + t.getMessage(), t);
    }
}
