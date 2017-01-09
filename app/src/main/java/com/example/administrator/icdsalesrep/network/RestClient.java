package com.example.administrator.icdsalesrep.network;

import com.example.administrator.icdsalesrep.network.api.RestApi;
import com.example.administrator.icdsalesrep.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by nitin on 22/08/16.
 */
public class RestClient {

        private  String BASE_URL = "http://dev.icdcloud.com/icdcloudSalesRepAppService/icdCloudSalesRepService.svc";

        private RestApi restApi;

        public RestClient(boolean connectToServiceUrl)
        {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            RequestInterceptor requestInterceptor = new RequestInterceptor()
            {
                @Override
                public void intercept(RequestFacade request) {
                    request.addQueryParam("Content-Type", "text/html");
                }
            };

            if(connectToServiceUrl)
                BASE_URL = Utility.getInstance().getServiceUrl();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(gson))
                    .build();

            restApi = restAdapter.create(RestApi.class);
        }

        public RestApi getRestApi() {
            return restApi;
        }


    }
