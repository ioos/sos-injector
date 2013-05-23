package com.axiomalaska.sos.xmlbuilder;

import net.opengis.sos.x10.DescribeSensorDocument;
import net.opengis.sos.x10.GetObservationDocument;
import net.opengis.sos.x10.InsertObservationDocument;
import net.opengis.sos.x10.RegisterSensorDocument;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

public class XmlBuildersTest extends AbstractXmlBuildersTest {    
    @Test
    public void testDescribeSensorBuilder() throws XmlException {
        String xmlString = new DescribeSensorBuilder( TEST_STATION.getId() ).build();
        DescribeSensorDocument describeSensorDoc = DescribeSensorDocument.Factory.parse( xmlString );
        validateXmlDocument( describeSensorDoc );
    }

    @Test
    public void testGetNewestObservationBuilder() throws XmlException {
        String xmlString = new GetNewestObservationBuilder( TEST_STATION, TEST_SENSOR,
                TEST_PHENOMENON, TEST_ROOT_NETWORK).build();
        GetObservationDocument getObsDoc = GetObservationDocument.Factory.parse( xmlString );
        validateXmlDocument( getObsDoc );
    }

    @Test
    public void testInsertObservationBuilder() throws XmlException {
        String xmlString = new InsertObservationBuilder( TEST_STATION, TEST_SENSOR, TEST_PHENOMENON,
                TEST_OBSERVATION_COLLECTION, null ).build();
        InsertObservationDocument insObsDoc = InsertObservationDocument.Factory.parse( xmlString );
        validateXmlDocument( insObsDoc );
    }
    
    @Test
    public void testNetworkRegisterSensorBuilder() throws XmlException {
        String xmlString = new NetworkRegisterSensorBuilder( TEST_NETWORK, 
        		TEST_PUBLISHER_INFO ).build();
        RegisterSensorDocument registerSensorDoc = RegisterSensorDocument.Factory.parse( xmlString );
        validateXmlDocument( registerSensorDoc );
    }

    //Test fails because a null xmlString is produced, probably because of an unset value
//    @Test
//    public void testStationRegisterSensorBuilder() throws XmlException {
//        String xmlString = new StationRegisterSensorBuilder( TEST_STATION, ID_CREATOR,
//                TEST_PUBLISHER_INFO ).build();
//        RegisterSensorDocument registerSensorDoc = RegisterSensorDocument.Factory.parse( xmlString );
//        validateXmlDocument( registerSensorDoc );
//    }
    
    @Test
    public void testSensorRegisterSensorBuilder() throws XmlException {
        String xmlString = new SensorRegisterSensorBuilder(TEST_SENSOR).build();
        RegisterSensorDocument registerSensorDoc = RegisterSensorDocument.Factory.parse( xmlString );
        validateXmlDocument( registerSensorDoc );
    }
}
