package com.axiomalaska.sos.exception;

import com.axiomalaska.sos.data.AbstractSosAsset;

public class UnsupportedSosAssetTypeException extends Exception{
    private static final long serialVersionUID = -7384817046855791036L;

    public UnsupportedSosAssetTypeException(AbstractSosAsset asset){
        super("Unsupported SOS asset type " + asset.getClass().getName());    
    }
}
