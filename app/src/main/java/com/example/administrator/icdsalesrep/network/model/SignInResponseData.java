package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 23/08/16.
 */
public class SignInResponseData {



    @SerializedName("DataCollection")//{"LoginToken":"5b54ff16-13fe-4284-a5c3-f70609a1f31e","ServiceURLEncryptionRequired":"true","ServiceURL":"http://dev.icdCloud.com/icdSalesRepAppService/ICDSalesRepAppService.svc"}

    public String dataCollection;
    @SerializedName("Message")
    public String message;
    @SerializedName("ResponseCode")
    public String responseCode;
    @SerializedName("ServiceName")
    public String serviceName;

}
