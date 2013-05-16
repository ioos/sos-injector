/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiomalaska.sos;

import com.axiomalaska.sos.data.SosStation;

/**
 *
 * @author SCowan
 */
public interface ISOFileWriter {
    
    public void writeISOFileForStation(SosStation station);
    
}
