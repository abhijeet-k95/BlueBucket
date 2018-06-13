package com.unical.bluebucket.bluebucket.Home.Model;

import com.google.gson.annotations.SerializedName;

public class DrinkDetails {

    @SerializedName("drink_id")
    String drinkId;

    @SerializedName("drink_type")
    String drinkType;

    @SerializedName("drink_name")
    String drinkName;

    @SerializedName("max_quantity_ml")
    String drinkQuantity;

    @SerializedName("price")
    String drinkPrice;

    public String getDrinkId() {
        return drinkId;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getDrinkQuantity() {
        return drinkQuantity;
    }

    public String getDrinkPrice() {
        return drinkPrice;
    }
}
