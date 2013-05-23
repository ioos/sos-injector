package com.axiomalaska.sos;

import javax.xml.namespace.QName;


public class SosInjectorConstants {
    public static final String SOS_SERVICE = "SOS";
    public static final String SOS_V100 = "1.0.0";
    public static final String SOS_V20 = "2.0";
    public static final String SML_V101 = "1.0.1";
    
    public static final String IOOS_SML_FORMAT = "text/xml;subtype=\"sensorML/1.0.1/profiles/ioos_sos/1.0\"";
    public static final String NONE_OBS_PROP = "NONE";
    public static final String MEASUREMENT_DEF = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";
    public static final String SAMPLING_POINT_DEF = "http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingPoint";
    public static final String PHENOMENON_TIME = "phenomenonTime";
    
    public static final QName QN_SOS_INSERTION_METADATA = new QName(
            XmlNamespaceConstants.NS_SOS_20, "SosInsertionMetadata", XmlNamespaceConstants.NS_SOS_PREFIX);
    
    public static final QName QN_SYSTEM = new QName(
            XmlNamespaceConstants.NS_SML, "System", XmlNamespaceConstants.NS_SML_PREFIX);

    public static final QName QN_SIMPLEDATARECORD = new QName(
            XmlNamespaceConstants.NS_SWE_101, "SimpleDataRecord", XmlNamespaceConstants.NS_SWE_PREFIX);

    public static final QName QN_POINT = new QName(
            XmlNamespaceConstants.NS_GML, "Point", XmlNamespaceConstants.NS_GML_PREFIX);

    public static final QName QN_TIMEINSTANT = new QName(
            XmlNamespaceConstants.NS_GML, "TimeInstant", XmlNamespaceConstants.NS_GML_PREFIX);
    
}
