package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 23/08/16.
 */
public class SignInRequestData {

    @SerializedName("TokenKey")
    public String tokenKey;
    @SerializedName("DataCollection")
    public String dataCollection;
    @SerializedName("DeviceID")
    public String deviceID;


}
