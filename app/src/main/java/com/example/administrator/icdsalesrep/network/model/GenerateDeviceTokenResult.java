package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 23/08/16.
 */
public class GenerateDeviceTokenResult {

    @SerializedName("DataCollection")
    public DataCollection dataCollection;
    @SerializedName("Message")
    public String message;
    @SerializedName("ResponseCode")
    public String responseCode;
    @SerializedName("ServiceName")
    public String serviceName;

}
