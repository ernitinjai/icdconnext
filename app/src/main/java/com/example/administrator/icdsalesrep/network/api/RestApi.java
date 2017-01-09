package com.example.administrator.icdsalesrep.network.api;

/**
 * Created by administrator on 22/08/16.
 */

import com.example.administrator.icdsalesrep.network.model.CloudDocumentRequest;
import com.example.administrator.icdsalesrep.network.model.CloudDocumentResponse;
import com.example.administrator.icdsalesrep.network.model.GenerateDeviceTokenRequestData;
import com.example.administrator.icdsalesrep.network.model.GenerateDeviceTokenResponsData;
import com.example.administrator.icdsalesrep.network.model.GenericRequestData;
import com.example.administrator.icdsalesrep.network.model.GenericResponseData;
import com.example.administrator.icdsalesrep.network.model.RegisterDeviceRequestData;
import com.example.administrator.icdsalesrep.network.model.RegisterDeviceResponseData;
import com.example.administrator.icdsalesrep.network.model.SignInRequestData;
import com.example.administrator.icdsalesrep.network.model.SignInResponseData;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by nitin on 22/08/16.
 */
public interface RestApi {



    @POST("/RegisterDevice")
    void registerDevice(@Body RegisterDeviceRequestData device, Callback<RegisterDeviceResponseData> cb);

    @POST("/GenerateDeviceToken")
    void generateDeviceToken(@Body GenerateDeviceTokenRequestData device, Callback<GenerateDeviceTokenResponsData> cb);

    @POST("/SignIn")
    void signIn(@Body SignInRequestData device, Callback<SignInResponseData> cb);

    @POST("/OrdericdCloudDocument")
    void getCloudDocument(@Body CloudDocumentRequest device, Callback<CloudDocumentResponse> cb);

    @POST("/OrderList")
    void getOrderList(@Body GenericRequestData orderRequest, Callback<GenericResponseData> cb);

    @POST("//OrderRequestPrelim")
    void requestPrelim(@Body GenericRequestData orderRequest, Callback<GenericResponseData> cb);



}

