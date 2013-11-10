package com.axiomalaska.sos.xmlbuilder;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.joda.time.DateTime;
import org.n52.sos.ioos.asset.NetworkAsset;
import org.n52.sos.ioos.asset.SensorAsset;
import org.n52.sos.ioos.asset.StationAsset;

import com.axiomalaska.ioos.sos.GeomHelper;
import com.axiomalaska.phenomena.Phenomena;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.phenomena.PhenomenonImp;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosSource;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.tools.XmlOptionsHelper;
import com.vividsolutions.jts.geom.Point;

public class AbstractXmlBuildersTest {
    protected Point TEST_STATION_POINT = GeomHelper.createLatLngPoint(70.4, -148.527);    
    protected Point TEST_SENSOR_POINT = GeomHelper.createLatLngPoint(70.4, -148.527, -1.0);
    protected Point TEST_FOI_POINT = GeomHelper.createLatLngPoint(70.4, -148.527, -10.0);
    protected String TEST_SOURCE_ID = "aoos";
    protected String TEST_NETWORK_ID = "all";
    protected String TEST_STATION_ID = "prudhoe";
    protected String TEST_STATION_NAME = "Prudhoe Bay Station";
    protected String TEST_STATION_PLATFORM_TYPE = "cman";
    protected String TEST_SENSOR_ID = "airtemp";
    protected String TEST_SHORT_NAME = "short_name";
    protected String TEST_LONG_NAME = "long_name";
    protected String TEST_FOI_NAME = "Prudhoe Bay";
    protected String TEST_EMAIL = "noone@nowhere.com";   
    protected String TEST_CITY = "Purgatory";
    protected String TEST_STATE = "Alaska";
    protected String TEST_OPERATOR_SECTOR = "academic";
    protected String TEST_PUBLISHER_NAME = "AOOS";
    protected String TEST_COUNTRY = "USA";
    protected String TEST_WEB_ADDRESS = "http://www.aoos.org";
    protected String TEST_ADDRESS = "1234 Nowhere Dr";
    protected String TEST_ZIPCODE = "99999";
    protected String TEST_ORGANIZATION = "test_organization";
    protected String TEST_SPONSOR = "test_sponsor";
    protected PublisherInfo TEST_PUBLISHER_INFO = buildTestPublisherInfo();    
    protected SosNetwork TEST_ROOT_NETWORK = buildRootNetwork();
    protected SosSource TEST_SOURCE = buildTestSource();
    protected Phenomenon TEST_PHENOMENON = buildTestPhenomenon();    
    protected SosNetwork TEST_NETWORK = buildTestNetwork();
    protected SosStation TEST_STATION = buildTestStation();    
    protected SosSensor TEST_SENSOR = buildTestSensor();    
    protected ObservationCollection TEST_OBSERVATION_COLLECTION = buildTestObservationCollection();
    
    private SosNetwork buildRootNetwork(){
        SosNetwork rootNetwork = new SosNetwork();
        rootNetwork.setAsset(new NetworkAsset("aoos","all"));
        rootNetwork.setLongName(TEST_LONG_NAME);
        rootNetwork.setShortName("All observations");
        return rootNetwork;
    }
    
    private SosSource buildTestSource(){
        SosSource testSource = new SosSource();
        testSource.setName( TEST_ORGANIZATION );
        testSource.setEmail( TEST_EMAIL );
        testSource.setCity( TEST_CITY );
        testSource.setState( TEST_STATE );
        testSource.setOperatorSector( TEST_OPERATOR_SECTOR );
        testSource.setAddress( TEST_ADDRESS );
        testSource.setCountry( TEST_COUNTRY );
        testSource.setWebAddress( TEST_WEB_ADDRESS );
        testSource.setZipcode( TEST_ZIPCODE );
        return testSource;
    }
    
    private SosNetwork buildTestNetwork(){
        SosNetwork testNetwork = new SosNetwork();
        testNetwork.setAsset(new NetworkAsset(TEST_SOURCE_ID, TEST_NETWORK_ID));
        testNetwork.setShortName( TEST_SHORT_NAME );
        testNetwork.setLongName( TEST_LONG_NAME );
        return testNetwork;
    }
    
