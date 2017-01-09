package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 24/08/16.
 */
public class GenericResponseData {


    @SerializedName("DataCollection")
    public String dataCollection;
    @SerializedName("Message")
    public String message;
    @SerializedName("ResponseCode")
    public String responseCode;
    @SerializedName("ServiceName")
    public String serviceName;
    @SerializedName("TokenKey")
    public String tokenKey;
}
