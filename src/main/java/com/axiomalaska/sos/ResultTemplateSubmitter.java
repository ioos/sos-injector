package com.axiomalaska.sos;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.opengis.ows.x11.ExceptionReportDocument;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.exception.SosCommunicationException;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.tools.ResponseInterpretter;
import com.axiomalaska.sos.xmlbuilder.InsertResultTemplateBuilder;

public class ResultTemplateSubmitter {
    private static final Logger LOGGER = Logger.getLogger(ResultTemplateSubmitter.class);

    private String sosUrl;
    private Set<String> resultTemplatesInSos = new HashSet<String>();
    
    public ResultTemplateSubmitter(String sosUrl) {
        this.sosUrl = sosUrl;
    }

	public boolean checkResultTemplateWithSos(SosSensor sensor, Phenomenon phenomenon, Double height)
	        throws SosCommunicationException{
	    String resultTemplateId = IdCreator.createResultTemplateId(sensor, phenomenon, height);
	    try {
            return resultTemplatesInSos.contains(resultTemplateId)
                    || isResultTemplateCreated(resultTemplateId, sensor, phenomenon, height)
                    || createResultTemplate(resultTemplateId, sensor, phenomenon, height);
        } catch (Exception e) {
            throw new SosCommunicationException(e);
        }
	}

    private boolean isResultTemplateCreated(String resultTemplateId, SosSensor sensor, Phenomenon phenomenon,
            Double height) throws IOException, XmlException{
        //XXX currently there's no way to do a GetResultTemplate check for a specific foi/depth or template url
        //always try to create the result template and ignore 
        return false;        
//        XmlObject xbResponse = XmlObject.Factory.parse(
//                HttpSender.sendPostMessage(sosUrl, new GetResultTemplateBuilder(sensor, phenomenon).build()));
//        if (xbResponse == null || !(xbResponse instanceof GetResultTemplateResponseDocument)){
//            return false;
//        }
//        resultTemplatesInSos.add(resultTemplateId);
//        return true;
    }

	private boolean createResultTemplate(String resultTemplateId, SosSensor sensor, Phenomenon phenomenon,
            Double height) throws IOException, XmlException{
		LOGGER.info("Creating result template " + resultTemplateId);
		XmlObject xbResponse = XmlObject.Factory.parse(
		        HttpSender.sendPostMessage(sosUrl, new InsertResultTemplateBuilder(sensor, phenomenon, height).build())); 
		if (xbResponse == null || ResponseInterpretter.isError(xbResponse)) {
		    //XXX ugly hack to work around inability to check for a specific foi or result template id in GetResultTemplate
		    if (ResponseInterpretter.getFirstExceptionText((ExceptionReportDocument) xbResponse).equals(
		            "The requested resultTemplate identifier (" + resultTemplateId
		            + ") is already registered at this service")){
		        resultTemplatesInSos.add(resultTemplateId);
		        return true;
		    }
			LOGGER.error("Error creating result template " + resultTemplateId);
			return false;
		}
		resultTemplatesInSos.add(resultTemplateId);
		LOGGER.info("Finished creating result template " + resultTemplateId);
		return true;
	}
}
