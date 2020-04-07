package com.example.unitedmania;

public class News {
    // news source
    private String mSource;
    // news title
    private String mTitle;
    // news details
    private String mDetails;
    // news source url
    private String mUrl;
    // news corresponding image
    private String mImageUrl;

    public News (String source, String title, String details, String url, String imageUrl){
        mSource = source;
        mTitle = title;
        mDetails = details;
        mUrl = url;
        mImageUrl = imageUrl;
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

    public String getImageUrl() {
        return mImageUrl;
    }
}
