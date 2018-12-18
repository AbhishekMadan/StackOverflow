package com.example.dagg.udaggerdemo.injections;

import android.support.annotation.UiThread;

import com.example.dagg.udaggerdemo.network.StackoverflowApi;
import com.example.dagg.udaggerdemo.questions.FetchQuesitionList;
import com.example.dagg.udaggerdemo.questions.FetchQuestionDetails;
import com.example.dagg.udaggerdemo.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit mRetrofit;
    private StackoverflowApi mStackoverflowApi;

    @UiThread
    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return mRetrofit;
    }

    private StackoverflowApi getStackOverflowApi() {
        if (mStackoverflowApi == null) {
            mStackoverflowApi = getRetrofit().create(StackoverflowApi.class);
        }
        return mStackoverflowApi;
    }

    @UiThread
    public FetchQuesitionList getQuestionList() {
        return new FetchQuesitionList(getStackOverflowApi());
    }

    @UiThread
    public FetchQuestionDetails getQuestionDetails() {
        return new FetchQuestionDetails(getStackOverflowApi());
    }
}
