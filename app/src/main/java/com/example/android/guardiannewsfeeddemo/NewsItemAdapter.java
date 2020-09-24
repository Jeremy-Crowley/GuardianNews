package com.example.android.guardiannewsfeeddemo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsItemAdapter extends ArrayAdapter {
    public NewsItemAdapter(Context context, ArrayList<NewsItem> NewsItem) {
        super(context, 0, NewsItem);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.newsviews, parent, false);
        }
        NewsItem currentNews =(NewsItem) getItem(position);
        TextView titleTextView= listItemView.findViewById(R.id.titleFront);
        assert currentNews != null;
        titleTextView.setText(currentNews.getTitle());

        TextView authorTextView= listItemView.findViewById(R.id.authorFront);
        authorTextView.setText(currentNews.getAuthor());

        TextView dateTextView= listItemView.findViewById(R.id.dateFront);
        dateTextView.setText(currentNews.getDate());

        return listItemView;
    }
}