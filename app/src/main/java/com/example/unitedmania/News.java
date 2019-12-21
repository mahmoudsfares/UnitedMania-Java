package com.example.unitedmania;

import android.net.Uri;

import java.net.URI;
import java.net.URL;

public class News {
    private String mSource;
    private String mTitle;
    private String mDetails;
    private String mUrl;

    public News (String source, String title, String details, String url){
        mSource = source;
        mTitle = title;
        mDetails = details;
        mUrl = url;
    }

    public String getSource() {
        return mSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getUrl() {
        return mUrl;
    }

}
