package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 23/08/16.
 */
public class DataCollection {

    @SerializedName("DeviceID")
    public String deviceID;
    @SerializedName("IsEncryptionRequired")
    public String isEncryptionRequired;
    @SerializedName("TokenAESKey")
    public String tokenAESKey;
    @SerializedName("TokenIVKey")
    public String tokenIVKey;
    @SerializedName("TokenKey")
    public String tokenKey;
}
