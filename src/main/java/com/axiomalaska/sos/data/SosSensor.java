package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.List;

import org.n52.sos.ioos.asset.SensorAsset;

import com.axiomalaska.phenomena.Phenomenon;

public class SosSensor extends AbstractSosAsset{
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
    
    private SosStation station;
    private SensorAsset asset;
	private List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
	private List<SosNetwork> networks = new ArrayList<SosNetwork>();

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public List<Phenomenon> getPhenomena() {
		return phenomena;
	}

	public SosStation getStation() {
        return station;
    }

    public void setStation(SosStation station) {
        this.station = station;
    }

    public void setPhenomena(List<Phenomenon> phenomena) {
		this.phenomena = phenomena;
	}
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks(){
		return networks;
	}
	
	public void setNetworks(List<SosNetwork> networks){
		this.networks = networks;
	}
	
	public void addNetwork(SosNetwork network){
		networks.add(network);
	}

    @Override
    public SensorAsset getAsset() {
        return asset;
    }

    public void setAsset(SensorAsset asset) {
        this.asset = asset;
    }
}
