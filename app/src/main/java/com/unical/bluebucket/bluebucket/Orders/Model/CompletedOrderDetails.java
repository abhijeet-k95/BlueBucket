package com.unical.bluebucket.bluebucket.Orders.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletedOrderDetails {


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
}
