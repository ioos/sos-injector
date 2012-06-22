package com.axiomalaska.sos.xmlbuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Builds a SOS DescribeSensor XML String with a passed in Station
 * 
 * @author Lance Finfrock
 */
public class DescribeSensorBuilder extends SosXmlBuilder {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private String procedureId;

  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------
	
	public DescribeSensorBuilder(String procedureId) {
		this.procedureId = procedureId;
	}

  // ---------------------------------------------------------------------------
  // Public Member
  // ---------------------------------------------------------------------------

	/**
	 * Build the XML String
	 * 
		<DescribeSensor version="1.0.0" service="SOS"
			xmlns="http://www.opengis.net/sos/1.0"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.opengis.net/sos/1.0
			http://schemas.opengis.net/sos/1.0.0/sosDescribeSensor.xsd"
			outputFormat="text/xml;subtype=&quot;sensorML/1.0.1&quot;">
			
			<procedure>urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1</procedure>
			
		</DescribeSensor>
	 * 
	 */
	public String build() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element describeSensor = doc.createElement("DescribeSensor");
			describeSensor.setAttribute("service", "SOS");
			describeSensor.setAttribute("version", "1.0.0");
			describeSensor.setAttribute("xmlns",
					"http://www.opengis.net/sos/1.0");
			describeSensor.setAttribute("xmlns:xsi",
					"http://www.w3.org/2001/XMLSchema-instance");
			describeSensor
					.setAttribute(
							"xsi:schemaLocation",
							"http://www.opengis.net/sos/1.0 http://schemas.opengis.net/sos/1.0.0/sosDescribeSensor.xsd");
			describeSensor.setAttribute("outputFormat",
					"text/xml;subtype=\"sensorML/1.0.1\"");
			doc.appendChild(describeSensor);

			Element procedure = doc.createElement("procedure");

			procedure.appendChild(doc.createTextNode(procedureId));
			
			describeSensor.appendChild(procedure);

			return getString(doc);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return null;
	}
}
