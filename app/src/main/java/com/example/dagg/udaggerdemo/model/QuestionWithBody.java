package com.example.dagg.udaggerdemo.model;

import com.google.gson.annotations.SerializedName;

public class QuestionWithBody extends Question{

    @SerializedName("body")
    private final String mBody;

    public QuestionWithBody(String title, String id, String body) {
        super(title, id);
        mBody = body;
    }

    public String getBody() {
        return mBody;
    }
}