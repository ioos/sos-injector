package com.axiomalaska.sos.xmlbuilder;

import net.opengis.sensorML.x101.SensorMLDocument;

import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.exception.UnsupportedSosAssetTypeException;

public abstract class AbstractSwesBuilder {
    protected SensorMLDocument buildSensorMLDocument(AbstractSosAsset asset,
            PublisherInfo publisherInfo) throws UnsupportedSosAssetTypeException{
        if (asset instanceof SosNetwork) {
            return new NetworkSensorMLBuilder((SosNetwork) asset, publisherInfo).build();
        } else if (asset instanceof SosStation) {
            return new StationSensorMLBuilder((SosStation) asset, publisherInfo).build();
        } else if (asset instanceof SosSensor) {
            return new SensorSensorMLBuilder((SosSensor) asset).build();
        } else {
            throw new UnsupportedSosAssetTypeException(asset);
        }
    }
}
