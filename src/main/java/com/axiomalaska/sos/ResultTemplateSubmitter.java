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
import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.tools.ResponseInterpretter;
import com.axiomalaska.sos.xmlbuilder.InsertResultTemplateBuilder;
import com.vividsolutions.jts.geom.Geometry;

public class ResultTemplateSubmitter {
    private static final Logger LOGGER = Logger.getLogger(ResultTemplateSubmitter.class);

    private String sosUrl;
    private Set<String> resultTemplatesInSos = new HashSet<String>();
    
    public ResultTemplateSubmitter(String sosUrl) {
        this.sosUrl = sosUrl;
    }

	public boolean checkResultTemplateWithSos(SosSensor sensor, Phenomenon phenomenon, Geometry geometry)
	        throws SosCommunicationException, UnsupportedGeometryTypeException{
	    String resultTemplateId = IdCreator.createResultTemplateId(sensor, phenomenon, geometry);
	    
            try {
                return resultTemplatesInSos.contains(resultTemplateId)
                        || isResultTemplateCreated(resultTemplateId, sensor, phenomenon, geometry)
                        || createResultTemplate(resultTemplateId, sensor, phenomenon, geometry);
            } catch (IOException e) {
                throw new SosCommunicationException(e);
            } catch (XmlException e) {
                throw new SosCommunicationException(e);                
            }
	}

    private boolean isResultTemplateCreated(String resultTemplateId, SosSensor sensor, Phenomenon phenomenon,
            Geometry geometry) throws IOException, XmlException{
        //XXX currently there's no way to do a GetResultTemplate check for a specific foi/geometry or template url
        //always try to create the result template and ignore 
        return false;
        //save this code in case a better way to check for specific result templates appears later
//        XmlObject xbResponse = ResponseInterpretter.getXmlObject(
//                HttpSender.sendPostMessage(sosUrl, new GetResultTemplateBuilder(sensor, phenomenon).build()));
//        if (xbResponse == null || !(xbResponse instanceof GetResultTemplateResponseDocument)){
//            return false;
//        }
//        resultTemplatesInSos.add(resultTemplateId);
//        return true;
    }

	private boolean createResultTemplate(String resultTemplateId, SosSensor sensor, Phenomenon phenomenon,
            Geometry geometry) throws IOException, XmlException, UnsupportedGeometryTypeException{
		LOGGER.info("Creating result template " + resultTemplateId);
        XmlObject xbResponse = ResponseInterpretter.getXmlObject(
		        HttpSender.sendPostMessage(sosUrl, new InsertResultTemplateBuilder(sensor, phenomenon, geometry).build())); 
		if (xbResponse == null || ResponseInterpretter.isError(xbResponse)) {
		    //XXX ugly hack to work around inability to check for a specific foi or result template id in GetResultTemplate
		    if (ResponseInterpretter.onlyExceptionContains((ExceptionReportDocument) xbResponse,
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
