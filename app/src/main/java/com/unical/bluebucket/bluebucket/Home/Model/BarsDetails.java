package com.unical.bluebucket.bluebucket.Home.Model;

import com.google.gson.annotations.SerializedName;

public class BarsDetails {


    @SerializedName("store_id")
    String barID;

    @SerializedName("store_name")
    String barName;

    @SerializedName("address")
    String barAddress;


    public String getBarID() {
        return barID;
    }

    public String getBarName() {
        return barName;
    }

    public String getBarAddress() {
        return barAddress;
    }
}
