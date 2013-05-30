package com.axiomalaska.sos.xmlbuilder;

import java.math.BigInteger;

import net.opengis.gml.x32.DirectPositionListType;
import net.opengis.gml.x32.AbstractGeometryType;
import net.opengis.gml.x32.CodeWithAuthorityType;
import net.opengis.gml.x32.DirectPositionType;
import net.opengis.gml.x32.LineStringType;
import net.opengis.gml.x32.PointType;
import net.opengis.om.x20.OMObservationType;
import net.opengis.samplingSpatial.x20.SFSpatialSamplingFeatureDocument;
import net.opengis.samplingSpatial.x20.SFSpatialSamplingFeatureType;
import net.opengis.sos.x20.InsertResultTemplateDocument;
import net.opengis.sos.x20.InsertResultTemplateType;
import net.opengis.sos.x20.ResultTemplateType;
import net.opengis.swe.x20.DataRecordType;
import net.opengis.swe.x20.DataRecordType.Field;
import net.opengis.swe.x20.QuantityType;
import net.opengis.swe.x20.TextEncodingType;
import net.opengis.swe.x20.TimeType;

import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.PosEncodedGeom;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.sos.tools.GeomHelper;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.tools.XmlHelper;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class InsertResultTemplateBuilder {
    // ---------------------------------------------------------------------------
    // Private Members
    // ---------------------------------------------------------------------------
    SosSensor sensor;
    Phenomenon phenomenon;
    Geometry foiGeometry;
    
    // ---------------------------------------------------------------------------
    // Public Members
    // ---------------------------------------------------------------------------
	
    public InsertResultTemplateBuilder(SosSensor sensor, Phenomenon phenomenon, Geometry foiGeometry) {
        this.sensor = sensor;
        this.phenomenon = phenomenon;
        this.foiGeometry = foiGeometry;
    }

    /**
	 * Build the XML String
	 * 
    <?xml version="1.0" encoding="UTF-8"?>
    <sos:InsertResultTemplate service="SOS" version="2.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:swes="http://www.opengis.net/swes/2.0" xmlns:sos="http://www.opengis.net/sos/2.0"
        xmlns:swe="http://www.opengis.net/swe/2.0" xmlns:sml="http://www.opengis.net/sensorML/1.0.1"
        xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink"
        xmlns:om="http://www.opengis.net/om/2.0" xmlns:sams="http://www.opengis.net/samplingSpatial/2.0"
        xmlns:sf="http://www.opengis.net/sampling/2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sosInsertResultTemplate.xsd http://www.opengis.net/om/2.0 http://schemas.opengis.net/om/2.0/observation.xsd
        http://www.opengis.net/samplingSpatial/2.0 http://schemas.opengis.net/samplingSpatial/2.0/spatialSamplingFeature.xsd">
        <sos:proposedTemplate>
            <!-- Before using this example, make sure that all preconditions are fulfilled, 
                e.g. perform InsertSensor example. -->
            <sos:ResultTemplate>
                <swes:identifier>urn:ioos:sensor:wmo:41001:watertemp1:sea_water_temperature</swes:identifier>
                <sos:offering>urn:ioos:sensor:wmo:41001:watertemp1</sos:offering>
                <sos:observationTemplate>
                    <om:OM_Observation gml:id="template">
                        <om:type xlink:href="http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement" />
                        <om:phenomenonTime nilReason="template" />
                        <om:resultTime nilReason="template" />
                        <om:procedure xlink:href="urn:ioos:sensor:wmo:41001:watertemp1" />
                        <om:observedProperty xlink:href="http://mmisw.org/ont/cf/parameter/sea_water_temperature" />
                        <om:featureOfInterest>
                            <sams:SF_SpatialSamplingFeature gml:id="foi">
                                <gml:identifier codeSpace="">urn:ioos:sensor:wmo:41001:watertemp1</gml:identifier>
                                <gml:name>urn:ioos:sensor:wmo:41001:watertemp1</gml:name>
                                <sf:type xlink:href="http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingPoint" />
                                <sf:sampledFeature xlink:href="http://www.opengis.net/def/nil/OGC/0/unknown" />
                                <sams:shape>
                                    <gml:Point gml:id="foiPoint">
                                        <gml:pos srsName="http://www.opengis.net/def/crs/EPSG/0/4326">34.7 -72.73</gml:pos>
                                    </gml:Point>
                                </sams:shape>
                            </sams:SF_SpatialSamplingFeature>
                        </om:featureOfInterest>
                        <om:result />
                    </om:OM_Observation>
                </sos:observationTemplate>
                <sos:resultStructure>
                    <swe:DataRecord>
                        <swe:field name="phenomenonTime">
                            <swe:Time
                                definition="http://www.opengis.net/def/property/OGC/0/PhenomenonTime">
                                <swe:uom xlink:href="http://www.opengis.net/def/uom/ISO-8601/0/Gregorian" />
                            </swe:Time>
                        </swe:field>
                        <swe:field name="sea_water_temperature">
                            <swe:Quantity definition="http://mmisw.org/ont/cf/parameter/sea_water_temperature">
                                <swe:uom code="degrees_Celcius" />
                            </swe:Quantity>
                        </swe:field>
                    </swe:DataRecord>
                </sos:resultStructure>
                <sos:resultEncoding>
                    <swe:TextEncoding tokenSeparator="@" blockSeparator="#" />
                </sos:resultEncoding>
            </sos:ResultTemplate>
        </sos:proposedTemplate>
    </sos:InsertResultTemplate>
     * @throws UnsupportedGeometryTypeException 
     * @ 
	 */
	public InsertResultTemplateDocument build() throws UnsupportedGeometryTypeException{
	    InsertResultTemplateDocument xbInsertResultTemplateDoc = InsertResultTemplateDocument.Factory.newInstance();
	    InsertResultTemplateType xbInsertResultTemplate = xbInsertResultTemplateDoc.addNewInsertResultTemplate();
	    xbInsertResultTemplate.setService(SosInjectorConstants.SOS_SERVICE);
	    xbInsertResultTemplate.setVersion(SosInjectorConstants.SOS_V200);
	    
	    ResultTemplateType xbResultTemplate = xbInsertResultTemplate.addNewProposedTemplate().addNewResultTemplate();
	    xbResultTemplate.setIdentifier(IdCreator.createResultTemplateId(sensor, phenomenon, foiGeometry));
	    xbResultTemplate.setOffering(sensor.getId());
	    
	    OMObservationType xbOMObservation = xbResultTemplate.addNewObservationTemplate().addNewOMObservation();
	    xbOMObservation.setId(SosInjectorConstants.TEMPLATE);
	    xbOMObservation.addNewType().setHref(SosInjectorConstants.MEASUREMENT_DEF);
	    xbOMObservation.addNewPhenomenonTime().setNilReason(SosInjectorConstants.TEMPLATE);
	    xbOMObservation.addNewResultTime().setNilReason(SosInjectorConstants.TEMPLATE);
	    xbOMObservation.addNewProcedure().setHref(sensor.getId());
	    xbOMObservation.addNewObservedProperty().setHref(phenomenon.getId());
	    xbOMObservation.addNewResult(); //empty result
	    
	    SFSpatialSamplingFeatureDocument xbSfSpatialSamplingFeatureDoc =
	            SFSpatialSamplingFeatureDocument.Factory.newInstance();
	    SFSpatialSamplingFeatureType xbSfSpatialSamplingFeature = xbSfSpatialSamplingFeatureDoc.addNewSFSpatialSamplingFeature();
	    xbSfSpatialSamplingFeature.setId("foi"); 
	    CodeWithAuthorityType xbFoiGmlIdentifier = xbSfSpatialSamplingFeature.addNewIdentifier();
	    xbFoiGmlIdentifier.setCodeSpace("");
	    xbFoiGmlIdentifier.setStringValue(IdCreator.createObservationFeatureOfInterestId(sensor, foiGeometry));
	    xbSfSpatialSamplingFeature.addNewName().setStringValue(
	            IdCreator.createObservationFeatureOfInterestName(sensor, foiGeometry));
	    xbSfSpatialSamplingFeature.addNewType().setHref(SosInjectorConstants.SAMPLING_POINT_DEF);
	    //TODO should this really be unknown? maybe earth?
	    xbSfSpatialSamplingFeature.addNewSampledFeature().setHref(SosInjectorConstants.UNKNOWN_DEF);
	    encodeGeometry(xbSfSpatialSamplingFeature.addNewShape().addNewAbstractGeometry(), foiGeometry);
	    XmlHelper.append(xbOMObservation.addNewFeatureOfInterest(), xbSfSpatialSamplingFeatureDoc);

	    DataRecordType xbDataRecord = (DataRecordType) xbResultTemplate.addNewResultStructure()
	            .addNewAbstractDataComponent().substitute(SosInjectorConstants.QN_DATARECORD_SWE2, DataRecordType.type);
	    //time field
	    Field xbTimeField = xbDataRecord.addNewField();
	    xbTimeField.setName(SosInjectorConstants.PHENOMENON_TIME);
	    TimeType xbTime = (TimeType) xbTimeField.addNewAbstractDataComponent()
	            .substitute(SosInjectorConstants.QN_SWE_TIME_SWE2, TimeType.type);
	    xbTime.setDefinition(IoosSosConstants.TIME_DEF);
	    xbTime.addNewUom().setHref(IoosSosConstants.GREGORIAN_DEF);
	    //observed property field
        Field xbObsPropField = xbDataRecord.addNewField();
        xbObsPropField.setName("observedProperty");
        QuantityType xbQuantity = (QuantityType) xbObsPropField.addNewAbstractDataComponent()
                .substitute(SosInjectorConstants.QN_SWE_QUANTITY_SWE2, QuantityType.type);
        xbQuantity.setDefinition(phenomenon.getId());
        //TODO check units, maybe it should be symbol or short name?
        xbQuantity.addNewUom().setCode(phenomenon.getUnit().getSymbol());
                
	    TextEncodingType xbTextEncodingType = (TextEncodingType) xbResultTemplate.addNewResultEncoding()
	            .addNewAbstractEncoding().substitute(SosInjectorConstants.QN_TEXTENCODING, TextEncodingType.type);
	    xbTextEncodingType.setBlockSeparator(SosInjectorConstants.RESULT_TEMPLATE_BLOCK_SEPARATOR);
	    xbTextEncodingType.setTokenSeparator(SosInjectorConstants.RESULT_TEMPLATE_TOKEN_SEPARATOR);
	    xbTextEncodingType.setDecimalSeparator(SosInjectorConstants.RESULT_TEMPLATE_DECIMAL_SEPARATOR);
	    
	    return xbInsertResultTemplateDoc;
	}
	
	/**
	 * Encode the geometry to gml
	 * If geometries need to be encoded elsewhere this should be moved to GeomHelper
	 * @param xbAbstractGeometryType AbstractGeometry to encode to
	 * @param geometry Geometry to encode
	 * @throws UnsupportedGeometryTypeException 
	 */
	private static void encodeGeometry(AbstractGeometryType xbAbstractGeometryType, Geometry geometry)
	        throws UnsupportedGeometryTypeException {
	    if (geometry instanceof Point) {
	        Point point = (Point) geometry;
            PointType xbPoint = (PointType) xbAbstractGeometryType.substitute(
                    SosInjectorConstants.QN_POINT, PointType.type);
            xbPoint.setId("foiPoint");
            DirectPositionType xbPos = xbPoint.addNewPos();
            xbPos.setSrsName(IoosSosConstants.EPSG_4326_DEF);
            PosEncodedGeom posEncodedGeom = GeomHelper.posEncodeGeom(point);
            xbPos.setSrsDimension(BigInteger.valueOf(posEncodedGeom.getDimension()));
            xbPos.setStringValue(posEncodedGeom.getPosList());
	    } else if (geometry instanceof LineString) {
	        LineString lineString = (LineString) geometry;
	        LineStringType xbLineString = (LineStringType) xbAbstractGeometryType.substitute(
                    SosInjectorConstants.QN_LINESTRING, LineStringType.type);
	        xbLineString.setId("foiLineString");
            DirectPositionListType xbPosList = xbLineString.addNewPosList();
            xbPosList.setSrsName(IoosSosConstants.EPSG_4326_DEF);
            PosEncodedGeom posEncodedGeom = GeomHelper.posEncodeGeom(lineString);
            xbPosList.setSrsDimension(BigInteger.valueOf(posEncodedGeom.getDimension()));
            xbPosList.setStringValue(posEncodedGeom.getPosList());	        
	    } else {
	        throw new UnsupportedGeometryTypeException(geometry);
	    }
	}
}
