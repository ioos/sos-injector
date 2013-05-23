package com.axiomalaska.sos.data;

import org.n52.sos.ioos.asset.NetworkAsset;

public class SosNetwork extends AbstractSosAsset {
	private NetworkAsset asset;
	private PublisherInfo publisherInfo;
	
	@Override
	public NetworkAsset getAsset() {
		return asset;
	}

	public void setAsset(NetworkAsset asset) {
		this.asset = asset;
	}

    public PublisherInfo getPublisherInfo() {
        return publisherInfo;
    }

    public void setPublisherInfo(PublisherInfo publisherInfo) {
        this.publisherInfo = publisherInfo;
    }
}
