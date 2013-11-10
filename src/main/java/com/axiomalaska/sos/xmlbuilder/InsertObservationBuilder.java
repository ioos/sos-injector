package com.axiomalaska.sos.xmlbuilder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.opengis.gml.x32.AbstractGeometryType;
import net.opengis.gml.x32.CodeWithAuthorityType;
import net.opengis.gml.x32.DirectPositionListType;
import net.opengis.gml.x32.DirectPositionType;
import net.opengis.gml.x32.LineStringType;
import net.opengis.gml.x32.PointType;
import net.opengis.om.x20.OMObservationType;
import net.opengis.samplingSpatial.x20.SFSpatialSamplingFeatureDocument;
import net.opengis.samplingSpatial.x20.SFSpatialSamplingFeatureType;
import net.opengis.sos.x20.InsertObservationDocument;
import net.opengis.sos.x20.InsertObservationType;
import net.opengis.swe.x20.BooleanDocument;
import net.opengis.swe.x20.BooleanType;
import net.opengis.swe.x20.DataArrayDocument;
import net.opengis.swe.x20.DataArrayType;
import net.opengis.swe.x20.DataArrayType.ElementType;
import net.opengis.swe.x20.DataRecordType;
import net.opengis.swe.x20.DataRecordType.Field;
import net.opengis.swe.x20.QuantityType;
import net.opengis.swe.x20.TextEncodingType;
import net.opengis.swe.x20.TimeType;

import org.joda.time.DateTime;

