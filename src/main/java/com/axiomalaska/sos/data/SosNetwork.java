package com.axiomalaska.sos.data;

import org.n52.sos.ioos.asset.NetworkAsset;

public class SosNetwork extends AbstractSosAsset {
	private NetworkAsset asset;
	
	@Override
	public NetworkAsset getAsset() {
		return asset;
	}

	public void setAsset(NetworkAsset asset) {
		this.asset = asset;
	}
}
