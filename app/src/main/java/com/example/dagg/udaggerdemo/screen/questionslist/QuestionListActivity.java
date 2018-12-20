package com.example.dagg.udaggerdemo.screen.questionslist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.dagg.udaggerdemo.fragment.ServerErrorDialogFragment;
import com.example.dagg.udaggerdemo.injections.Application;
import com.example.dagg.udaggerdemo.model.Question;
import com.example.dagg.udaggerdemo.network.QuestionsListResponseSchema;
import com.example.dagg.udaggerdemo.questions.FetchQuesitionList;
import com.example.dagg.udaggerdemo.screen.common.activity.BaseActivity;
import com.example.dagg.udaggerdemo.screen.common.dialogue.DialogManager;
import com.example.dagg.udaggerdemo.screen.questionsdetils.QuestionDetailsActivity;
import com.example.dagg.udaggerdemo.screen.questionsdetils.QuestionDetailsViewMvc;

import java.util.List;

import retrofit2.Call;

public class QuestionListActivity extends BaseActivity
    implements QuestionsListViewMvc.Listener, FetchQuesitionList.Listener {

    private Call<QuestionsListResponseSchema> mCall;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 10;
    private final int QUESTION_COUNT_TO_FETCH = 20;

    private FetchQuesitionList mFetchQuestionList;
    private QuestionsListViewMvc mViewMvc;
    private DialogManager mDialogueManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void askForPermission() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            int permissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                initiateNetworkRequest();
            }
        } else {
            initiateNetworkRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        initiateNetworkRequest();
    }

    private void init() {
        mViewMvc = getPresentationCompositionRoot().getViewMvcFactory().newInstance(QuestionsListViewMvc.class,
            null, getPresentationCompositionRoot().getImageLoader());
        mFetchQuestionList = getCompositionRoot().getQuestionList();
        mDialogueManager = getPresentationCompositionRoot().getDialogueManager();
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionList.registerListener(this);
        askForPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionList.unregisterListener(this);
    }

    private void initiateNetworkRequest() {
        mFetchQuestionList.fetchQuestionListAndNotify(QUESTION_COUNT_TO_FETCH);
    }

    @Override
    public void onQuestionClicked(Question question) {
        Intent intent = new Intent(getApplicationContext(), QuestionDetailsActivity.class);
        intent.putExtra(QuestionDetailsActivity.EXTRA_QUESTION_ID, question.getId());
        startActivity(intent);
    }

    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchOfQuestionsFailed() {
        mDialogueManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
