package com.example.administrator.icdsalesrep.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.administrator.icdsalesrep.R;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by nitin on 23/08/16.
 */
public class Utility {

    private final  String deviceId = "DEVICEID";
    private final  String TOKEN = "tokenkey";
    private final  String ISENCRYPTREQUIRED= "IsEncryptionRequired";
    private final  String AESKEY = "TokenAESKey";
    private final  String IVKey = "TokenIVKey";
    private final  String COMPANYCODE = "CompanyCode";
    private final  String SERVICEURL = "serviceurl";
    private final  String LOGINTOKEN = "logintoken";
    private final  String REMEMBER_ME ="rememberMe";
    private final  String USER_NAME ="uname";

    private  SharedPreferences pref;
    private  Editor editor;

    private static Utility instance = null;

    protected Utility() {
        // Exists only to defeat instantiation.
    }
    public static Utility getInstance() {
        if(instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    public  String decrypt(String base64EncodedString) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {

        byte[] KEY = Utility.getInstance().getAESKey().getBytes();
        byte[] ivx = Utility.getInstance().getIVKeyKey().getBytes();

        /*base64EncodedString = "QyuZRuBSZhI4gXyX0szq67RlcAGAM2eNBOh9h0hzkCzuaoHu3XxUEU1wliVeIts/\n" +
                "FUsYZoU757noxFCXV1vjabYrKPxlXN1ghkGBLgN9DoswgLm+NI3SS2K+lRrNu7Ly\n" +
                "4trJzRm4pWNMRub7YecCOA==";*/
        SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivx);

        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);

        byte[] raw = Base64.decode(base64EncodedString, Base64.DEFAULT);

        byte[] originalBytes = ecipher.doFinal(raw);

        String original = new String(originalBytes, "UTF8");

        Log.d("Decrypted message ",original);

        return original;

    }

    public  String encrypt(String strToEncrypt) throws InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,NoSuchAlgorithmException
            ,BadPaddingException, UnsupportedEncodingException ,NoSuchPaddingException{

        Log.d("Encryption Before ",strToEncrypt);
        byte[] KEY = Utility.getInstance().getAESKey().getBytes();//"908E9CFC88DE40F0AE2E79ECA3A52B5C".getBytes();
        byte[] ivx = Utility.getInstance().getIVKeyKey().getBytes();//"F52DB51FA77B4BC3".getBytes();

        SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivx);

        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

        byte[] encryptedBytes = ecipher.doFinal(strToEncrypt.getBytes());

        byte[] byteToEncrypt = Base64.encode(encryptedBytes, Base64.DEFAULT);

        String original = new String(byteToEncrypt, "UTF8");

        return original;

    }


    public  void createSharedPrefernce(Application application){
        pref = application.getSharedPreferences("MyPreference", application.MODE_PRIVATE);
        editor = pref.edit();
    }



    public  void saveDeviceId(String advId){
        editor.putString(deviceId, advId);
        editor.commit();
    }

    public  String getDeviceId(){
        return "a4571cd6-fe93-4ad9-a0ac-19bfbe25c1be";
        //return pref.getString(deviceId, "");
    }


    public  void saveTokenKey(String token){
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public  String getTokenKey(){
        return pref.getString(TOKEN, "");
    }


    public  void saveEncryption(boolean flag){
        editor.putBoolean(ISENCRYPTREQUIRED, flag);
        editor.commit();
    }

    public  boolean getEncryption(){
        return pref.getBoolean(ISENCRYPTREQUIRED, false);
    }

    public  void saveAESKey(String aeskey){
        editor.putString(AESKEY, aeskey);
        editor.commit();
    }

    public  String getAESKey(){
        return pref.getString(AESKEY, "");
    }

    public  void saveIVKeyKey(String ivkey){
        editor.putString(IVKey, ivkey);
        editor.commit();
    }

    public  String getIVKeyKey(){
        return pref.getString(IVKey, "");
    }

    public  void saveCompanyCode(String companycode){
        editor.putString(COMPANYCODE, companycode);
        editor.commit();
    }

    public  String getCompanyCode(){
        return pref.getString(COMPANYCODE, "");
    }

    public  void saveLoginToken(String logintoekn){
        editor.putString(LOGINTOKEN, logintoekn);
        editor.commit();
    }

    public  String getLoginToken(){
        return pref.getString(LOGINTOKEN, "");
    }

    public  void saveServiceUrl(String serviceUrl){
        editor.putString(SERVICEURL, serviceUrl);
        editor.commit();
    }

    public  String getServiceUrl(){
        return pref.getString(SERVICEURL, "");
    }

    public void rememberMe(boolean checked) {
        editor.putBoolean(REMEMBER_ME, checked);
        editor.commit();
    }

    public boolean isRememberMe() {
        return pref.getBoolean(REMEMBER_ME, false);
    }

    public  void saveUserName(String userName){
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public  String getUserName(){
        return pref.getString(USER_NAME, "");
    }


    public void showErrorMessage(String message,Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.error)
                .content(message)
                .positiveText(R.string.ok)
                .theme(Theme.LIGHT)
                .show();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
