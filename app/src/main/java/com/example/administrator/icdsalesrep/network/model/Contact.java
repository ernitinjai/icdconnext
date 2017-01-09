package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 24/08/16.
 */
public class Contact {

    @SerializedName("ContactType")
    public String contactType;
    @SerializedName("ContactName")
    public String contactName;
    @SerializedName("ContactCompanyName")
    public String contactCompanyName;
    @SerializedName("Email")
    public String email;
    @SerializedName("PhoneNo")
    public String phoneNo;
    @SerializedName("RefNo")
    public String refNo;
}
