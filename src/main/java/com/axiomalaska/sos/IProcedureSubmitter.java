package com.axiomalaska.sos;

import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.exception.SosCommunicationException;

public interface IProcedureSubmitter {

    public abstract boolean checkProcedureWithSos(AbstractSosAsset asset)
            throws SosCommunicationException;

}