package com.unical.bluebucket.bluebucket.Bar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceNewOrder {


    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("error_code")
    @Expose
    String eroorCode;

    @SerializedName("error_message")
    @Expose
    String errorMessage;

    @SerializedName("order_id")
    @Expose
    String orderId;

    public boolean isSuccess() {
        return success;
    }

    public String getEroorCode() {
        return eroorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getOrderId() {
        return orderId;
    }
}
