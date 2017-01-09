package com.example.administrator.icdsalesrep.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.administrator.icdsalesrep.ICdSalesRepApplication;
import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.adapter.OrderListAdapter;
import com.example.administrator.icdsalesrep.network.RestClient;
import com.example.administrator.icdsalesrep.network.model.GenericRequestData;
import com.example.administrator.icdsalesrep.network.model.GenericResponseData;
import com.example.administrator.icdsalesrep.network.model.OrderList;
import com.example.administrator.icdsalesrep.network.model.OrderListRequestDetailData;
import com.example.administrator.icdsalesrep.network.model.OrderListResponseDetailData;
import com.example.administrator.icdsalesrep.util.Utility;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ICdSalesRepMainActivity extends AppCompatActivity {

    private String orderStatus ="Opening";
    private String orderType ="All";

    @InjectView(R.id.list)
    ListView orderList;

    @InjectView(R.id.all_button)
    Button all_button;

    @InjectView(R.id.sale_button)
    Button sale_button;

    @InjectView(R.id.ref_button)
    Button ref_button;

    @InjectView(R.id.search_button)
    ImageView search_button;

    @InjectView(R.id.opening_button)
    ImageView opening_button;

    @InjectView(R.id.closing_button)
    ImageView closing_button;

    @InjectView(R.id.prelims_button)
    ImageView prelims_button;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        openingButtonOnClick();
        showButtonSelection(0);
    }

    @OnClick(R.id.all_button)
    public void allButtonOnClick(){
        orderType = "All";
        showButtonSelection(0);
        initOrderListData(prepareOrder());
    }

    private void showButtonSelection(int selection) {
        switch(selection){
            case 0: all_button.setBackgroundColor(getResources().getColor(R.color.tab_bg_color));
                    all_button.setTextColor(getResources().getColor(R.color.white));
                sale_button.setBackgroundColor(getResources().getColor(R.color.grey));
                sale_button.setTextColor(getResources().getColor(R.color.black));
                ref_button.setBackgroundColor(getResources().getColor(R.color.grey));
                ref_button.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:  all_button.setBackgroundColor(getResources().getColor(R.color.grey));
                all_button.setTextColor(getResources().getColor(R.color.black));
                sale_button.setBackgroundColor(getResources().getColor(R.color.tab_bg_color));
                sale_button.setTextColor(getResources().getColor(R.color.white));
                ref_button.setBackgroundColor(getResources().getColor(R.color.grey));
                ref_button.setTextColor(getResources().getColor(R.color.black));
                    break;
            case 2:  all_button.setBackgroundColor(getResources().getColor(R.color.grey));
                all_button.setTextColor(getResources().getColor(R.color.black));
                sale_button.setBackgroundColor(getResources().getColor(R.color.grey));
                sale_button.setTextColor(getResources().getColor(R.color.black));
                ref_button.setBackgroundColor(getResources().getColor(R.color.tab_bg_color));
                ref_button.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    @OnClick(R.id.sale_button)
    public void saleButtonOnClick(){
        showButtonSelection(1);
        orderType = "Sale";
        initOrderListData(prepareOrder());
    }

    @OnClick(R.id.ref_button)
    public void refButtonOnClick(){
        //clearAdapter();
        showButtonSelection(2);
        orderType = "Refi";
        initOrderListData(prepareOrder());
    }

    @OnClick(R.id.opening_button)
    public void openingButtonOnClick(){
        showSelection(0);
        orderStatus = "Opening";
        initOrderListData(prepareOrder());
    }

    private void showSelection(int selectedIndex) {
        switch(selectedIndex){
            case 0: opening_button.setImageResource(R.drawable.opening_selected);
                    closing_button.setImageResource(R.drawable.closings);
                    search_button.setImageResource(R.drawable.search);
                    prelims_button.setImageResource(R.drawable.prelims);
                    break;
            case 1: opening_button.setImageResource(R.drawable.openings);
                    closing_button.setImageResource(R.drawable.closing_selected);
                    search_button.setImageResource(R.drawable.search);
                    prelims_button.setImageResource(R.drawable.prelims);
                    break;
            case 2: opening_button.setImageResource(R.drawable.openings);
                    closing_button.setImageResource(R.drawable.closings);
                    search_button.setImageResource(R.drawable.search);
                    prelims_button.setImageResource(R.drawable.prelims_selected);
                    break;
            case 3: opening_button.setImageResource(R.drawable.openings);
                    closing_button.setImageResource(R.drawable.closings);
                    search_button.setImageResource(R.drawable.search_selected);
                    prelims_button.setImageResource(R.drawable.prelims);
                    break;

        }

        showButtonSelection(0);
    }

    @OnClick(R.id.closing_button)
    public void closingButtonOnClick(){
        showSelection(1);
        orderStatus = "Closing";
        initOrderListData(prepareOrder());
    }

    @OnClick(R.id.search_button)
    public void searchButtonOnClick(){
        showSelection(3);
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i, 1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @OnClick(R.id.prelims_button)
    public void prelimButtonOnClick(){
        showSelection(2);
        orderStatus = "Prelim";
        initOrderListData(prepareOrder());
    }

    @OnClick(R.id.logout)
    public void logout(){
        new MaterialDialog.Builder(this)
                .title(R.string.you_sure_logout)
                .theme(Theme.LIGHT)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(ICdSalesRepMainActivity.this, SignInActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    private OrderListRequestDetailData prepareOrder(){
        OrderListRequestDetailData orderRequest = new OrderListRequestDetailData();
        return orderRequest;
    }

    private void initOrderListData(OrderListRequestDetailData orderRequest) {

        orderList.setAdapter(null);

        orderRequest.orderStatus = orderStatus;
        orderRequest.orderType=orderType;
        orderRequest.loginSessionKey=Utility.getInstance().getLoginToken();

        final Dialog dialog=new Dialog(this,android.R.style.Theme);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.show();

        Gson gson = new Gson();
        String jsonData = gson.toJson(orderRequest);

        //Encrypt string
        String encryptedData ="";
        try {

            encryptedData = Utility.getInstance().encrypt(jsonData);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        GenericRequestData orderRequestData = new GenericRequestData();
        orderRequestData.dataCollection = encryptedData;
        orderRequestData.tokenKey =Utility.getInstance().getTokenKey();

        RestClient rC = new RestClient(true);
        rC.getRestApi().getOrderList(orderRequestData, new Callback<GenericResponseData>() {
            @Override
            public void success(GenericResponseData orderListResponseData, Response response) {
                if(orderListResponseData.responseCode.equalsIgnoreCase("200")){
                    //progressBar.setVisibility(View.INVISIBLE);
                    String decryptMessage ="";

                    try {
                        decryptMessage =     Utility.getInstance().decrypt(orderListResponseData.dataCollection);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    Gson gson = new Gson();
                    final OrderListResponseDetailData orderListData = gson.fromJson(decryptMessage.toString(), OrderListResponseDetailData.class);

                    orderList.setAdapter(new OrderListAdapter(ICdSalesRepMainActivity.this, (ArrayList<OrderList>) orderListData.orderList,orderStatus));
                    orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            view.setSelected(true);
                            Intent intent = new Intent(ICdSalesRepMainActivity.this, OrderDetailActivity.class);
                            ((ICdSalesRepApplication)getApplication()).selectedOrder(orderListData.orderList.get(position));
                            startActivity(intent);
                        }
                    });
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    showErrorMessage(orderListResponseData.message,orderListResponseData.responseCode);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Utility.getInstance().showErrorMessage(getResources().getString(R.string.no_network),ICdSalesRepMainActivity.this);


            }
        });
    }

    private void showErrorMessage(String message,final String respCode) {
        new MaterialDialog.Builder(this)
                .title(R.string.error)
                .content(message)
                .positiveText(R.string.ok)
                .theme(Theme.LIGHT)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(respCode.equalsIgnoreCase("510")) {//session expire{
                            startActivity(new Intent(ICdSalesRepMainActivity.this, SignInActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }

                    }
                })
                .show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                initOrderListData(((ICdSalesRepApplication)getApplication()).getSearchOrderRequest());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void initNavigationDrawer() {

        ImageView menuRight = (ImageView) findViewById(R.id.menuRight);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