import com.axiomalaska.ioos.sos.GeomHelper;
import com.axiomalaska.ioos.sos.IoosSosConstants;
import com.axiomalaska.ioos.sos.PosEncodedGeom;
import com.axiomalaska.ioos.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.tools.XmlHelper;
import com.axiomalaska.sos.tools.XmlOptionsHelper;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class InsertObservationBuilder {
    // ---------------------------------------------------------------------------
    // Private Members
    // ---------------------------------------------------------------------------
    ObservationCollection observationCollection;
    
    // ---------------------------------------------------------------------------
    // Public Members
    // ---------------------------------------------------------------------------
	
    public InsertObservationBuilder(ObservationCollection observationCollection) {
        this.observationCollection = observationCollection;
    }

    /**
	 * Build the XML String
	 * 
    <?xml version="1.0" encoding="UTF-8"?>
    <sos:InsertObservation service="SOS" version="2.0.0"
      xmlns:sos="http://www.opengis.net/sos/2.0"
      xmlns:swes="http://www.opengis.net/swes/2.0"
      xmlns:swe="http://www.opengis.net/swe/2.0"
      xmlns:sml="http://www.opengis.net/sensorML/1.0.1"
      xmlns:gml="http://www.opengis.net/gml/3.2"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      xmlns:om="http://www.opengis.net/om/2.0"
      xmlns:sams="http://www.opengis.net/samplingSpatial/2.0"
      xmlns:sf="http://www.opengis.net/sampling/2.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd">
      <swes:extension>
        <swe:Boolean definition="SplitDataArrayIntoObservations">
          <swe:value>true</swe:value>
        </swe:Boolean>
      </swes:extension>
      <sos:offering>http://www.52north.org/test/offering/6</sos:offering>
      <sos:observation>
          <om:OM_Observation gml:id="o1">
              <om:type xlink:href="http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_SWEArrayObservation"/>
              <om:phenomenonTime>
                   <gml:TimePeriod gml:id="phenomenonTime">
                       <gml:beginPosition>2012-11-19T13:30:00+02:00</gml:beginPosition>
                       <gml:endPosition>2012-11-19T13:44:00+02:00</gml:endPosition>
                   </gml:TimePeriod>
               </om:phenomenonTime>
               <om:resultTime>
                   <gml:TimeInstant gml:id="resultTime">
                       <gml:timePosition>2012-11-19T13:50:00+02:00</gml:timePosition>
                   </gml:TimeInstant>
               </om:resultTime>
               <om:procedure xlink:href="http://www.52north.org/test/procedure/6"/>
               <om:observedProperty xlink:href="http://www.52north.org/test/observableProperty/6"/>
               <om:featureOfInterest>
                   <sams:SF_SpatialSamplingFeature gml:id="ssf_test_feature_6">
                       <gml:identifier codeSpace="">http://www.52north.org/test/featureOfInterest/6</gml:identifier>
                       <gml:name>52°North</gml:name>
                       <sf:type xlink:href="http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingPoint"/>
                       <sf:sampledFeature xlink:href="http://www.52north.org/test/featureOfInterest/1"/>
                       <sams:shape>
                           <gml:Point gml:id="test_feature_6">
                               <gml:pos srsName="http://www.opengis.net/def/crs/EPSG/0/4326">51.935101100104916 7.651968812254194</gml:pos>
                           </gml:Point>
                       </sams:shape>
                   </sams:SF_SpatialSamplingFeature>
               </om:featureOfInterest>
               <om:result xsi:type="swe:DataArrayPropertyType">
                   <swe:DataArray>
                       <swe:elementCount>
                           <swe:Count>
                               <swe:value>15</swe:value>
                           </swe:Count>
                       </swe:elementCount>
                       <swe:elementType name="defs">
                           <swe:DataRecord>
                               <swe:field name="phenomenonTime">
                                   <swe:Time definition="http://www.opengis.net/def/property/OGC/0/PhenomenonTime">
                                       <swe:uom xlink:href="http://www.opengis.net/def/uom/ISO-8601/0/Gregorian"/>
                                   </swe:Time>
                               </swe:field>
                               <swe:field name="test_observable_property_6">
                                   <swe:Quantity definition="http://www.52north.org/test/observableProperty/6">
                                       <swe:uom code="test_unit_6"/>
                                   </swe:Quantity>
                               </swe:field>
                           </swe:DataRecord>
                       </swe:elementType>
                       <swe:encoding>
                           <swe:TextEncoding tokenSeparator="#" blockSeparator="@"/>
                       </swe:encoding>
                       <swe:values>
                         2012-11-19T13:30:00+02:00#159.15@2012-11-19T13:31:00+02:00#159.15@2012-11-19T13:32:00+02:00#159.85@
                         2012-11-19T13:33:00+02:00#160.5@2012-11-19T13:34:00+02:00#160.9@2012-11-19T13:35:00+02:00#160.7@
                         2012-11-19T13:36:00+02:00#160.5@2012-11-19T13:37:00+02:00#160.6@2012-11-19T13:38:00+02:00#160.5@
                         2012-11-19T13:39:00+02:00#160.4@2012-11-19T13:40:00+02:00#160.34@2012-11-19T13:41:00+02:00#160.25@
                         2012-11-19T13:42:00+02:00#159.79@2012-11-19T13:43:00+02:00#159.56@2012-11-19T13:44:00+02:00#159.25
                       </swe:values>
                   </swe:DataArray>
               </om:result>
           </om:OM_Observation>
       </sos:observation>
     </sos:InsertObservation> 
     * @throws UnsupportedGeometryTypeException 
     * @ 
	 */
	public InsertObservationDocument build() throws UnsupportedGeometryTypeException{
	    InsertObservationDocument xbInsertObservationDoc = InsertObservationDocument.Factory.newInstance(
	            XmlOptionsHelper.getInstance().getXmlOptions());
	    InsertObservationType xbInsertObservation = xbInsertObservationDoc.addNewInsertObservation();
	    xbInsertObservation.setService(SosInjectorConstants.SOS_SERVICE);
	    xbInsertObservation.setVersion(SosInjectorConstants.SOS_V200);
	    
	    // set SplitDataArrayIntoObservations extension
	    BooleanDocument xbSplitArrayExtensionDoc = BooleanDocument.Factory.newInstance(
	            XmlOptionsHelper.getInstance().getXmlOptions());
	    BooleanType xbSplitArrayExtension = xbSplitArrayExtensionDoc.addNewBoolean();
	    xbSplitArrayExtension.setDefinition(SosInjectorConstants.SPLIT_OBSERVATIONS_EXTENSION);
	    xbSplitArrayExtension.setValue(Boolean.TRUE);
	    xbInsertObservation.addNewExtension().set(xbSplitArrayExtensionDoc);

	    //set offering to station's id
	    xbInsertObservation.addOffering(observationCollection.getSensor().getStation().getId());
	    
	    OMObservationType xbOMObservation = xbInsertObservation.addNewObservation().addNewOMObservation();
	    xbOMObservation.setId("o1");
	    xbOMObservation.addNewType().setHref(SosInjectorConstants.SWE_ARRAY_DEF);
	    xbOMObservation.addNewProcedure().setHref(observationCollection.getSensor().getId());
	    xbOMObservation.addNewObservedProperty().setHref(observationCollection.getPhenomenon().getId());
	    
	    //null times
	    xbOMObservation.addNewPhenomenonTime().setNilReason(SosInjectorConstants.TEMPLATE);
	    xbOMObservation.addNewResultTime().setNilReason(SosInjectorConstants.TEMPLATE);
	    
	    //feature
	    SFSpatialSamplingFeatureDocument xbSfSpatialSamplingFeatureDoc =
	            SFSpatialSamplingFeatureDocument.Factory.newInstance(
	                    XmlOptionsHelper.getInstance().getXmlOptions());
	    SFSpatialSamplingFeatureType xbSfSpatialSamplingFeature = xbSfSpatialSamplingFeatureDoc.addNewSFSpatialSamplingFeature();
	    xbSfSpatialSamplingFeature.setId("foi1"); 
	    CodeWithAuthorityType xbFoiGmlIdentifier = xbSfSpatialSamplingFeature.addNewIdentifier();
	    xbFoiGmlIdentifier.setCodeSpace("");
	    xbFoiGmlIdentifier.setStringValue(IdCreator.createObservationFeatureOfInterestId(observationCollection.getSensor(),
	            observationCollection.getGeometry()));
	    xbSfSpatialSamplingFeature.addNewName().setStringValue(
	            IdCreator.createObservationFeatureOfInterestName(observationCollection.getSensor(),
	                    observationCollection.getGeometry()));
	    xbSfSpatialSamplingFeature.addNewType().setHref(SosInjectorConstants.SAMPLING_POINT_DEF);
	    //TODO should this really be unknown? maybe earth?
	    xbSfSpatialSamplingFeature.addNewSampledFeature().setHref(SosInjectorConstants.UNKNOWN_DEF);
	    encodeGeometry(xbSfSpatialSamplingFeature.addNewShape().addNewAbstractGeometry(),
	            observationCollection.getGeometry());
	    XmlHelper.append(xbOMObservation.addNewFeatureOfInterest(), xbSfSpatialSamplingFeatureDoc);

	    //result
	    DataArrayDocument xbDataArrayDoc = DataArrayDocument.Factory.newInstance(
	            XmlOptionsHelper.getInstance().getXmlOptions());
	    DataArrayType xbDataArray = xbDataArrayDoc.addNewDataArray1();
	    xbDataArray.addNewElementCount().addNewCount().setValue(BigInteger.valueOf(
	            observationCollection.getObservationValues().size()));
	    ElementType xbElementType = xbDataArray.addNewElementType();
	    xbElementType.setName("components");
        DataRecordType xbDataRecord = (DataRecordType) xbElementType.addNewAbstractDataComponent()
                .substitute(SosInjectorConstants.QN_DATARECORD_SWE2, DataRecordType.type);
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
        xbQuantity.setDefinition(observationCollection.getPhenomenon().getId());
        xbQuantity.addNewUom().setHref(IdCreator.createUnitHref(observationCollection.getPhenomenon().getUnit()));
                
	    TextEncodingType xbTextEncodingType = (TextEncodingType) xbDataArray.addNewEncoding()
	            .addNewAbstractEncoding().substitute(SosInjectorConstants.QN_TEXTENCODING, TextEncodingType.type);
	    xbTextEncodingType.setBlockSeparator(SosInjectorConstants.BLOCK_SEPARATOR);
	    xbTextEncodingType.setTokenSeparator(SosInjectorConstants.TOKEN_SEPARATOR);
	    xbTextEncodingType.setDecimalSeparator(SosInjectorConstants.DECIMAL_SEPARATOR);
	    
	    xbDataArray.addNewValues().newCursor().setTextValue(encodeResultValues(observationCollection));

	    xbOMObservation.addNewResult().set(xbDataArrayDoc);	    
	    return xbInsertObservationDoc;
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

    private String encodeResultValues(ObservationCollection obsCollection) {
        StringBuilder str = new StringBuilder();
        boolean first = true;
        List<DateTime> obsTimes = new ArrayList<DateTime>(obsCollection.getObservationValues().keySet());
        Collections.sort(obsTimes);
        for (DateTime time : obsTimes) {
            Double value = obsCollection.getObservationValues().get(time);
            if (!first) {
                str.append(SosInjectorConstants.BLOCK_SEPARATOR);               
            }
            first = false;
            str.append(time);
            str.append(SosInjectorConstants.TOKEN_SEPARATOR);
            str.append(value);           
        }
        return str.toString();
    }	
}