    private SosStation buildTestStation(){    
        SosStation testStation = new SosStation();
        testStation.setAsset(new StationAsset(TEST_SOURCE_ID, TEST_STATION_ID));
        testStation.setShortName( TEST_SHORT_NAME );
        testStation.setLongName( TEST_LONG_NAME );
        testStation.setFeatureOfInterestName( TEST_FOI_NAME );
        testStation.setLocation( TEST_STATION_POINT );
        testStation.setShortName( TEST_STATION_NAME );
        List<SosNetwork> networks = new ArrayList<SosNetwork>();
        networks.add( TEST_NETWORK );
        testStation.setNetworks( networks );
        testStation.setPlatformType( TEST_STATION_PLATFORM_TYPE );
        testStation.setSource( TEST_SOURCE );        
        List<SosSensor> sensors = new ArrayList<SosSensor>();
        sensors.add( TEST_SENSOR );
        testStation.setSensors( sensors );
        testStation.setSponsor(TEST_SPONSOR);
        return testStation;
    }
    
    private SosSensor buildTestSensor(){
        SosSensor testSensor = new SosSensor();
        testSensor.setStation(TEST_STATION);
        testSensor.setAsset(new SensorAsset(TEST_SOURCE_ID, TEST_STATION_ID, TEST_SENSOR_ID));
        testSensor.setLongName( TEST_LONG_NAME );
        testSensor.setLocation(TEST_SENSOR_POINT);
        List<SosNetwork> networks = new ArrayList<SosNetwork>();
        networks.add( TEST_NETWORK );        
        testSensor.setNetworks( networks );
        List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
        phenomena.add( TEST_PHENOMENON );                
        testSensor.setPhenomena( phenomena );
        return testSensor;
    }
    
    private Phenomenon buildTestPhenomenon(){
        Phenomenon testPhenomenon = new PhenomenonImp();
        try{
            testPhenomenon = Phenomena.instance().AIR_TEMPERATURE;
        }
        catch (Exception x){
            System.out.println("error creating phenomenon: " + x.getMessage());
        }
        
        return testPhenomenon;
    }
    
    private ObservationCollection buildTestObservationCollection(){
        ObservationCollection obsCol = new ObservationCollection();
        obsCol.setSensor( TEST_SENSOR );
        obsCol.setPhenomenon( TEST_PHENOMENON );
        for (int i = 0; i < 50; i++) {
            obsCol.addObservationValue(DateTime.now().minusHours(i), Math.random());
        }        
        return obsCol;                
    }
    
    private PublisherInfo buildTestPublisherInfo(){
        PublisherInfo testPublisherInfo = new PublisherInfo();
        testPublisherInfo.setEmail( TEST_EMAIL );
        testPublisherInfo.setName( TEST_PUBLISHER_NAME );
        testPublisherInfo.setCountry( TEST_COUNTRY );
        testPublisherInfo.setWebAddress( TEST_WEB_ADDRESS );
        return testPublisherInfo;
    }
    
    protected void validateXmlDocument( XmlObject xmlDocument ){
        ArrayList<XmlError> validationErrors = new ArrayList<XmlError>();
        XmlOptions validationOptions = new XmlOptions();
        validationOptions.setErrorListener(validationErrors);
        xmlDocument.validate(validationOptions);

        List<String> excludeErrors = new ArrayList<String>();
        excludeErrors.add("Expected element '_Feature@http://www.opengis.net/gml' instead of");
        excludeErrors.add("Expected element 'InsertionMetadata@http://www.opengis.net/swes/2.0'");
        excludeErrors.add("Expected element 'AbstractFeature@http://www.opengis.net/gml/3.2' instead of 'SF_SpatialSamplingFeature@http://www.opengis.net/samplingSpatial/2.0'");
        for( XmlError validationError : validationErrors ){
            boolean skip = false;
            for( String excludeError : excludeErrors ){
                if (validationError.getMessage().startsWith(excludeError)) {
                    skip = true;
                }
            }

            if( !skip ){
                fail( validationError.getMessage() );
            }
        }
        
        String xmlStr = xmlDocument.xmlText(XmlOptionsHelper.getInstance().getXmlOptions());
        if (xmlStr.contains("xmlns:ns=")){
            fail("Document contains ns: namespace: \n" + xmlStr);
        }
    }
    
    protected void addSchemaLocations(XmlObject xmlObject, Map<String,String> locations) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String,String> entry : locations.entrySet()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(entry.getKey() + " " + entry.getValue());
        }
        XmlCursor cursor = xmlObject.newCursor();
        if (cursor.toFirstChild()) {
          cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"),
                  sb.toString());
        }       
    }
}
