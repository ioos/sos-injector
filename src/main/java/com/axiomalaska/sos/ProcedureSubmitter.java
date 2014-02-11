package com.axiomalaska.sos;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.opengis.sensorML.x101.SensorMLDocument;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.n52.sos.ioos.asset.NetworkAsset;

import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.exception.SosCommunicationException;
import com.axiomalaska.sos.exception.UnsupportedSosAssetTypeException;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.tools.ResponseInterpretter;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertSensorBuilder;

public class ProcedureSubmitter implements IProcedureSubmitter {
    private static final Logger LOGGER = Logger.getLogger(ProcedureSubmitter.class);

    private String sosPoxUrl;
    private String sosProcedureExistsUrl;
    private String authorizationToken;
    private PublisherInfo publisherInfo;
    private Set<AbstractSosAsset> proceduresInSos = new HashSet<AbstractSosAsset>();
    
    public ProcedureSubmitter(String sosUrl, String authorizationToken, PublisherInfo publisherInfo) {
        this.sosPoxUrl = sosUrl + SosInjectorConstants.POX_ENDPOINT;
        this.sosProcedureExistsUrl = sosUrl + SosInjectorConstants.PROCEDURE_EXISTS_ENDPOINT;
        this.authorizationToken = authorizationToken;        
        this.publisherInfo = publisherInfo;
    }

	/* (non-Javadoc)
     * @see com.axiomalaska.sos.IProcedureSubmitter#checkProcedureWithSos(com.axiomalaska.sos.data.AbstractSosAsset)
     */
	@Override
    public boolean checkProcedureWithSos(AbstractSosAsset asset) throws SosCommunicationException{	    
	    if (asset instanceof SosStation) {
	        SosStation station = (SosStation) asset;
	        station.addNetwork(createNetworkAll());
	        for (SosNetwork network : ((SosStation) asset).getNetworks()) {
	            checkProcedureWithSos(network);
	        }
	    } else if (asset instanceof SosSensor) {
	        checkProcedureWithSos(((SosSensor) asset).getStation());
	    }
	    try {
            return proceduresInSos.contains(asset) || isProcedureCreated(asset) || createProcedure(asset);
        } catch (Exception e) {
            throw new SosCommunicationException(e);
        }
	}

    private boolean isProcedureCreated(AbstractSosAsset asset) throws XmlException, IOException{
        //try shortcut first
        try {
            return ResponseInterpretter.getExists(HttpSender.sendGetMessage(
                    sosProcedureExistsUrl + asset.getId()));
        } catch (Exception e) {
            //NOOP, keep going and try the normal SOS request
        }

        XmlObject xbResponse = ResponseInterpretter.getXmlObject(
                HttpSender.sendPostMessage(sosPoxUrl, authorizationToken,
                        new DescribeSensorBuilder(asset).build()));
        if (xbResponse == null || !(xbResponse instanceof SensorMLDocument)){
            return false;
        }
        proceduresInSos.add(asset);
        return true;
    }

	private boolean createProcedure(AbstractSosAsset asset) throws XmlException, IOException,
	        UnsupportedSosAssetTypeException {
		LOGGER.info("Creating procedure: " + asset.getId());
        XmlObject xbResponse = ResponseInterpretter.getXmlObject(
		        HttpSender.sendPostMessage(sosPoxUrl, authorizationToken,
		                new InsertSensorBuilder(asset, publisherInfo).build())); 
		if (xbResponse == null || ResponseInterpretter.isError(xbResponse)) {
			LOGGER.error("Error creating procedure " + asset.getId() + ":\n" + xbResponse);
			return false;
		}
        proceduresInSos.add(asset);		
		LOGGER.info("Finished creating procedure " + asset.getId());
		return true;
	}

    /**
     * Creates the network-all network which will contain all procedures
     * @return SosNetwork containing info for network all
     */
    private SosNetwork createNetworkAll() {
        SosNetwork netall = new SosNetwork();
        String authority = publisherInfo.getCode() == null ? "ioos" : publisherInfo.getCode();
        netall.setAsset(new NetworkAsset(authority,"all"));
        netall.setLongName("Contains all procedures in the SOS Server");
        netall.setShortName("network-all");
        return netall;
    }
}
