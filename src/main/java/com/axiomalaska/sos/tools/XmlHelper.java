package com.axiomalaska.sos.tools;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;


public class XmlHelper {
    public static String xmlText(XmlObject xmlObject){
        return xmlObject.xmlText(XmlOptionsHelper.getInstance().getXmlOptions());        
    }
    
    public static void addSchemaLocations(XmlObject xmlObject, Map<String,String> locations) {
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
