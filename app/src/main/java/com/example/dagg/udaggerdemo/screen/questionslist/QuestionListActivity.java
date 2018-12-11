package com.example.dagg.udaggerdemo.screen.questionslist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.dagg.udaggerdemo.fragment.ServerErrorDialogFragment;
import com.example.dagg.udaggerdemo.model.Question;
import com.example.dagg.udaggerdemo.network.QuestionsListResponseSchema;
import com.example.dagg.udaggerdemo.network.StackoverflowApi;
import com.example.dagg.udaggerdemo.screen.questionsdetils.QuestionDetailsActivity;
import com.example.dagg.udaggerdemo.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionListActivity extends AppCompatActivity
    implements Callback<QuestionsListResponseSchema>, QuestionsListViewMvc.Listener {

    private StackoverflowApi mStackoverflowApi;
    private Call<QuestionsListResponseSchema> mCall;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 10;

    private QuestionsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = new QuestionsListViewMvcImpl(LayoutInflater.from(this), null);
        setContentView(mViewMvc.getRootView());

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
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        mStackoverflowApi = retrofit.create(StackoverflowApi.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);

        askForPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);

        if (mCall != null) {
            mCall.cancel();
        }
    }

    private void initiateNetworkRequest() {
        mCall = mStackoverflowApi.lastActiveQuestions(20);
        mCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        if (response.isSuccessful() && response.body() != null) {
            mViewMvc.bindQuestions(response.body().getQuestions());
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss();
    }

    @Override
    public void onQuestionClicked(Question question) {
        Intent intent = new Intent(getApplicationContext(), QuestionDetailsActivity.class);
        intent.putExtra(QuestionDetailsActivity.EXTRA_QUESTION_ID, question.getId());
        startActivity(intent);
    }
}
