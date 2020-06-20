package com.example.newsfeed.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Query {

    public static final String LOG_TAG = Query.class.getSimpleName();

    public Query() {

    }

    public static List<Model> fetchNews(Context context, String requestURL) {

        URL url = createURL(requestURL);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Model> news = extractFromJsonResponse(context, jsonResponse);
        return news;

    }



    private static URL createURL(String stringURL) {

        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "response code ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();


    }

    private static List<Model> extractFromJsonResponse(Context context, String bookJson) {

        if  (TextUtils.isEmpty(bookJson))  {
            return null;
        }

            List<Model> news = new ArrayList<>();

           try {
               JSONObject jsonResponseBase = new JSONObject(bookJson);
               JSONArray currentNewsArray = jsonResponseBase.getJSONArray("articles");

               for (int i = 0; i < currentNewsArray.length(); i++) {
                   JSONObject newsArray = currentNewsArray.getJSONObject(i);
                   JSONObject thesource = newsArray.getJSONObject("source");

                   String title = newsArray.getString("title");
                   String description = newsArray.getString("description");
                   String source = thesource.getString("name");
                   String url = newsArray.getString("url");
                   String image = newsArray.getString("urlToImage");
                   String time = newsArray.getString("publishedAt");

                   Model newss = new Model (url, image, title, source, description, time);
                   news.add(newss);

               }

           } catch (JSONException e) {
               e.printStackTrace();
           }


            return news;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getLanguage());
        return country.toLowerCase();
    }
}



