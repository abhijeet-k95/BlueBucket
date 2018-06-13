package com.unical.bluebucket.bluebucket.Login.Model;

import com.google.gson.annotations.SerializedName;

public class LogInData {


    @SerializedName("success")
    boolean  success;

    @SerializedName("error_code")
    String errorCode;

    @SerializedName("error_message")
    String errorMessage;

    @SerializedName("customer_id")
    String custId;

    @SerializedName("mobile_number")
    String mobileNumber;

    @SerializedName("address")
    String address;




    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCustId() {
        return custId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }
}
