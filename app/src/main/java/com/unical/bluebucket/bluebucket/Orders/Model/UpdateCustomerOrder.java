package com.unical.bluebucket.bluebucket.Orders.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class UpdateCustomerOrder {

    @SerializedName("order_id")
    @Expose
    String orderId;


    @SerializedName("sub_order_count")
    @Expose
    int subOrderCount;

    @SerializedName("bottles_id")
    @Expose
    String[] bottleId;

    @SerializedName("quantity_ml")
    @Expose
    String[] quantityMl;

    @SerializedName("quantity_percent")
    @Expose
    String[] quantityPercent;

    @SerializedName("amount")
    @Expose
    String[] amount;

    String jsonFromJavaArrayList;
    Gson gsonBuilder = new GsonBuilder().create();



    public UpdateCustomerOrder(String orderId, int subOrderCount, String[] bottleId, String[] quantityMl, String[] quantityPercent, String[] amount) {
        this.orderId = orderId;
        this.subOrderCount = subOrderCount;
        this.bottleId = bottleId;
        this.quantityMl = quantityMl;
        this.quantityPercent = quantityPercent;
        this.amount = amount;

        }

    @Override
    public String toString() {
        return "{" +
                "\"orderId\"  : \"" + orderId + '\"' +
                ", \"subOrderCount\"  : \"" + subOrderCount +'\"' +
                ", \"bottleId\"  : " + gsonBuilder.toJson(bottleId) +
                ", \"quantityMl\" : " + gsonBuilder.toJson(quantityMl) +
                ", \"quantityPercent\" : " + gsonBuilder.toJson(quantityPercent) +
                ", \"amount\" : " + gsonBuilder.toJson(amount) +
                '}';
    }
}
