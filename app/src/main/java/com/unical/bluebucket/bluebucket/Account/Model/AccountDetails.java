package com.unical.bluebucket.bluebucket.Account.Model;

import com.google.gson.annotations.SerializedName;

public class AccountDetails {
    @SerializedName("name")
    private String Name;

    @SerializedName("email_id")
    private String Email;

    @SerializedName("mobile_number")
    private String PhoneNumber;


    @SerializedName("address")
    private String Address;



    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }
}
