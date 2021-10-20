package com.example.unitedmania.model;

import com.google.gson.annotations.SerializedName;

public class NewsSource {

    @SerializedName("name")
    private final String name;

    public NewsSource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
