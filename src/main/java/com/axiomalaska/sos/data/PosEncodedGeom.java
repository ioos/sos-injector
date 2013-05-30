package com.axiomalaska.sos.data;

public class PosEncodedGeom {
    private int dimension;
    private String posList;
    
    public PosEncodedGeom(int dimension, String posList) {
        this.dimension = dimension;
        this.posList = posList;
    }

    public int getDimension() {
        return dimension;
    }

    public String getPosList() {
        return posList;
    }
}