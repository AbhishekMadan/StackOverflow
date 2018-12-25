package com.example.dagg.udaggerdemo.injections.presentation;

import com.example.dagg.udaggerdemo.screen.questionsdetils.QuestionDetailsActivity;
import com.example.dagg.udaggerdemo.screen.questionslist.QuestionListActivity;

import dagger.Component;

@Component(modules = PresentationModule.class)
public interface PresentationComponent {

    public void inject(QuestionListActivity questionListActivity);
    public void inject(QuestionDetailsActivity questionDetailsActivity);
}
