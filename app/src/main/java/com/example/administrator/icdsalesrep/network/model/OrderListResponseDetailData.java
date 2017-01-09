package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nitin on 24/08/16.
 */
public class OrderListResponseDetailData {


    @SerializedName("OrderList")
    public List<OrderList> orderList;

}
