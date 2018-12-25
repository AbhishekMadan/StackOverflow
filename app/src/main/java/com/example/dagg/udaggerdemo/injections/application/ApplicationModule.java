package com.example.dagg.udaggerdemo.injections.application;

import com.example.dagg.udaggerdemo.network.StackoverflowApi;
import com.example.dagg.udaggerdemo.questions.FetchQuesitionList;
import com.example.dagg.udaggerdemo.questions.FetchQuestionDetails;
import com.example.dagg.udaggerdemo.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Singleton
    StackoverflowApi getStackOverflowApi(Retrofit retrofit) {
        return retrofit.create(StackoverflowApi.class);
    }

    @Provides
    public FetchQuesitionList getQuestionList(StackoverflowApi stackoverflowApi) {
        return new FetchQuesitionList(stackoverflowApi);
    }

    @Provides
    public FetchQuestionDetails getQuestionDetails(StackoverflowApi stackoverflowApi) {
        return new FetchQuestionDetails(stackoverflowApi);
    }
}
