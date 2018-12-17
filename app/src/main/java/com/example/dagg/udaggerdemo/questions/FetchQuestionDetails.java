package com.example.dagg.udaggerdemo.questions;

import android.support.annotation.Nullable;

import com.example.dagg.udaggerdemo.model.QuestionWithBody;
import com.example.dagg.udaggerdemo.network.SingleQuestionResponseSchema;
import com.example.dagg.udaggerdemo.network.StackoverflowApi;
import com.example.dagg.udaggerdemo.screen.common.BaseObservable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetails extends BaseObservable<FetchQuestionDetails.Listener> {

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();
    }

    private final StackoverflowApi mStackoverflowApi;

    @Nullable
    Call<SingleQuestionResponseSchema> mCall;

    public FetchQuestionDetails(StackoverflowApi stackoverflowApi) {
        mStackoverflowApi = stackoverflowApi;
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {

        cancelCurrentFetchIfActive();

        mCall = mStackoverflowApi.questionDetails(questionId);
        mCall.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getQuestion());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(QuestionWithBody question) {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsFailed();
        }
    }
}
