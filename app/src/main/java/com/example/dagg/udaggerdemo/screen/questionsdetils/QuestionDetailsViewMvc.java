package com.example.dagg.udaggerdemo.screen.questionsdetils;

import com.example.dagg.udaggerdemo.model.QuestionDetails;
import com.example.dagg.udaggerdemo.screen.common.mvcview.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestion(QuestionDetails question);
}
