package com.axiomalaska.sos.tools;

import ucar.units.Unit;

import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.AssetWithLocation;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

/**
 * This class is used to create the ID of the objects of the SOS. This was
 * created because of the multiple changes being made to the IDs.
 * 
 * @author Lance Finfrock
 * 
 */
public class IdCreator {
	public static String createFeatureOfInterestId(SosSensor sensor){
		return sensor.getId();
	}
	
	public static String createFeatureOfInterestName(SosStation station, SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}
	
	public static String createFeatureOfInterestId(SosStation station, SosSensor sensor){
	    return station.getId();
	}

    public static String createObservationFeatureOfInterestId(SosSensor sensor, Geometry foiGeometry)
            throws UnsupportedGeometryTypeException {
        //use station as the feature asset if sensor location is the same (minimize number of fois in SOS)
        AssetWithLocation asset = sensor.getLocation().equals(sensor.getStation().getLocation()) ?
                sensor.getStation() : sensor;

        StringBuilder builder = new StringBuilder();
        if (foiGeometry instanceof Point){
            Point foiPoint = (Point) foiGeometry;
            if (!GeomHelper.equal2d(asset.getLocation(), foiPoint)) {
                builder.append("lat" + foiPoint.getY());
                builder.append("lng" + foiPoint.getX());
            }
            if (GeomHelper.hasHeight(foiPoint)) {
                builder.append("height" + GeomHelper.getHeight(foiPoint) + "m");
            }
        }
        if (builder.length() > 0 ){
            return asset.getId() + "(" + builder.toString() + ")";
        } else {
            return asset.getId();
        }
    }

    public static String createObservationFeatureOfInterestName(SosSensor sensor, Geometry foiGeometry)
            throws UnsupportedGeometryTypeException {
        return createObservationFeatureOfInterestId(sensor, foiGeometry);
    }

    public static String createResultTemplateId(SosSensor sensor, Phenomenon phenomenon, Geometry foiGeometry)
            throws UnsupportedGeometryTypeException {
        return createObservationFeatureOfInterestId(sensor, foiGeometry) + ":" + phenomenon.getId();       
    }
    
    public static String createUnitHref(Unit unit){
        return unit == null ? null : IoosSosConstants.UDUNITS_URN_PREFIX + unit.toString().replace(' ', '.');
    }
}
