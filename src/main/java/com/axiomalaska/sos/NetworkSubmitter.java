package com.axiomalaska.sos;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.tools.HttpPart;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.NetworkRegisterSensorBuilder;

/**
 * This class is used to manage the creation of networks and offerings. 
 * 
 * @author lance finfrock
 */
public class NetworkSubmitter {
	private Logger logger;
	private String sosUrl;
	private HttpSender httpSender = new HttpSender();
	
	public NetworkSubmitter(String sosUrl, Logger logger){
		this.sosUrl = sosUrl;
		this.logger = logger;
	}
        
        public Boolean checkNetworkWithSos(SosNetwork network, PublisherInfo publisherInfo) throws Exception {
            return checkNetworkWithSos(network, publisherInfo, false);
        }
	
	public Boolean checkNetworkWithSos(SosNetwork network, 
			PublisherInfo publisherInfo, boolean networkAll) throws Exception {
		if (isNetworkCreated(network) && isOfferingCreated(network)) {
			return true;
		}
		else{
			Boolean networkCreated = createNewSosNetwork(network, publisherInfo);
			Boolean offeringCreated = createOffering(network, networkAll);
			
			return networkCreated && offeringCreated;
		}
	}

	private Boolean createNewSosNetwork(SosNetwork network, 
			PublisherInfo publisherInfo) throws Exception {
		if (!isNetworkCreated(network)) {
			logger.info("Creating network: " + network.getId());

			NetworkRegisterSensorBuilder registerSensorBuilder = new NetworkRegisterSensorBuilder(
					network, publisherInfo);

			String xml = registerSensorBuilder.build();

			String response = httpSender.sendPostMessage(sosUrl, xml);

			if (response == null || response.contains("Exception")) {
				logger.error("network: " + network.getId()
						+ " = " + response);
				return false;
			} else {
				logger.info("Finished creating network: " + network.getId());
				return true;
			}
		} else {
			return true;
		}
	}
	
	private Boolean createOffering(SosNetwork network, boolean networkAll) throws Exception {
		if (!isOfferingCreated(network)) {
			if (isNetworkCreated(network)) {
                logger.info("Creating offering: " + network.getId());
				List<HttpPart> httpParts = new ArrayList<HttpPart>();
				httpParts.add(new HttpPart("request", "CreateOffering"));
				httpParts.add(new HttpPart("id", network.getId()));
				httpParts.add(new HttpPart("name", network.getLongName()));
				httpParts.add(new HttpPart("procedures", network.getId()));
				httpParts.add(new HttpPart("allObservedProperties", "true"));
				httpParts.add(new HttpPart("allFeaturesOfInterest", "true"));
                                if (networkAll)
                                    httpParts.add(new HttpPart("allProcedures", "true"));
                                else
                                    httpParts.add(new HttpPart("procedures", network.getId()));
                                    
				String response = httpSender.sendGetMessage(getSosAdminUrl(),
						httpParts);

				if (response != null && response.equals("\"Offering " + network.getId() + " created\"")) {
					return true;
				} else {
                                    System.err.println(response);
					return false;
				}
			}
			else{
				return false;
			}
		} else {
			return true;
		}
	}
	
	private Boolean isOfferingCreated(SosNetwork network) throws Exception {
		List<HttpPart> httpParts = new ArrayList<HttpPart>();
		httpParts.add(new HttpPart("request", "OfferingExists"));
		httpParts.add(new HttpPart("id", network.getId()));
		
		String response = httpSender.sendGetMessage(getSosAdminUrl(), httpParts);
		
		if(response != null && response.equals("true")){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Boolean isNetworkCreated(SosNetwork network) throws Exception {
		DescribeSensorBuilder describeSensorBuilder = new DescribeSensorBuilder(network.getId());

		String text = describeSensorBuilder.build();

		String output = httpSender.sendPostMessage(sosUrl, text);

		return output != null && output.contains("sml:SensorML");
	}
	
	private String getSosAdminUrl(){
		int x = sosUrl.lastIndexOf("sos");
		
		return sosUrl.substring(0, x) + "admin";
	}
}
