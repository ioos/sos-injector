/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
package com.axiomalaska.sos.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.xmlbeans.XmlOptions;

import com.axiomalaska.sos.XmlNamespaceConstants;

/**
 * SOS XML utility class
 * 
 */
public final class XmlOptionsHelper {
    //xml namespaces and prefixes
    private static final String characterEncoding = "UTF-8";
    private static XmlOptionsHelper instance;

    private final ReentrantLock lock = new ReentrantLock();
    private XmlOptions xmlOptions;
    
    public static XmlOptionsHelper getInstance() {
        if (instance == null) {
            instance = new XmlOptionsHelper();
        }
        return instance;
    }

    /**
     * private constructor for singleton
     */
    private XmlOptionsHelper() {}

    private Map<String, String> getPrefixMap() {
        Map<String, String> prefixMap = new HashMap<String, String>();
        prefixMap.put(XmlNamespaceConstants.NS_OGC, XmlNamespaceConstants.NS_OGC_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SML, XmlNamespaceConstants.NS_SML_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_OM, XmlNamespaceConstants.NS_OM_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_OM_2, XmlNamespaceConstants.NS_OM_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SA, XmlNamespaceConstants.NS_SA_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SOS, XmlNamespaceConstants.NS_SOS_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SOS_20, XmlNamespaceConstants.NS_SOS_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_XLINK, XmlNamespaceConstants.NS_XLINK_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_XSI, XmlNamespaceConstants.NS_XSI_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_XS, XmlNamespaceConstants.NS_XS_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SWE_101, XmlNamespaceConstants.NS_SWE_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SWE_20, XmlNamespaceConstants.NS_SWE_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SWES_20, XmlNamespaceConstants.NS_SWES_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_GML, XmlNamespaceConstants.NS_GML_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_GML_32, XmlNamespaceConstants.NS_GML_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SAMS, XmlNamespaceConstants.NS_SAMS_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_SF, XmlNamespaceConstants.NS_SF_PREFIX);
        prefixMap.put(XmlNamespaceConstants.NS_FES_2, XmlNamespaceConstants.NS_FES_2_PREFIX);
        return prefixMap;
    }

    public XmlOptions getXmlOptions() {
        if (this.xmlOptions == null) {
            this.lock.lock();
            if (xmlOptions == null) {
                xmlOptions = new XmlOptions();
                Map<String, String> prefixes = getPrefixMap();
                xmlOptions.setSaveSuggestedPrefixes(prefixes);
                xmlOptions.setSaveImplicitNamespaces(prefixes);
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSavePrettyPrint();
                xmlOptions.setSaveNamespacesFirst();
                xmlOptions.setCharacterEncoding(characterEncoding);
            }
            this.lock.unlock();
        }
        return xmlOptions;
    }
}
