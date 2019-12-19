package com.example.unitedmania;

import android.content.Context;
import org.json.JSONException;
import android.content.AsyncTaskLoader;
import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if (mUrl == null) return null;

        ArrayList<News> news = new ArrayList<>();
        try {
            news = QueryUtils.extractNews(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }
}
