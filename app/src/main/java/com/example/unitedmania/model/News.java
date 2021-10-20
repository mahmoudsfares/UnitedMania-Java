package com.example.unitedmania.model;

import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("source")
    final private NewsSource mSource;
    @SerializedName("title")
    final private String mTitle;
    @SerializedName("content")
    final private String mDetails;
    @SerializedName("url")
    final private String mUrl;
    @SerializedName("urlToImage")
    final private String mImageUrl;

    public News (NewsSource source, String title, String details, String url, String imageUrl){
        mSource = source;
        mTitle = title;
        mDetails = details;
        mUrl = url;
        mImageUrl = imageUrl;
    }

    public NewsSource getSource() {
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

    public String getImageUrl() {
        return mImageUrl;
    }
}
