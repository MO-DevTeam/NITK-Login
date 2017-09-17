package com.example.android.nitklogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public WebView webv ;
    private boolean pageLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPref = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

        final String username = sharedPref.getString("username", "");
        final String password = sharedPref.getString("password", "");

        // got to sign in if not saved
        if(username.equals("") || password.equals(""))
        {
            Toast.makeText(this, "Enter Login Details", Toast.LENGTH_LONG).show();
//            Intent main = new Intent(this, MainActivity.class);
//            finish();
//            startActivity(main);
            loginPage();
        }

        else {

            // load web view
            String url = "http://10.10.54.4:8090/";

            webv = (WebView) findViewById(R.id.webv);
            webv.getSettings().setDomStorageEnabled(true);
            webv.loadUrl(url);
            webv.getSettings().setJavaScriptEnabled(true);

            webv.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView webv, String url){

                super.onPageFinished(webv, url);


                // perform auto login using js
                String js = "javascript: document.getElementsByName('username')[0].value='" + username + "';" +
                        "var two = document.getElementsByName('password');" +
                        "two[0].value = '" + password + "';" +
                        "var three = document.getElementById('logincaption').click();";

                if(Build.VERSION.SDK_INT >= 19){

                webv.evaluateJavascript(js, new ValueCallback<String>() {

                    @Override
                    public void onReceiveValue(String s) {
                        pageLogin = true;
                    }
                });}
                else{
                    webv.loadUrl(js);
                    pageLogin = true;

                }

            }

            // handle ssh error
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        }

    }

    // go to login page
    public void loginPage()
    {
        Intent main = new Intent(this, MainActivity.class);
        finish();
        startActivity(main);
    }

    public void logout(){

        if(pageLogin){
            String js = "javascript: var three = document.getElementById('logincaption').click();";

            if(Build.VERSION.SDK_INT >= 19){

                webv.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });}
            else{
                webv.loadUrl(js);
                pageLogin = false;
            }
        }


    }

    // inflate options in menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    // handle item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection


        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.cdetails:
                logout();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loginPage();
                    }
                }, 2000);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
