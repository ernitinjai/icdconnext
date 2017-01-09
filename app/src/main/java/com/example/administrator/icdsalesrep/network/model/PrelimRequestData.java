package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 26/08/16.
 */
public class PrelimRequestData {

    //{"LoginSessionKey":"860a3a0d-6293-4ea4-adb1-21b85f281c80","idOrd":"619241","Email":"123@gmail.com"}

    @SerializedName("LoginSessionKey")
    public String loginSessionKey;
    public String idOrd;
    @SerializedName("Email")
    public String email;
}
