package com.example.dagg.udaggerdemo.screen.common.mvcview;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dagg.udaggerdemo.screen.common.ImageLoader;
import com.example.dagg.udaggerdemo.screen.questionsdetils.QuestionDetailsViewMvc;
import com.example.dagg.udaggerdemo.screen.questionsdetils.QuestionDetailsViewMvcImpl;
import com.example.dagg.udaggerdemo.screen.questionslist.QuestionsListViewMvcImpl;

public class ViewMvcFactoy {

    private LayoutInflater mLayoutInflater;

    public ViewMvcFactoy(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public <T extends ViewMvc> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container,
        ImageLoader imageLoader) {
        ViewMvc viewMvc;

        if (mvcViewClass == QuestionDetailsViewMvc.class) {
            viewMvc = new QuestionDetailsViewMvcImpl(mLayoutInflater, container, imageLoader);
        } else {
            viewMvc = new QuestionsListViewMvcImpl(mLayoutInflater, container);
        }
        return (T) viewMvc;
    }
}
