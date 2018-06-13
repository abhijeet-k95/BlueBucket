package com.unical.bluebucket.bluebucket.Orders.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCustomerOrderResult {
    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("error_code")
    @Expose
    String eroorCode;

    @SerializedName("error_message")
    @Expose
    String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public String getEroorCode() {
        return eroorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
