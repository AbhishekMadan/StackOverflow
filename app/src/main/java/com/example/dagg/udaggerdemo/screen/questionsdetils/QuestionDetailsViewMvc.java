package com.example.dagg.udaggerdemo.screen.questionsdetils;

import com.example.dagg.udaggerdemo.model.QuestionWithBody;
import com.example.dagg.udaggerdemo.screen.common.mvcview.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestion(QuestionWithBody question);
}
