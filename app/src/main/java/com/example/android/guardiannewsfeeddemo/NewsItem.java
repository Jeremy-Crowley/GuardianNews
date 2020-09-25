package com.example.android.guardiannewsfeeddemo;

//A custom class to store information about a news article.

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import java.util.ArrayList;

public class NewsItem  {

    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String  mWebAddress;

    public NewsItem(String Title, String Author, String Date, String webAddress){
       mTitle = Title;
       mAuthor = Author;
       mDate = Date;
       mWebAddress = webAddress;
    }
    public String getTitle() {return mTitle;}

    public String getAuthor() {return mAuthor;}

    public String getDate() {return mDate;}

    public String getURL() {return mWebAddress;}
    }


