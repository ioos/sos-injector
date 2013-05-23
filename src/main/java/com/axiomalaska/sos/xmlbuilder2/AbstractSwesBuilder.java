package com.axiomalaska.sos.xmlbuilder2;

import net.opengis.sensorML.x101.SensorMLDocument;

import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;

public abstract class AbstractSwesBuilder {
    protected SensorMLDocument buildSensorMLDocument(AbstractSosAsset asset) throws Exception {
        if (asset instanceof SosNetwork) {
            return new NetworkSensorMLBuilder((SosNetwork) asset).build();
        } else if (asset instanceof SosStation) {
            return new StationSensorMLBuilder((SosStation) asset).build();
        } else if (asset instanceof SosSensor) {
            return new SensorSensorMLBuilder((SosSensor) asset).build();
        } else {
            throw new Exception("Unsupported SOS asset type " + asset.getClass().getName());
        }
    }
}
