package com.example.administrator.icdsalesrep.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.RestClient;
import com.example.administrator.icdsalesrep.network.model.GenerateDeviceTokenRequestData;
import com.example.administrator.icdsalesrep.network.model.GenerateDeviceTokenResponsData;
import com.example.administrator.icdsalesrep.network.model.SignInRequestData;
import com.example.administrator.icdsalesrep.network.model.SignInRequestDetailData;
import com.example.administrator.icdsalesrep.network.model.SignInResponseData;
import com.example.administrator.icdsalesrep.network.model.SignInResponseDetailData;
import com.example.administrator.icdsalesrep.util.Utility;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by nitin on 22/08/16.
 */

public class SignInActivity extends AppCompatActivity {



    @InjectView(R.id.username)
    EditText userNameEditText;

    @InjectView(R.id.password)
    EditText passwordEditText;

    @InjectView(R.id.ProgressCircle)
    ProgressBar progressBar;

    @InjectView(R.id.remeber_checkbox)
    CheckBox rememberCheckbox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        ButterKnife.inject(this);
        //userNameEditText.setText("testto");
        //passwordEditText.setText("P@ssword123");
        if(Utility.getInstance().isRememberMe()){
            userNameEditText.setText(Utility.getInstance().getUserName());
        }

        if(Utility.getInstance().isRememberMe())
            rememberCheckbox.setChecked(true);

    }


    @OnClick(R.id.signin_button)
    public void signInButtonOnClick(){
        initGetDeviceTokenConnection();
    }


    private void initConnection() {


        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
            Utility.getInstance().showErrorMessage(getString(R.string.uname_pname_req),this);
            progressBar.setVisibility(View.INVISIBLE);
            return;
        } else if(TextUtils.isEmpty(username) ){
            Utility.getInstance().showErrorMessage(getString(R.string.uname_req),this);
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }else if(TextUtils.isEmpty(password)){
            Utility.getInstance().showErrorMessage(getString(R.string.pname_req),this);
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if(rememberCheckbox.isChecked())
            Utility.getInstance().saveUserName(username);

        SignInRequestDetailData signInData = new SignInRequestDetailData();
        signInData.setUserName(username);
        signInData.setPassword(password);
        signInData.setCompanyCode(Utility.getInstance().getCompanyCode());

        Gson gson = new Gson();
        String jsonData = gson.toJson(signInData);

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

        SignInRequestData signInRequestData = new SignInRequestData();
        signInRequestData.dataCollection = encryptedData;
        signInRequestData.deviceID = Utility.getInstance().getDeviceId();
        signInRequestData.tokenKey =Utility.getInstance().getTokenKey();

        RestClient rC = new RestClient(false);
        rC.getRestApi().signIn(signInRequestData,new Callback<SignInResponseData>() {
            @Override
            public void success(SignInResponseData signInResponseData, Response response) {

                progressBar.setVisibility(View.INVISIBLE);

                if(signInResponseData.responseCode.equalsIgnoreCase("200")){
                    String decryptMessage ="";

                    try {
                        decryptMessage =     Utility.getInstance().decrypt(signInResponseData.dataCollection);
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
                    SignInResponseDetailData signInDetail = gson.fromJson(decryptMessage.toString(), SignInResponseDetailData.class);

                    Utility.getInstance().saveLoginToken(signInDetail.loginToken);
                    Utility.getInstance().saveServiceUrl(signInDetail.serviceURL);
                    Utility.getInstance().rememberMe(rememberCheckbox.isChecked());


                    startActivity(new Intent(SignInActivity.this, ICdSalesRepMainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }else{
                    Utility.getInstance().showErrorMessage(signInResponseData.message,SignInActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Utility.getInstance().showErrorMessage(getResources().getString(R.string.no_network),SignInActivity.this);


            }
        });
    }

    private void initGetDeviceTokenConnection() {
        progressBar.setVisibility(View.VISIBLE);
        GenerateDeviceTokenRequestData deviceTokenRequest = new GenerateDeviceTokenRequestData();
        deviceTokenRequest.setDeviceID(Utility.getInstance().getDeviceId());
        RestClient rC = new RestClient(false);
        rC.getRestApi().generateDeviceToken(deviceTokenRequest, new Callback<GenerateDeviceTokenResponsData>() {
            @Override
            public void success(GenerateDeviceTokenResponsData deviceTokenResponseData, Response response) {
                if(deviceTokenResponseData.generateDeviceTokenResult.responseCode.equalsIgnoreCase("200")){

                    Utility.getInstance().saveTokenKey(deviceTokenResponseData.generateDeviceTokenResult.dataCollection.tokenKey);
                    // Utility.getInstance().saveEncryption(deviceTokenResponseData.generateDeviceTokenResult.dataCollection.isEncryptionRequired);
                    Utility.getInstance().saveAESKey(deviceTokenResponseData.generateDeviceTokenResult.dataCollection.tokenAESKey);
                    Utility.getInstance().saveIVKeyKey(deviceTokenResponseData.generateDeviceTokenResult.dataCollection.tokenIVKey);
                    initConnection();
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Utility.getInstance().showErrorMessage(deviceTokenResponseData.generateDeviceTokenResult.message,SignInActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Utility.getInstance().showErrorMessage(getResources().getString(R.string.no_network),SignInActivity.this);


            }
        });
    }



}

