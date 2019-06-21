package com.softopian.cha8;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity implements Runnable {

    static ArrayList<HashMap<String, String>> MessageList;

    static Button button;
    static TextView messageBox;
    static ListView lstv;
    static ListAdapter adapter;

    public static String registerURL = "http://localhost/com.softopian.cha8/register.php";
    public static String loginURL = "http://localhost/com.softopian.cha8/login.php";
    public static String sendURL = "http://localhost/com.softopian.cha8/send.php";
    public static String fetchURL = "http://localhost/com.softopian.cha8/fetch.php";

    static String last_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ChatActivity.messageBox = (TextView) findViewById(R.id.messageBox);
        ChatActivity.button = (Button) findViewById(R.id.button);

        ChatActivity.MessageList = new ArrayList<>();
        ChatActivity.lstv = findViewById(R.id.chatView);
        ChatActivity.adapter = new SimpleAdapter(this, ChatActivity.MessageList,
                R.layout.chat_message,
                new String[]{ "sender", "message"},
                new int[]{R.id.chatSender, R.id.chatMessage});
        ChatActivity.lstv.setAdapter(adapter);

        ChatActivity.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        ChatActivity.lstv.post(new Runnable() {
            @Override
            public void run() {
                ChatActivity.lstv.setSelection(ChatActivity.adapter.getCount() - 1);
            }
        });

        Thread checker = new Thread(new ChatActivity());
        checker.setName("checker");
        checker.start();

    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();

        System.out.println("Thread Started: " + current.getName());

        if(current.getName().equals("checker")){
            // Check For Incoming Message
            while(true) {
                checkMessage();
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(){

        String message = ChatActivity.messageBox.getText().toString();

        String MyURL = Uri.parse(ChatActivity.sendURL)
                .buildUpon()
                .appendQueryParameter("user", LoginActivity.username)
                .appendQueryParameter("pass", LoginActivity.password)
                .appendQueryParameter("message", message)
                .build().toString();

        HTTPClient httpClient = new HTTPClient();
        String response = httpClient.doInBackground(MyURL);

        Log.d("Send_Message: ", response);

        if(response.equals("FAILED") ||  response.equals("UNDEFINED")){
            Toast.makeText(getApplicationContext(), "Something Wrong!", Toast.LENGTH_LONG).show();
            return;
        }

        if(response.equals("SUCCESS")){
            ChatActivity.messageBox.setText("");
        }

    }

    public void checkMessage(){

        String MyURL = Uri.parse(ChatActivity.fetchURL)
                .buildUpon()
                .appendQueryParameter("user", LoginActivity.username)
                .appendQueryParameter("pass", LoginActivity.password)
                .appendQueryParameter("last", ChatActivity.last_id)
                .build().toString();

        Log.d("Last_ID_Q: ", ChatActivity.last_id);

        HTTPClient httpClient = new HTTPClient();
        String response = httpClient.doInBackground(MyURL);

        Log.d("Check_Message: ", response);

        if(response.equals("FAILED") ||  response.equals("UNDEFINED") || response.equals("NULL")){
            return;
        }

        JSONObject reader;
        try {
            reader = new JSONObject(response);
            JSONArray mails = reader.getJSONArray("data");
            JSONObject last = reader.getJSONObject("last");

            Log.d("Last_ID: ", last.getString("id"));

            for (int i = 0; i < mails.length(); i++) {
                JSONObject c = mails.getJSONObject(i);

                final HashMap<String, String> chatHash = new HashMap<>();

                chatHash.put("sender", c.getString("sender"));
                chatHash.put("message", c.getString("message"));

                ChatActivity.last_id = last.getString("id");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChatActivity.MessageList.add(chatHash);
                        ((BaseAdapter)ChatActivity.lstv.getAdapter()).notifyDataSetChanged();
                        ChatActivity.lstv.invalidateViews();
                    }
                });

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
