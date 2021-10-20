package com.example.unitedmania.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("articles")
    private final List<News> articles;

    public NewsResponse(List<News> articles) {
        this.articles = articles;
    }

    public List<News> getArticles() {
        return articles;
    }
}
