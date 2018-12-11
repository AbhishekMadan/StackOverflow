package com.example.dagg.udaggerdemo.screen.questionslist;

import com.example.dagg.udaggerdemo.model.Question;
import com.example.dagg.udaggerdemo.screen.common.mvcview.ObservableViewMvc;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> questions);
}
