package com.axiomalaska.sos.xmlbuilder2;

import net.opengis.sos.x10.DescribeSensorDocument;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.axiomalaska.sos.xmlbuilder.AbstractXmlBuildersTest;

public class XmlBuildersTest extends AbstractXmlBuildersTest {
    @Test
    public void testDescribeSensorBuilder() throws XmlException {
        DescribeSensorDocument describeSensorDoc = new DescribeSensorBuilder( TEST_STATION.getId() ).build();
        validateXmlDocument( describeSensorDoc );
    }
    
    @Test
    public void testNetworkSensorMLBuilder() throws XmlException {
        validateXmlDocument( new NetworkSensorMLBuilder(TEST_NETWORK).build() );
    }

    @Test
    public void testStationSensorMLBuilder() throws XmlException {
        validateXmlDocument( new StationSensorMLBuilder(TEST_STATION).build() );
    }

    @Test
    public void testSensorSensorMLBuilder() throws XmlException {
        validateXmlDocument( new SensorSensorMLBuilder(TEST_SENSOR).build() );
    }

    @Test
    public void testInsertSensorBuilder() throws Exception {
        validateXmlDocument( new InsertSensorBuilder(TEST_SENSOR).build() );
    }
    
    @Test
    public void testUpdateSensorDescriptionBuilder() throws Exception {
        validateXmlDocument( new UpdateSensorDescriptionBuilder(TEST_NETWORK).build() );
    }

    @Test
    public void testInsertResultTemplateBuilder() throws Exception {
        validateXmlDocument( new InsertResultTemplateBuilder(TEST_SENSOR, TEST_PHENOMENON, -5.0).build() );
    }
    
    @Test
    public void testInsertResultBuilder() throws Exception {
        validateXmlDocument( new InsertResultBuilder(TEST_OBSERVATION_COLLECTION).build() );
    }

    @Test
    public void testGetResultTemplateBuilder() throws Exception {
        validateXmlDocument( new GetResultTemplateBuilder(TEST_SENSOR, TEST_PHENOMENON).build() );
    }
}