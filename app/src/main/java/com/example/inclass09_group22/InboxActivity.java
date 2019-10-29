package com.example.inclass09_group22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InboxActivity extends AppCompatActivity {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzIzMDQ4NTgsImV4cCI6MTYwMzkyNzI1OCwianRpIjoiMzdkN2M2RTFLbWVRdk1wM0R5WDFVVSIsInVzZXIiOjY2fQ._PUvkyPJw5VWY2fuItT9EkdRZQANDzfm3ZJMA16BXbc";
    ListView listView;
    ArrayList<InboxData> MailList = new ArrayList<>();
    ArrayList<String> MailIDs =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        listView = findViewById(R.id.listview);
        getMails();
    }

    public void getMails() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox").addHeader("Authorization", "BEARER " + token)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject root = new JSONObject(response.body().string());
                    JSONArray message = root.getJSONArray("messages");
                    for (int i = 0; i < message.length(); i++) {
                        JSONObject each = message.getJSONObject(i);
                        InboxData mail = new InboxData();
                        mail.created_at = each.getString("created_at");
                        mail.id = each.getString("id");
                        mail.message = each.getString("message");
                        mail.receiver_id = each.getString("receiver_id");
                        mail.sender_fname = each.getString("sender_fname");
                        mail.sender_lname = each.getString("sender_lname");
                        mail.sender_id = each.getString("sender_id");
                        mail.subject = each.getString("subject");
                        mail.updated_at = each.getString("updated_at");
                        MailList.add(mail);
                        MailIDs.add(mail.id);
                    }
                    Log.d("test", "onResponse: " + MailList.toString());
                    IndoxListViewAdapter adapter = new IndoxListViewAdapter(InboxActivity.this, R.layout.inbox_listview, MailList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(), "Opening Mail ", Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clickMe(View view) {
        ImageButton bt = (ImageButton) view;
        final InboxData data = (InboxData) bt.getTag();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/delete/" + data.id).addHeader("Authorization", "BEARER " + token)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("test", "Mail Deleted: " + data.subject);
                int index = MailIDs.indexOf(data.id);
                MailIDs.remove(index);
                MailList.remove(index);
            }
        });
        IndoxListViewAdapter adapter = new IndoxListViewAdapter(InboxActivity.this, R.layout.inbox_listview, MailList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Opening Mail ", Toast.LENGTH_LONG).show();
            }
        });
    }

//    private class GetMails extends AsyncTask<String, Void, ArrayList<InboxData>> {
//        @Override
//        protected ArrayList<InboxData> doInBackground(String... strings) {
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<InboxData> inboxData) {
//            super.onPostExecute(inboxData);
//        }
//    }
}
