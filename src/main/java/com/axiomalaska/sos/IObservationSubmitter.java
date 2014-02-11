package com.axiomalaska.sos;

import com.axiomalaska.ioos.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.exception.InvalidObservationCollectionException;
import com.axiomalaska.sos.exception.ObservationRetrievalException;
import com.axiomalaska.sos.exception.SosCommunicationException;

public interface IObservationSubmitter {

    /**
     * Pull: Update the observations of a station with a specific phenomenon in the SOS server.
     * 
     * @param sensor - the sensor from which to pull observations 
     * @param phenomenon - the phenomenon for which to pull observations 
     * @param observationRetriever - retrieves observations from the sensor
     * @throws InvalidObservationCollectionException 
     * @throws ObservationRetrievalException 
     * @throws SosCommunicationException 
     * @throws UnsupportedGeometryTypeException 
     */
    public abstract void update(SosSensor sensor, Phenomenon phenomenon,
            ObservationRetriever observationRetriever)
            throws InvalidObservationCollectionException,
            ObservationRetrievalException, SosCommunicationException,
            UnsupportedGeometryTypeException;

}