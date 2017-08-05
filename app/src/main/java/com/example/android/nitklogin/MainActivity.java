package com.example.android.nitklogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.start;
import static com.example.android.nitklogin.R.id.passwordInp;
import static com.example.android.nitklogin.R.id.usernameInp;

public class MainActivity extends AppCompatActivity {

    EditText usernameInp;
    EditText passwordInp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInp = (EditText) findViewById(R.id.usernameInp);
        passwordInp = (EditText) findViewById(R.id.passwordInp);

    }



    public void loginButton(View view)
    {
        SharedPreferences sharedPref = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("username", usernameInp.getText().toString());
        editor.putString("password", passwordInp.getText().toString());
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);

    }


}
