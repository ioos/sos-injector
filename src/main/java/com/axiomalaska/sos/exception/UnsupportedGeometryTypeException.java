package com.axiomalaska.sos.exception;

import com.vividsolutions.jts.geom.Geometry;

public class UnsupportedGeometryTypeException extends Exception{
    private static final long serialVersionUID = -1677718928145889705L;

    public UnsupportedGeometryTypeException(Geometry geometry){
        super("Unsupported geometry type: " + (geometry == null ? "null" : geometry.getGeometryType()));    
    }
}
