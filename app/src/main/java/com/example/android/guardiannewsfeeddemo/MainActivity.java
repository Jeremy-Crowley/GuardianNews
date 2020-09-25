package com.example.android.guardiannewsfeeddemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {
    public static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=Careers&show-tags=contributor&api-key=255dafbf-d5d8-4420-8d76-fec56b5a3b37";

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }

    private void updateUi(NewsItem News) {

        ArrayList<NewsItem> NewsList = new ArrayList<>();
      //NewsList.add(new NewsItem("Are Gorillas coming for our beans!?!?!?!? Find out now!", "TerfSupreme", "12/32/3088"));
       NewsList.add(News);


        NewsItemAdapter Adapter = new NewsItemAdapter(this, NewsList);
        ListView listView = findViewById(R.id.frontListView);
        listView.setEmptyView(findViewById(R.id.emptyView));
        listView.setAdapter(Adapter);


    }

   private class NewsAsyncTask extends AsyncTask<URL, Void, NewsItem> {


        protected NewsItem doInBackground(URL... urls) {
            URL url = makeUrl(GUARDIAN_REQUEST_URL);
            String jsonRes = "";
            try {
                jsonRes = buildHttpRequest(url);
            } catch (IOException e) {
                // TODO: 9/23/2020 handle IOException
            }
            NewsItem News = extractResultsFromJson(jsonRes);
            return News;
        }

        @Override
        protected void onPostExecute(NewsItem News) {
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


        private NewsItem extractResultsFromJson(String NewsJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(NewsJSON);
                JSONObject response = baseJsonResponse.getJSONObject("response");
                JSONArray resultsArray = response.getJSONArray("results");

                if (resultsArray.length() > 0) {
                    JSONObject firstResult = resultsArray.getJSONObject(0);
                    //JSONObject properties = firstResult.getJSONObject("properties");


                    String Title = firstResult.getString("webTitle");
                    String Author = firstResult.getString("webTitle");
                    String Date = firstResult.getString("webPublicationDate");

                    NewsItem test = new NewsItem(Title, Author, Date);



                    return new NewsItem(Title, Author, Date);
                }
            } catch (JSONException e) {
                Log.e("Error", "Problem parsing JSON data");
            }
            return null;
        }
    }

}
