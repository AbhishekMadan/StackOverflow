package com.example.dagg.udaggerdemo.network;

import com.example.dagg.udaggerdemo.model.QuestionWithBody;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class SingleQuestionResponseSchema {

    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;

    public SingleQuestionResponseSchema(QuestionWithBody question) {
        mQuestions = Collections.singletonList(question);
    }

    public QuestionWithBody getQuestion() {
        return mQuestions.get(0);
    }
}
