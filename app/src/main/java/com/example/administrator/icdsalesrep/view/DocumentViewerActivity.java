package com.example.administrator.icdsalesrep.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.util.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nitin on 22/08/16.
 */

public class DocumentViewerActivity extends AppCompatActivity {


    @InjectView(R.id.webview)
    WebView webview;

    @InjectView(R.id.imageview)
    ImageView imgview;

    @InjectView(R.id.email_button)
    ImageView emailButton;

    String strFolderName = "iCDSalesApp";
    String docName;

    //String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/"+strFolderName+"/";

    Dialog dialog;

    final int SENT_MAIL = 1;

    //Boolean isSDPresent;
    File folder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_viwer_activity);
        ButterKnife.inject(this);
        Bundle bundle = getIntent().getExtras();
        String pdfUrl = bundle.getString("PDFURL");
        docName = bundle.getString("docName");
        //docName = docName.replace(' ',' ');

        if(pdfUrl!= null)
        {
            //TODO here get the string stored in the string variable and do
            // setText() on userName
            initPdfView(pdfUrl);
            new DownloadFile().execute(pdfUrl);
        }

        //isSDPresent = false;// android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

    }

    private void initPdfView(String url) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setWebViewClient(new Callback());
        if(docName.contains("pdf"))
            webview.loadUrl( "http://docs.google.com/gview?embedded=true&url="+url);
        else {
            //webview.loadUrl(url);
            /*webview.setVisibility(View.GONE);
            imgview.setVisibility(View.VISIBLE);
            Picasso.with(this).load(url).into(imgview);*/
            dialog=new Dialog(this,android.R.style.Theme);
            dialog.setContentView(R.layout.progress_dialog);
            dialog.show();
        }
    }


    @OnClick(R.id.email_button)
    public void emailButtonOnClick(){

        /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        File file;
        if(isSDPresent)
            file = new File(PATH,docName);
        else
            file = new File(getCacheDir(),docName);

        if (!file.exists() || !file.canRead()) {
            Toast.makeText(this, "Attachment Error", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Uri uri = Uri.parse("file://" + file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, pdfUrl);


        try {
            startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."),SENT_MAIL);

        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }*/

        startActivityForResult(Utils.getSendEmailIntent(
                this,
                "", "",
                "See attached", docName),SENT_MAIL);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }



    class DownloadFile extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL((String) aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                /*File folder;
                if(isSDPresent)
                    folder = new File(PATH,docName);
                else
                    folder = new File(getCacheDir(),docName);

                if(!folder.exists()){

                    folder.getParentFile().mkdir();//If there is no folder it will be created.
                }*/


                folder = new File(getCacheDir(), docName);

                if(!folder.exists()){
                    try {
                        folder.createNewFile();//If there is no folder it will be created.
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(folder);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {

                    output.write(data, 0, count);
                }

                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return "s";
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equalsIgnoreCase("s") && docName.contains("pdf"))
                emailButton.setVisibility(View.VISIBLE);
            else {
                if(dialog !=null)
                    dialog.dismiss();

                emailButtonOnClick();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}

