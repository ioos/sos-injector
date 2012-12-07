package com.axiomalaska.sos;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.tools.HttpPart;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.NetworkRegisterSensorBuilder;

/**
 * This class is used to manage the creation of networks and offerings. 
 * 
 * @author lance finfrock
 */
public class NetworkSubmitter {
	private Logger logger;
	private IdCreator idCreator;
	private String sosUrl;
	private HttpSender httpSender = new HttpSender();
	
	public NetworkSubmitter(String sosUrl, Logger logger, IdCreator idCreator){
		this.sosUrl = sosUrl;
		this.logger = logger;
		this.idCreator = idCreator;
	}
	
	public Boolean checkNetworkWithSos(SosNetwork network) throws Exception {
		if (isNetworkCreated(network) && isOfferingCreated(network)) {
			return true;
		}
		else{
			Boolean networkCreated = createNewSosNetwork(network);
			Boolean offeringCreated = createOffering(network);
			
			return networkCreated && offeringCreated;
		}
	}

	private Boolean createNewSosNetwork(SosNetwork network) throws Exception {
		if (!isNetworkCreated(network)) {
			logger.info("Creating network: "
					+ idCreator.createNetworkId(network));

			NetworkRegisterSensorBuilder registerSensorBuilder = new NetworkRegisterSensorBuilder(
					network, idCreator);

			String xml = registerSensorBuilder.build();

			String response = httpSender.sendPostMessage(sosUrl, xml);

			if (response == null || response.contains("Exception")) {
				logger.error("network: " + idCreator.createNetworkId(network)
						+ " = " + response);
				return false;
			} else {
				logger.info("Finished creating network: "
						+ idCreator.createNetworkId(network));
				return true;
			}
		} else {
			return true;
		}
	}
	
	private Boolean createOffering(SosNetwork network) throws Exception {
		if (!isOfferingCreated(network)) {
			if (isNetworkCreated(network)) {
				String procedureId = idCreator.createNetworkId(network);
				List<HttpPart> httpParts = new ArrayList<HttpPart>();
				httpParts.add(new HttpPart("request", "CreateOffering"));
				httpParts.add(new HttpPart("id", procedureId));
				httpParts.add(new HttpPart("name", network.getOfferingName()));
				httpParts.add(new HttpPart("procedures", procedureId));
				httpParts.add(new HttpPart("allObservedProperties", "true"));
				httpParts.add(new HttpPart("allFeaturesOfInterest", "true"));

				String response = httpSender.sendGetMessage(getSosAdminUrl(),
						httpParts);

				System.out.println(response);

				if (response.equals("\"Offering " + procedureId + " created\"")) {
					return true;
				} else {
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
		String procedureId = idCreator.createNetworkId(network);
		List<HttpPart> httpParts = new ArrayList<HttpPart>();
		httpParts.add(new HttpPart("request", "OfferingExists"));
		httpParts.add(new HttpPart("id", procedureId));
		
		String response = httpSender.sendGetMessage(getSosAdminUrl(), httpParts);
		
		if(response.equals("true")){
			return true;
		}
		else{
			return false;
		}
	}
	
	private Boolean isNetworkCreated(SosNetwork network) throws Exception {
		String procedureId = idCreator.createNetworkId(network);
		DescribeSensorBuilder describeSensorBuilder = new DescribeSensorBuilder(
				procedureId);

		String text = describeSensorBuilder.build();

		String output = httpSender.sendPostMessage(sosUrl, text);

		return output != null && output.contains("sml:SensorML");
	}
	
	private String getSosAdminUrl(){
		int x = sosUrl.lastIndexOf("sos");
		
		return sosUrl.substring(0, x) + "/admin";
	}
}
