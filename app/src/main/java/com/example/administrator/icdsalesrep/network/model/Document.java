package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 24/08/16.
 */
public class Document {

    @SerializedName("DocumentName")
    public String documentName;
    @SerializedName("DocumentDownloadUrl")
    public String documentDownloadUrl;
}
