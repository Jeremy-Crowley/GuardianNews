package com.example.android.guardiannewsfeeddemo;

//A custom class to store information about a news article.

public class NewsItem {

    private String mTitle;
    private String mAuthor;
    private String mDate;

    public NewsItem(String Title, String Author, String Date){
       mTitle = Title;
       mAuthor = Author;
       mDate = Date;
    }
    public String getTitle() {return mTitle;}

    public String getAuthor() {return mAuthor;}

    public String getDate() {return mDate;}
    }

