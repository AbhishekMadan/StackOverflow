package com.example.dagg.udaggerdemo.screen.questionsdetils;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.dagg.udaggerdemo.fragment.ServerErrorDialogFragment;
import com.example.dagg.udaggerdemo.injections.Application;
import com.example.dagg.udaggerdemo.model.QuestionWithBody;
import com.example.dagg.udaggerdemo.network.SingleQuestionResponseSchema;
import com.example.dagg.udaggerdemo.questions.FetchQuestionDetails;
import com.example.dagg.udaggerdemo.screen.common.activity.BaseActivity;
import com.example.dagg.udaggerdemo.screen.common.dialogue.DialogManager;

import retrofit2.Call;

public class QuestionDetailsActivity extends BaseActivity
implements QuestionDetailsViewMvc.Listener, FetchQuestionDetails.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    private Call<SingleQuestionResponseSchema> mCall;
    private String mQuestionId;

    private QuestionDetailsViewMvc mViewMvc;
    private FetchQuestionDetails mFetchQuestionDetails;
    private DialogManager mDialogueManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

    }

    public void init() {
        mViewMvc = new QuestionDetailsViewMvcImpl(LayoutInflater.from(this), null);
        mFetchQuestionDetails = getCompositionRoot().getQuestionDetails();
        mDialogueManager = getPresentationCompositionRoot().getDialogueManager();
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
    public void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question) {
        mViewMvc.bindQuestion(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogueManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
