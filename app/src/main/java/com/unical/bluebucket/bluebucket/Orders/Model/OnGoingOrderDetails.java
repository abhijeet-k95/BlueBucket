package com.unical.bluebucket.bluebucket.Orders.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnGoingOrderDetails {

    @SerializedName("success")
    boolean  success;

    @SerializedName("error_code")
    String errorCode;

    @SerializedName("error_message")
    String errorMessage;


    @SerializedName("order_id")
    @Expose
    String orderId;

    @SerializedName("store_name")
    @Expose
    String storeName;

    @SerializedName("store_id")
    @Expose
    String storeId;

    @SerializedName("ac_service")
    @Expose
    String serviceType;

    @SerializedName("order_place_time")
    @Expose
    String orderPlaceTime;

    @SerializedName("order_status")
    @Expose
    String orderStatus;

    @SerializedName("accepted")
    @Expose
    boolean accepted;


    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getOrderPlaceTime() {
        return orderPlaceTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
