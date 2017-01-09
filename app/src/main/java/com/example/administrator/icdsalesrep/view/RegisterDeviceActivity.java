package com.example.administrator.icdsalesrep.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.RestClient;
import com.example.administrator.icdsalesrep.network.model.RegisterDeviceRequestData;
import com.example.administrator.icdsalesrep.network.model.RegisterDeviceResponseData;
import com.example.administrator.icdsalesrep.util.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by nitin on 22/08/16.
 */

public class RegisterDeviceActivity extends AppCompatActivity {



    @InjectView(R.id.register_device)
    EditText registerDeviceEditText;

    @InjectView(R.id.ProgressCircle)
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_device_activity);
        ButterKnife.inject(this);

        //registerDeviceEditText.setText("Dev9");
    }


    @OnClick(R.id.register_button)
    public void registerDeviceButtonOnClick(){
        initDeviceRegistrationConnection();
    }


    private void initDeviceRegistrationConnection() {

        final String companyCode = registerDeviceEditText.getText().toString();
        if(TextUtils.isEmpty(companyCode)){
            Utility.getInstance().showErrorMessage(getString(R.string.compnay_code_req),this);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        RegisterDeviceRequestData registerDeviceData = new RegisterDeviceRequestData();
        registerDeviceData.setCompanyCode(companyCode);
        registerDeviceData.setDeviceID(Utility.getInstance().getDeviceId());


        RestClient rC = new RestClient(false);
        rC.getRestApi().registerDevice(registerDeviceData,new Callback<RegisterDeviceResponseData>() {
            @Override
            public void success(RegisterDeviceResponseData registrationDeviceResponse, Response response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(registrationDeviceResponse.ResponseCode.equalsIgnoreCase("200")){
                    Utility.getInstance().saveCompanyCode(companyCode);
                    startActivity(new Intent(RegisterDeviceActivity.this, SignInActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();

                }else{
                    Utility.getInstance().showErrorMessage(registrationDeviceResponse.Message,RegisterDeviceActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Utility.getInstance().showErrorMessage(getResources().getString(R.string.no_network),RegisterDeviceActivity.this);

            }
        });
    }






}

