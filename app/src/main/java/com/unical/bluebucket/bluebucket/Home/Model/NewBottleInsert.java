package com.unical.bluebucket.bluebucket.Home.Model;

import com.google.gson.annotations.SerializedName;

public class NewBottleInsert {
    @SerializedName("success")
    boolean success;

    @SerializedName("error_code")
    String errorCode;


    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
