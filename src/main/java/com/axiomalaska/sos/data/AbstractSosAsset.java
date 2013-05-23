package com.axiomalaska.sos.data;

import org.n52.sos.ioos.asset.AbstractAsset;

public abstract class AbstractSosAsset implements SosAsset{
    private String longName;
    private String shortName;

    @Override
    public String getId(){
        return getAsset().getAssetId();
    }
    
    @Override
    public String getLongName() {
        if (longName == null) {
            return shortName;
        }
        return longName;
    }

    @Override
    public String getShortName() {
        if (shortName == null) {
            return getId();
        }
        return shortName;
    }

    @Override    
    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Override    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public abstract AbstractAsset getAsset();

    @Override    
    public String toString(){
        return getAsset().toString();
    }
}
