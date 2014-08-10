package com.axiomalaska.sos.data;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ObservationCollectionTest {
    @Test
    public void testEmptyObservationValuesToString() {
        ObservationCollection obsCollection = new ObservationCollection();
        assertTrue(obsCollection.getObservationValues().isEmpty());
        obsCollection.toString();
    }

    @Test
    public void testNullObservationValuesToString() {
        ObservationCollection obsCollection = new ObservationCollection();
        obsCollection.setObservationValues(null);
        assertNull(obsCollection.getObservationValues());
        obsCollection.toString();
    }    
}
