package com.example.administrator.icdsalesrep;

import android.app.Application;

import com.example.administrator.icdsalesrep.network.model.OrderList;
import com.example.administrator.icdsalesrep.network.model.OrderListRequestDetailData;
import com.example.administrator.icdsalesrep.util.Utility;

/**
 * Created by nitin on 23/08/16.
 */

/*@ReportsCrashes(
        formKey = "", // This is required for backward compatibility but not used
        sharedPreferencesName = "MyPref",
        sharedPreferencesMode = Context.MODE_PRIVATE,
        mailTo = "ernitinjai@gmail.com"
)*/
public class ICdSalesRepApplication extends Application {

    private OrderList orderList;
    private OrderListRequestDetailData orderRequest;

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        // The following line triggers the initialization of ACRA
        //ACRA.init(this);
        Utility.getInstance().createSharedPrefernce(this);

    }

    public void selectedOrder(OrderList orderList) {
        this.orderList = orderList;
    }

    public OrderList getSelectedOrder() {
        return orderList;
    }

    public OrderListRequestDetailData getSearchOrderRequest() {
        return orderRequest;
    }

    public void setSearchOrderRequest(OrderListRequestDetailData orderRequest) {
        this.orderRequest = orderRequest;
    }


}
