package com.example.dagg.udaggerdemo.screen.questionsdetils;

import android.os.Bundle;

import com.example.dagg.udaggerdemo.fragment.ServerErrorDialogFragment;
import com.example.dagg.udaggerdemo.model.QuestionDetails;
import com.example.dagg.udaggerdemo.network.SingleQuestionResponseSchema;
import com.example.dagg.udaggerdemo.questions.FetchQuestionDetails;
import com.example.dagg.udaggerdemo.screen.common.ImageLoader;
import com.example.dagg.udaggerdemo.screen.common.activity.BaseActivity;
import com.example.dagg.udaggerdemo.screen.common.dialogue.DialogManager;
import com.example.dagg.udaggerdemo.screen.common.mvcview.ViewMvcFactoy;

import javax.inject.Inject;

import retrofit2.Call;

public class QuestionDetailsActivity extends BaseActivity
implements QuestionDetailsViewMvc.Listener, FetchQuestionDetails.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    private Call<SingleQuestionResponseSchema> mCall;
    private String mQuestionId;

    private QuestionDetailsViewMvc mViewMvc;
    @Inject FetchQuestionDetails mFetchQuestionDetails;
    @Inject DialogManager mDialogueManager;
    @Inject ViewMvcFactoy mViewMvcFactory;
    @Inject ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

    }

    public void init() {
        getPresentationComponent().inject(this);
        mViewMvc = mViewMvcFactory.newInstance(QuestionDetailsViewMvc.class, null, mImageLoader);
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionDetails.registerListener(this);
        mFetchQuestionDetails.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionDetails questionDetails) {
        mViewMvc.bindQuestion(questionDetails);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogueManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
