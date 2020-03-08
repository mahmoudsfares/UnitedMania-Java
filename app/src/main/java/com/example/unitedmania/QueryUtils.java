package com.example.unitedmania;

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
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.unitedmania.NewsActivity.LOG_TAG;

public final class QueryUtils {

    //Return News List that contains the news data after parsing the JSONResponse
    public static ArrayList<News> extractNews(String Url) throws JSONException {

        //Create an empty ArrayList to hold the news data
        ArrayList<News> news = new ArrayList<>();

        //Try to parse the JSONResponse after extracting it from the URL
        try {
            JSONObject root = new JSONObject(makeHttpRequest(createUrl(Url)));
            JSONArray articles = root.getJSONArray("articles");
            for(int i=0;i<articles.length();i++){

                JSONObject currentObj = articles.getJSONObject(i);

                //Get the news source
                JSONObject sourceObj = currentObj.getJSONObject("source");
                String source = sourceObj.getString("name");

                //Get the news title
                String title = currentObj.getString("title");

                //Get the news details
                String details = currentObj.getString("content");

                //Get the news source url
                String url = currentObj.getString("url");

                // Get the news corresponding image
                String imageUrl = currentObj.getString("urlToImage");


                news.add(new News(source,title,details,url,imageUrl));
            }

        } catch (JSONException e) {
            //If an error is thrown when executing any of the above statements in the "try" block,
            //catch the exception here, so the app doesn't crash. Print a log message
            //with the message from the exception.
            Log.e(LOG_TAG, "Problem with parsing the news JSON results", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the list of earthquakes
        return news;
    }



    //Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    //Make an HTTP request to the given URL and return a String as the response.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //If the URL is null, return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request was successful (response code 200),
            //then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
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

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
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
}
