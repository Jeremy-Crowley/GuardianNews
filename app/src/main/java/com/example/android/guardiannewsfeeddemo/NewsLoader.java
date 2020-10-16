package com.example.android.guardiannewsfeeddemo;

import android.content.Context;

import android.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader  extends AsyncTaskLoader<ArrayList<NewsItem>> {
    private String mUrl;


    /**
     * @param context
     * @deprecated
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    public ArrayList<NewsItem> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<NewsItem> newsItem = MainActivity.getnews(mUrl);
        return (ArrayList<NewsItem>) newsItem;
    }
}

