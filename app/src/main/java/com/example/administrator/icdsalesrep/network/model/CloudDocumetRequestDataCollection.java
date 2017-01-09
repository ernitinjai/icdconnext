package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 24/08/16.
 */
public class CloudDocumetRequestDataCollection {

    //{"LoginSessionKey":"28f0e1df-4b3d-4a24-be6b-1eeae5732e19","idOrd":"619243"}

    @SerializedName("LoginSessionKey")
    public String loginSessionKey;
    public String idOrd;

}
