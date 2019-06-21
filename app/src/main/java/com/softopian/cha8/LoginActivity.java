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

public class LoginActivity extends AppCompatActivity {

    static String username;
    static String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void TryLogin(View v)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView mail = findViewById(R.id.loginUsername);
        TextView pass = findViewById(R.id.loginPassword);

        String mMail = mail.getText().toString();
        String mPass = pass.getText().toString();

        String MyURL = Uri.parse(ChatActivity.loginURL)
                .buildUpon()
                .appendQueryParameter("user", mMail)
                .appendQueryParameter("pass", mPass)
                .build().toString();

        HTTPClient lgn = new HTTPClient();
        String response = lgn.doInBackground(MyURL);

        Log.d("Network: ", response);

        switch(response)
        {
            case "UNDEFINED":
                Toast.makeText(getApplicationContext(), "Unable To Login...", Toast.LENGTH_LONG).show();
                break;
            case "SUCCESS":
                Toast.makeText(getApplicationContext(), "Logged In...", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(this, MailActivity.class);
                Intent intent = new Intent(this, ChatActivity.class);
                username = mMail;
                password = mPass;
                startActivity(intent);
                break;
            case "FAILED":
                Toast.makeText(getApplicationContext(), "Failed To Login...", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void gotoRegister(View v)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
