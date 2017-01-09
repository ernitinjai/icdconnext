package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 23/08/16.
 */
public class SignInResponseDetailData {

    @SerializedName("LoginToken")
    public String loginToken;
    @SerializedName("ServiceURLEncryptionRequired")
    public String serviceURLEncryptionRequired;
    @SerializedName("ServiceURL")
    public String serviceURL;
}
