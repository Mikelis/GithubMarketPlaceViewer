
package com.kaneps.mikelis.githubmarketplace.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("marketplaceListings")
    @Expose
    private MarketplaceListings marketplaceListings;

    public MarketplaceListings getMarketplaceListings() {
        return marketplaceListings;
    }

    public void setMarketplaceListings(MarketplaceListings marketplaceListings) {
        this.marketplaceListings = marketplaceListings;
    }

}
