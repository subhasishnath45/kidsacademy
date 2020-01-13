package com.example.kidsacademy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView web;
    //    private static ProgressBar progressbar;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);


        web = (WebView) findViewById(R.id.webv);
        //progressbar = (ProgressBar) findViewById(R.id.progressBar);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(MainActivity.this, "", "Loading...");
        //web.setWebViewClient(new AppWebViewClients(progressbar));
        web.setWebViewClient(new WebViewClient(){

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }

        });

        web.loadUrl("http://kidsacademy.co.in/");

        WebSettings websettings = web.getSettings();
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        websettings.setJavaScriptEnabled(true);

        websettings.setAllowContentAccess(true);
        websettings.setAppCacheEnabled(true);
        websettings.setDomStorageEnabled(true);
        websettings.setUseWideViewPort(true);


    }

    @Override
    public void onBackPressed() {

        if (web.canGoBack()){
            web.goBack();
        }else{
//            super.onBackPressed();
            new AlertDialog.Builder(this).setMessage("Are you sure, you want to exit ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            }).setNegativeButton("No",null).show();
        }
    }
}
