package com.example.android.nitklogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.android.nitklogin.R.id.webv;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPref = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

        final String username = sharedPref.getString("username", "");
        final String password = sharedPref.getString("password", "");

        if(username == "" || password == "")
        {
            Toast.makeText(this, "Enter Login Details", Toast.LENGTH_LONG).show();
            Intent main = new Intent(this, MainActivity.class);
            finish();
            startActivity(main);
        }

        else {
            // Put the LOGIN code here

            String url = "http://10.10.54.4:8090/";

            WebView webv = (WebView) findViewById(R.id.webv);
            webv.getSettings().setDomStorageEnabled(true);
            webv.loadUrl(url);
            webv.getSettings().setJavaScriptEnabled(true);

            webv.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageFinished(WebView webv, String url){

                super.onPageFinished(webv, url);

                String js = "javascript: document.getElementsByName('username')[0].value='" + username + "';" +
                        "var two = document.getElementsByName('password');" +
                        "two[0].value = '" + password + "';" +
                        "var three = document.getElementById('logincaption').click();";

                if(Build.VERSION.SDK_INT >= 19){

                webv.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });}
                else{
                    webv.loadUrl(js);
                }

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        }

    }



    public void loginPage(View view)
    {
        Intent main = new Intent(this, MainActivity.class);
        finish();
        startActivity(main);
    }

}
