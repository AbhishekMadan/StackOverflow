package com.example.dagg.udaggerdemo.questions;

import android.support.annotation.Nullable;

import com.example.dagg.udaggerdemo.model.Question;
import com.example.dagg.udaggerdemo.model.QuestionSchema;
import com.example.dagg.udaggerdemo.network.QuestionsListResponseSchema;
import com.example.dagg.udaggerdemo.network.StackoverflowApi;
import com.example.dagg.udaggerdemo.screen.common.BaseObservable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchQuesitionList extends BaseObservable<FetchQuesitionList.Listener> {

    private final StackoverflowApi mStackOverflowApi;

    @Nullable
    Call<QuestionsListResponseSchema> mCall;

    public interface Listener {
        void onFetchOfQuestionsSucceeded(List<Question> questions);
        void onFetchOfQuestionsFailed();
    }

    public FetchQuesitionList(StackoverflowApi stackoverflowApi) {
        mStackOverflowApi = stackoverflowApi;
    }

    public void fetchQuestionListAndNotify(int questionCount) {
        cancelCurrentRequest();
        mCall = mStackOverflowApi.lastActiveQuestions(20);
        mCall.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySuccessful(questionsFromQuestionsSchemas(response.body().getQuestions()));
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private List<Question> questionsFromQuestionsSchemas(List<QuestionSchema> questionSchemas) {
        List<Question> questions = new ArrayList<>(questionSchemas.size());
        for (QuestionSchema questionSchema : questionSchemas) {
            questions.add(new Question(questionSchema.getId(), questionSchema.getTitle()));
        }
        return questions;
    }

    private void cancelCurrentRequest() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySuccessful(List<Question> resp) {
        List<Question> unmodifiableQuestions = Collections.unmodifiableList(resp);
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionsSucceeded(unmodifiableQuestions);
        }
    }

    private void notifyFailed() {
        for (Listener listener: getListeners()) {
            listener.onFetchOfQuestionsFailed();
        }
    }

}
