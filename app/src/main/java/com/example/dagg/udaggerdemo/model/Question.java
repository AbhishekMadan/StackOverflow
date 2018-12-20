package com.example.dagg.udaggerdemo.model;

import com.google.gson.annotations.SerializedName;

public class Question {
    private final String mId;

    private final String mTitle;

    public Question(String id, String title) {
        mId = id;
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }
}
