package com.axiomalaska.sos.data;

import org.n52.sos.ioos.asset.AbstractAsset;

public interface SosAsset {
    public String getId();
    public AbstractAsset getAsset();    
    public String getLongName();
    public String getShortName();
    void setLongName(String longName);
    void setShortName(String shortName);
}