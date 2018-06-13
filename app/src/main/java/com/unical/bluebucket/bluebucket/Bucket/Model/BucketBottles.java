package com.unical.bluebucket.bluebucket.Bucket.Model;

import com.google.gson.annotations.SerializedName;

public class BucketBottles {

    @SerializedName("bottle_id")
    private String bottleId;

    @SerializedName("drink_id")
    private String drinkId;

    @SerializedName("drink_name")
    private String drinkName;

    @SerializedName("drink_type")
    private String drinkType;

    @SerializedName("purch_date_time")
    private String purchedOn;

    @SerializedName("remaining")
    private String remainingQuantity;


    public String getBottleId() {
        return bottleId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getPurchedOn() {
        return purchedOn;
    }

    public String getRemainingQuantity() {
        return remainingQuantity;
    }
}
