package com.example.dagg.udaggerdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dagg.udaggerdemo.R;
import com.example.dagg.udaggerdemo.fragment.ServerErrorDialogFragment;
import com.example.dagg.udaggerdemo.model.Question;
import com.example.dagg.udaggerdemo.network.QuestionsListResponseSchema;
import com.example.dagg.udaggerdemo.network.StackoverflowApi;
import com.example.dagg.udaggerdemo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionListActivity extends AppCompatActivity
    implements Callback<QuestionsListResponseSchema> {

    private RecyclerView mRecyclerView;
    private QuestionAdapter mQuestionsAdapter;
    private StackoverflowApi mStackoverflowApi;
    private Call<QuestionsListResponseSchema> mCall;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question_list);
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
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mQuestionsAdapter = new QuestionAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                Intent intent = new Intent(getApplicationContext(), QuestionDetailsActivity.class);
                intent.putExtra(QuestionDetailsActivity.EXTRA_QUESTION_ID, question.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mQuestionsAdapter);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        mStackoverflowApi = retrofit.create(StackoverflowApi.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //permission
        askForPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
            mQuestionsAdapter.bindData(response.body().getQuestions());
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

    public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

        private List<Question> mQuestionList = new ArrayList<>();
        private OnQuestionClickListener mOnQuestionClickListener;

        QuestionAdapter(OnQuestionClickListener onQuestionClickListener) {
            mOnQuestionClickListener = onQuestionClickListener;
        }

        public void bindData(List<Question> li) {
            mQuestionList.clear();
            mQuestionList.addAll(li);
            notifyDataSetChanged();
        }

        public class QuestionViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitle;

            public QuestionViewHolder(View view) {
                super(view);
                mTitle = view.findViewById(R.id.txt_title);
            }
        }

        @Override
        public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new QuestionViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_question_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(QuestionViewHolder holder, final int position) {
            holder.mTitle.setText(mQuestionList.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnQuestionClickListener.onQuestionClicked(mQuestionList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mQuestionList.size();
        }
    }

    public interface OnQuestionClickListener {
        void onQuestionClicked(Question question);
    }
}
