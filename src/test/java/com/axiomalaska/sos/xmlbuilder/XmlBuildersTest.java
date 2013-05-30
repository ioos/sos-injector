package com.axiomalaska.sos.xmlbuilder;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.sos.exception.UnsupportedSosAssetTypeException;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.GetNewestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.GetOldestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.GetResultTemplateBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertResultBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertResultTemplateBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.NetworkSensorMLBuilder;
import com.axiomalaska.sos.xmlbuilder.SensorSensorMLBuilder;
import com.axiomalaska.sos.xmlbuilder.StationSensorMLBuilder;
import com.axiomalaska.sos.xmlbuilder.UpdateSensorDescriptionBuilder;

public class XmlBuildersTest extends AbstractXmlBuildersTest {
    @Test
    public void testDescribeSensorBuilder() throws XmlException {
        validateXmlDocument( new DescribeSensorBuilder(TEST_STATION).build() );
    }
    
    @Test
    public void testNetworkSensorMLBuilder() throws XmlException {
        validateXmlDocument( new NetworkSensorMLBuilder(TEST_NETWORK, TEST_PUBLISHER_INFO).build() );
    }

    @Test
    public void testStationSensorMLBuilder() throws XmlException {
        validateXmlDocument( new StationSensorMLBuilder(TEST_STATION, TEST_PUBLISHER_INFO).build() );
    }

    @Test
    public void testSensorSensorMLBuilder() throws XmlException {
        validateXmlDocument( new SensorSensorMLBuilder(TEST_SENSOR).build() );
    }

    @Test
    public void testInsertSensorBuilder() throws UnsupportedSosAssetTypeException  {
        validateXmlDocument( new InsertSensorBuilder(TEST_SENSOR, TEST_PUBLISHER_INFO).build() );
    }
    
    @Test
    public void testUpdateSensorDescriptionBuilder() throws UnsupportedSosAssetTypeException  {
        validateXmlDocument( new UpdateSensorDescriptionBuilder(TEST_NETWORK, TEST_PUBLISHER_INFO).build() );
    }

    @Test
    public void testInsertResultTemplateBuilder() throws UnsupportedGeometryTypeException  {
        validateXmlDocument( new InsertResultTemplateBuilder(TEST_SENSOR, TEST_PHENOMENON, TEST_FOI_POINT).build() );
    }
    
    @Test
    public void testInsertResultBuilder() throws UnsupportedGeometryTypeException  {
        validateXmlDocument( new InsertResultBuilder(TEST_OBSERVATION_COLLECTION).build() );
    }

    @Test
    public void testGetResultTemplateBuilder()  {
        validateXmlDocument( new GetResultTemplateBuilder(TEST_SENSOR, TEST_PHENOMENON).build() );
    }

    @Test
    public void testGetNewestObservationBuilder() throws UnsupportedGeometryTypeException  {
        validateXmlDocument( new GetNewestObservationBuilder(TEST_SENSOR, TEST_PHENOMENON, TEST_FOI_POINT).build() );
    }

    @Test
    public void testGetLatestTemplateBuilder() throws UnsupportedGeometryTypeException  {
        validateXmlDocument( new GetOldestObservationBuilder(TEST_SENSOR, TEST_PHENOMENON, TEST_FOI_POINT).build() );
    }    
}