package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.PosEncodedGeom;
import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

public class GeomHelper {
    private static final GeometryFactory geomFactory =
            new GeometryFactory( new PrecisionModel(), 4326 );
    
    public static Point createLatLngPoint( Double lat, Double lng){
        return geomFactory.createPoint(new Coordinate(lng, lat));
    }
    
    public static Point createLatLngPoint( Double lat, Double lng, Double height){
        return geomFactory.createPoint(new Coordinate(lng, lat, height));
    }
    
    public static boolean equal2d(Point a, Point b){
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    public static boolean hasHeight(Geometry geom) throws UnsupportedGeometryTypeException{
        if (geom instanceof Point){            
            return hasHeight(((Point) geom).getCoordinate());
        } else if (geom instanceof LineString){
            LineString lineString = (LineString) geom;
            Coordinate[] coords = lineString.getCoordinates();
            for (int i = 0; i < coords.length; i++) {
                if (hasHeight(coords[i])) {
                    return true;
                }
            }
            return false;
        } else {
            throw new UnsupportedGeometryTypeException(geom);
        }
    }

    private static boolean hasHeight(Coordinate coord){
        return !Double.isNaN(coord.z);
    }
    
    public static double getHeight(Point point){
        return point.getCoordinate().z;
    }

    public static int getDimension(Geometry geom) throws UnsupportedGeometryTypeException{
        return getDimension(hasHeight(geom));
    }

    public static int getDimension(boolean hasHeight) throws UnsupportedGeometryTypeException{
        return hasHeight ? 3 : 2;
    }
    
    public static PosEncodedGeom posEncodeGeom(Geometry geom) throws UnsupportedGeometryTypeException{ 
        if (geom instanceof Point){
            Point point = (Point) geom;
            boolean hasHeight = hasHeight(point);
            return new PosEncodedGeom(getDimension(hasHeight), posEncodeCoord(point.getCoordinate(), hasHeight));
        } else if (geom instanceof LineString){
            LineString lineString = (LineString) geom;
            boolean hasHeight = hasHeight(lineString);
            StringBuilder builder = new StringBuilder();
            Coordinate[] coords = lineString.getCoordinates();
            for (int i = 0; i < coords.length; i++) {
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(posEncodeCoord(coords[i], hasHeight));
            }            
            return new PosEncodedGeom(getDimension(hasHeight), builder.toString());
        } else {
            throw new UnsupportedGeometryTypeException(geom);
        }        
    }

    /**
     * Encode the coordinate for GML 3.2 gml:pos, possibly including z.
     * Note: since we're only dealing with EPSG 4326, we always use y/x order (lng/lat)
     * 
     * @param coord
     * @param includeZ
     * @return
     */
    private static String posEncodeCoord(Coordinate coord, boolean includeZ){
        return coord.y + " " + coord.x + (includeZ ? " " + coord.z : ""); 
    }
}
