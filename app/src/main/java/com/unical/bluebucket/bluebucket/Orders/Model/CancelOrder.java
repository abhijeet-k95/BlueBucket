package com.unical.bluebucket.bluebucket.Orders.Model;

import com.google.gson.annotations.SerializedName;

public class CancelOrder {

    @SerializedName("success")
    boolean success;

    @SerializedName("error_code")
    String errorCode;

    @SerializedName("error_message")
    String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
