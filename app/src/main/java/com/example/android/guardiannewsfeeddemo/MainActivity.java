package com.example.android.guardiannewsfeeddemo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?show-tags=contributor&api-key=255dafbf-d5d8-4420-8d76-fec56b5a3b37";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }

    private void updateUi(ArrayList<NewsItem> News) {


        final NewsItemAdapter Adapter = new NewsItemAdapter(this, News);
        ListView listView = findViewById(R.id.frontListView);
        listView.setEmptyView(findViewById(R.id.emptyView));
        listView.setAdapter(Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                NewsItem currentNewsItem = Adapter.getItem(position);
                Uri newsUri = Uri.parse(currentNewsItem.getURL());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

    }

    private class NewsAsyncTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {

        protected ArrayList<NewsItem> doInBackground(URL... urls) {
            URL url = makeUrl(GUARDIAN_REQUEST_URL);
            String jsonRes = "";
            try {
                jsonRes = buildHttpRequest(url);
            } catch (IOException e) {
                // TODO: 9/23/2020 handle IOException
            }
           ArrayList<NewsItem> News = extractResultsFromJson(jsonRes);
            return News;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> News) {
            if (News == null) {
                return;
            }

            updateUi(News);
        }

        private URL makeUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }

        private String buildHttpRequest(URL url) throws IOException {
            String jsonRes = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonRes = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {

                    inputStream.close();
                }
            }
            return jsonRes;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


        private ArrayList<NewsItem> extractResultsFromJson(String NewsJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(NewsJSON);

                JSONObject response = baseJsonResponse.getJSONObject("response");
                JSONArray resultsArray = response.getJSONArray("results");

                ArrayList<NewsItem> NewsList = new ArrayList<>();

                if (resultsArray.length() > 0) {
                    for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject firstResult = resultsArray.getJSONObject(i);

                    JSONArray tagsArray = firstResult.getJSONArray("tags");

                    String Author = getString(R.string.blankAuthor);
                    if (tagsArray.length() > 0) {
                        JSONObject firstTag = tagsArray.getJSONObject(0);
                        Author = firstTag.getString("webTitle");
                    }

                    String Title = firstResult.getString("webTitle");
                    String webAddress = firstResult.getString("webUrl");

                    String date = firstResult
                                    .getString("webPublicationDate")
                                    .replace("T", " ")
                                    .replace("Z", "");
                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                    try {
                        Date parsedDate = parser.parse(date);
                        String formattedDate = formatter.format(parsedDate);

                        NewsItem News = new NewsItem(Title, Author, formattedDate, webAddress);
                        NewsList.add(News);
                    } catch (ParseException e) {
                        System.out.println(e);
                    }
                    }
                    return NewsList;
                }
            } catch (JSONException e) {
                Log.e("Error", "Problem parsing JSON data");
            }
            return null;
        }
    }

}
