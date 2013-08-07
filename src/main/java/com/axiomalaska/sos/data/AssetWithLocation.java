package com.axiomalaska.sos.data;

import com.vividsolutions.jts.geom.Point;

public interface AssetWithLocation {
    public String getId();
    public Point getLocation();
}
