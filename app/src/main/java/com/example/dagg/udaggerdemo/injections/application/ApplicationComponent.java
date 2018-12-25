package com.example.dagg.udaggerdemo.injections.application;

import com.example.dagg.udaggerdemo.questions.FetchQuesitionList;
import com.example.dagg.udaggerdemo.questions.FetchQuestionDetails;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    public FetchQuestionDetails getQuestionDetails();
    public FetchQuesitionList getQuestionList();
}
