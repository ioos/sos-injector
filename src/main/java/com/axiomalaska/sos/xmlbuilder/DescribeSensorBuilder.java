package com.axiomalaska.sos.xmlbuilder;

import net.opengis.sos.x10.DescribeSensorDocument;
import net.opengis.sos.x10.DescribeSensorDocument.DescribeSensor;

import com.axiomalaska.sos.SosInjectorConstants;
import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.tools.XmlOptionsHelper;

/**
 * Builds a SOS DescribeSensor XML String with a passed in Station
 * 
 * @author Lance Finfrock
 */
public class DescribeSensorBuilder {

  // ---------------------------------------------------------------------------
  // Private Data
  // ---------------------------------------------------------------------------

	private AbstractSosAsset asset;

  // ---------------------------------------------------------------------------
  // Constructor
  // ---------------------------------------------------------------------------
	
	public DescribeSensorBuilder(AbstractSosAsset asset) {
		this.asset = asset;
	}

  // ---------------------------------------------------------------------------
  // Public Member
  // ---------------------------------------------------------------------------

	/**
	 * Build the XML String
	 * 
		<DescribeSensor version="1.0.0" service="SOS"
			xmlns="http://www.opengis.net/sos/1.0"
			outputFormat="text/xml;subtype=&quot;sensorML/1.0.1&quot;">			
			<procedure>urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1</procedure>
		</DescribeSensor>
	 * 
	 */
	public DescribeSensorDocument build() {
	    DescribeSensorDocument xbDescribeSensorDoc = DescribeSensorDocument.Factory.newInstance(
	            XmlOptionsHelper.getInstance().getXmlOptions());
	    DescribeSensor xbDescribeSensor = xbDescribeSensorDoc.addNewDescribeSensor();
	    xbDescribeSensor.setService(SosInjectorConstants.SOS_SERVICE);
	    xbDescribeSensor.setVersion(SosInjectorConstants.SOS_V100);
	    xbDescribeSensor.setOutputFormat(SosInjectorConstants.IOOS_SML_FORMAT);
	    xbDescribeSensor.setProcedure(asset.getId());
	    return xbDescribeSensorDoc;
	}
}
