package com.axiomalaska.sos.exception;

public class StationCreationException extends Exception{
    private static final long serialVersionUID = -7315009234500481133L;

    public StationCreationException(Throwable t){
        super("Error creating station", t);
    }
}
