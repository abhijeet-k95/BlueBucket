package com.unical.bluebucket.bluebucket.Bar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuCardDetails {

    @SerializedName("drink_id")
    @Expose
    String drinkId;

    @SerializedName("drink_name")
    @Expose
    String drinkName;

    @SerializedName("drink_type")
    @Expose
    String drinkType;

    @SerializedName("store_idac_service")
    @Expose
    String acService;

    @SerializedName("30ml")
    @Expose
    String ml30;
    @SerializedName("60ml")
    @Expose
    String ml60;
    @SerializedName("90ml")
    @Expose
    String ml90;
    @SerializedName("120ml")
    @Expose
    String ml120;
    @SerializedName("150ml")
    @Expose
    String ml150;
    @SerializedName("180ml")
    @Expose
    String ml180;

    public String getDrinkId() {
        return drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getAcService() {
        return acService;
    }

    public String getMl30() {
        return ml30;
    }

    public String getMl60() {
        return ml60;
    }

    public String getMl90() {
        return ml90;
    }

    public String getMl120() {
        return ml120;
    }

    public String getMl150() {
        return ml150;
    }

    public String getMl180() {
        return ml180;
    }


    @Override
    public String toString() {
        return   ml30 +
                " " + ml60 +
                " " + ml90 +
                " " + ml120 +
                " " + ml150 +
                " " + ml180  ;

    }
}
