package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 24/08/16.
 */
public class CloudDocumentRequest {
   // {"LoginSessionKey":"28f0e1df-4b3d-4a24-be6b-1eeae5732e19","idOrd":"619243"}
  /* {
       "DataCollection": "25KY2R67Nx9KmFjetCZzL79S5NnyLWI\/gL9xHzht6VRbT1RluikL7hw2ox5ggEwX\r\nKz9vCF\/PpnjGi5X12mbyHKjMNJVb2FHS\/VJl04YNZC8=",
           "TokenKey": "ef4d3476-dc77-42ea-8440-3bfd1418dec1"
   }*/

    @SerializedName("DataCollection")
    public String dataCollection;
    @SerializedName("TokenKey")
    public String tokenKey;
}
