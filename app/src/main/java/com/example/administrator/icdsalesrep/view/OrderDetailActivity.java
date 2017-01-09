package com.example.administrator.icdsalesrep.view;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.administrator.icdsalesrep.ICdSalesRepApplication;
import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.adapter.ExpandableOrderListAdapter;
import com.example.administrator.icdsalesrep.network.RestClient;
import com.example.administrator.icdsalesrep.network.model.CloudDocumentRequest;
import com.example.administrator.icdsalesrep.network.model.CloudDocumentResponse;
import com.example.administrator.icdsalesrep.network.model.CloudDocumetRequestDataCollection;
import com.example.administrator.icdsalesrep.network.model.Documents;
import com.example.administrator.icdsalesrep.network.model.GenericRequestData;
import com.example.administrator.icdsalesrep.network.model.GenericResponseData;
import com.example.administrator.icdsalesrep.network.model.OrderList;
import com.example.administrator.icdsalesrep.network.model.PrelimRequestData;
import com.example.administrator.icdsalesrep.util.Utility;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by nitin on 24/08/16.
 */
public class OrderDetailActivity extends AppCompatActivity {

    @InjectView(R.id.detail_lvExp)
    ExpandableListView lvExp;

    @InjectView(R.id.address)
    TextView address;

    @InjectView(R.id.detail_loan_amt)
    TextView detail_loan_amt;

    @InjectView(R.id.detail_sale_price)
    TextView detail_sale_price;

    @InjectView(R.id.detail_file_type)
    TextView detail_file_type;

    @InjectView(R.id.detail_date_opn)
    TextView detail_date_opn;

    @InjectView(R.id.detail_date_closed)
    TextView detail_date_closed;


    @InjectView(R.id.file_no_header)
    TextView file_no_header;

    @InjectView(R.id.prelims)
    ImageView prelimsIcon;

    ExpandableOrderListAdapter listAdapter;

    List<String> listDataHeader;
    private OrderList orderList;
    public Documents documents;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        ButterKnife.inject(this);
        orderList = ((ICdSalesRepApplication) getApplication()).getSelectedOrder();
        populateDetailData();
        initExpandableListAdapter();
        initGetDocumentListConnection();
    }

    private void populateDetailData() {
        address.setText(orderList.propertyAddress);
        file_no_header.setText(orderList.fileNo);
        detail_loan_amt.setText(orderList.getLoanAmount());
        detail_sale_price.setText(orderList.getsaleAmount());
        detail_file_type.setText(orderList.fileType);
        detail_date_opn.setText(orderList.dateOpened);
        detail_date_closed.setText(orderList.dateClosed);
        if(orderList.isPreLimReady.equalsIgnoreCase("Y"))
            prelimsIcon.setVisibility(View.VISIBLE);
        else
            prelimsIcon.setVisibility(View.INVISIBLE);
    }


    private void initExpandableListAdapter() {

        setExpandedListIndicator();

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableOrderListAdapter(this, listDataHeader, orderList);

        // setting list adapter
        lvExp.setAdapter(listAdapter);

    }

    private void setExpandedListIndicator() {
        lvExp.setGroupIndicator(null);
        lvExp.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        lvExp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View clickedView, int groupPosition, long rowId) {
                ImageView groupIndicator = (ImageView) clickedView.findViewById(R.id.help_group_indicator);
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    groupIndicator.setImageResource(R.drawable.down_blue);
                } else {
                    parent.expandGroup(groupPosition);
                    groupIndicator.setImageResource(R.drawable.up_blue);
                }
                return true;
            }
        });
        lvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(final int groupPosition) {
                if (groupPosition == 2) {
                    lvExp.post(new Runnable() {

                        @Override
                        public void run() {
                            lvExp.setSelection(groupPosition);
                            if (lvExp.getChildAt(groupPosition) != null)
                                lvExp.requestChildRectangleOnScreen(lvExp.getChildAt(groupPosition),
                                        new Rect(0, 0, lvExp.getChildAt(groupPosition).getRight(), lvExp.getChildAt(groupPosition).getHeight()), false);
                        }
                    });
                }
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataHeader.add(getString(R.string.contacts));
        listDataHeader.add(getString(R.string.icd_services));
        listDataHeader.add(getString(R.string.icd_cloud_doc));
    }

    private void initGetDocumentListConnection() {

        final Dialog dialog=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.show();

        CloudDocumetRequestDataCollection documentRequest = new CloudDocumetRequestDataCollection();
        documentRequest.loginSessionKey = Utility.getInstance().getLoginToken();
        documentRequest.idOrd = orderList.idOrd;
        Gson gson = new Gson();
        String jsonData = gson.toJson(documentRequest);

        //Encrypt string
        String encryptedData = "";
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

        CloudDocumentRequest documentRequestData = new CloudDocumentRequest();
        documentRequestData.dataCollection = encryptedData;
        documentRequestData.tokenKey = Utility.getInstance().getTokenKey();

        RestClient rC = new RestClient(true);
        rC.getRestApi().getCloudDocument(documentRequestData, new Callback<CloudDocumentResponse>() {
            @Override
            public void success(CloudDocumentResponse documentListResponseData, Response response) {
                dialog.dismiss();

                if (documentListResponseData.responseCode.equalsIgnoreCase("200")) {
                    //progressBar.setVisibility(View.INVISIBLE);
                    String decryptMessage = "";

                    try {
                        decryptMessage = Utility.getInstance().decrypt(documentListResponseData.dataCollection);
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
                    documents = gson.fromJson(decryptMessage.toString(), Documents.class);

                } else {
                    //showErrorMessage(documentListResponseData.message);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Utility.getInstance().showErrorMessage(getResources().getString(R.string.no_network),OrderDetailActivity.this);

            }
        });
    }

    public void requestPrlims() {
        new MaterialDialog.Builder(this)
                .title(R.string.please_enter_email)
                .customView(R.layout.prelim_req_dialog, false)
                .theme(Theme.LIGHT)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText editText = (EditText) dialog.getCustomView().findViewById(R.id.prelim_req_edittext);
                        String email = editText.getText()!=null?editText.getText().toString():"";
                        initPrelimRequest(email);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    private void initPrelimRequest(String email){
        PrelimRequestData prelimReqData = new PrelimRequestData();
        prelimReqData.email =email;
        prelimReqData.idOrd = orderList.idOrd;
        prelimReqData.loginSessionKey = Utility.getInstance().getLoginToken();
        Gson gson = new Gson();
        String jsonData = gson.toJson(prelimReqData);

        //Encrypt string
        String encryptedData = "";
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

        GenericRequestData genericRequest = new GenericRequestData();
        genericRequest.dataCollection = encryptedData;
        genericRequest.tokenKey = Utility.getInstance().getTokenKey();

        RestClient rC = new RestClient(true);
        rC.getRestApi().requestPrelim(genericRequest, new Callback<GenericResponseData>() {
            @Override
            public void success(GenericResponseData genericResponseData, Response response) {

                    new MaterialDialog.Builder(OrderDetailActivity.this)
                            .title(genericResponseData.message)
                            .positiveText(R.string.ok)
                            .theme(Theme.LIGHT)
                            .show();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
