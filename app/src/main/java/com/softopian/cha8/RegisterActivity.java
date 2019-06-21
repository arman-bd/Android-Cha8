package com.softopian.cha8;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void TryRegister(View v)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView tv_fullname = findViewById(R.id.regFullname);
        TextView tv_username = findViewById(R.id.regUsername);
        TextView tv_password = findViewById(R.id.regPassword);
        TextView tv_password2 = findViewById(R.id.regPassword2);

        String regFullname = tv_fullname.getText().toString();
        String regUsername = tv_username.getText().toString();
        String regPassword = tv_password.getText().toString();
        String regPassword2 = tv_password2.getText().toString();

        if(regFullname.equals("") || regFullname.length() > 32 || regFullname.length() < 3)
        {
            Toast.makeText(getApplicationContext(), "Enter Full Name Correctly.", Toast.LENGTH_LONG).show();
            tv_fullname.requestFocus();
            return;
        }

        if(regUsername.equals("") || regUsername.length() > 32 || regUsername.length() < 3)
        {
            Toast.makeText(getApplicationContext(), "Enter UserName Correctly.", Toast.LENGTH_LONG).show();
            tv_username.requestFocus();
            return;
        }

        if(regPassword.equals("") || regPassword.length() > 32 || regPassword.length() < 3)
        {
            Toast.makeText(getApplicationContext(), "Enter Password Correctly.", Toast.LENGTH_LONG).show();
            tv_password.requestFocus();
            return;
        }

        if(!regPassword.equals(regPassword2))
        {
            Toast.makeText(getApplicationContext(), "Password Doesn't Match!", Toast.LENGTH_LONG).show();
            tv_password2.requestFocus();
            return;
        }


        String MyURL = Uri.parse(ChatActivity.registerURL)
                .buildUpon()
                .appendQueryParameter("user", regUsername)
                .appendQueryParameter("pass", regPassword)
                .appendQueryParameter("fullname", regFullname)
                .build().toString();

        HTTPClient reg = new HTTPClient();
        String response = reg.doInBackground(MyURL);

        Log.d("Network: ", response);

        switch(response)
        {
            case "UNDEFINED":
                Toast.makeText(getApplicationContext(), "Unable To Register...", Toast.LENGTH_LONG).show();
                break;
            case "SUCCESS":
                Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case "FAILED":
                Toast.makeText(getApplicationContext(), "Failed To Register...", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void gotoLogin(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

