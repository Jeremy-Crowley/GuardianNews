package com.example.android.guardiannewsfeeddemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Adapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String GUARDIAN_REQUEST_URL = "/https://content.guardianapis.com/search?api-key=255dafbf-d5d8-4420-8d76-fec56b5a3b37";
    private static final String GUARDIAN_KEY = "/https://content.guardianapis.com/search?api-key=255dafbf-d5d8-4420-8d76-fec56b5a3b37";
    private static final String GET = "GET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  NewsAsyncTask task = new NewsAsyncTask();
       // task.execute();
    }

    private void updateUi(NewsItem NewsList) {

        ArrayList<NewsItem> News = new ArrayList<>();
        News.add(new NewsItem("Are Gorillas coming for our beans!?!?!?!? Find out now!", "TerfSupreme", "12/32/3088"));

        NewsItemAdapter Adapter = new NewsItemAdapter(this, News);
        ListView listView = findViewById(R.id.frontListView);
        listView.setEmptyView(findViewById(R.id.emptyView));
        listView.setAdapter(Adapter);
    }


   }