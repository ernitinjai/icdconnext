package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 23/08/16.
 */
public class OrderListRequestDetailData {

    //{"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"All",
    //        "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N",
    //       "CustomerFirstName":"","ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Opening",
    //       "LoginSessionKey":"5b54ff16-13fe-4284-a5c3-f70609a1f31e","City":"","OpenDateFrom":""}


    /*{"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"Sale"
            ,"FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N",
            "CustomerFirstName":"","ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Opening",
            "LoginSessionKey":"5b54ff16-13fe-4284-a5c3-f70609a1f31e","City":"","OpenDateFrom":""}*/

   /* {"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"Refi",
            "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N",
            "CustomerFirstName":"","ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Opening",
            "LoginSessionKey":"5b54ff16-13fe-4284-a5c3-f70609a1f31e","City":"","OpenDateFrom":""}*/

    /*{"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"All",
            "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N",
            "CustomerFirstName":"","ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Prelim",
            "LoginSessionKey":"5b54ff16-13fe-4284-a5c3-f70609a1f31e","City":"","OpenDateFrom":""}*/


    @SerializedName("ClosedDateFrom")
    public String closedDateFrom ="";
    @SerializedName("StreetName")
    public String streetName ="";
    @SerializedName("OpenDateTo")
    public String openDateTo="";
    @SerializedName("PreLimDateTo")
    public String preLimDateTo="";
    @SerializedName("StreetNumber")
    public String streetNumber="";
    @SerializedName("OrderType")
    public String orderType="";
    @SerializedName("FileNumber")
    public String fileNumber="";
    @SerializedName("CustomerName")
    public String customerName="";
    @SerializedName("CustomerLastName")
    public String customerLastName="";
    @SerializedName("CustomerCity")
    public String customerCity="";
    @SerializedName("AllRep")
    public String allRep="";
    @SerializedName("CustomerFirstName")
    public String customerFirstName="";
    @SerializedName("ClosedDateTo")
    public String closedDateTo="";
    @SerializedName("PreLimDateFrom")
    public String preLimDateFrom="";
    @SerializedName("OrderStatus")
    public String orderStatus="";
    @SerializedName("LoginSessionKey")
    public String loginSessionKey="";
    @SerializedName("City")
    public String city="";
    @SerializedName("OpenDateFrom")
    public String openDateFrom="";
}
