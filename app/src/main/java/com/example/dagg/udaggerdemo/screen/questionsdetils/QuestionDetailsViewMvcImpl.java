package com.example.dagg.udaggerdemo.screen.questionsdetils;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dagg.udaggerdemo.R;
import com.example.dagg.udaggerdemo.model.QuestionDetails;
import com.example.dagg.udaggerdemo.screen.common.ImageLoader;
import com.example.dagg.udaggerdemo.screen.common.mvcview.BaseViewMvc;

public class QuestionDetailsViewMvcImpl extends BaseViewMvc<QuestionDetailsViewMvc.Listener>
    implements QuestionDetailsViewMvc {

    private final TextView mTxtQuestionBody;
    private final TextView mTxtUserDisplayName;
    private final ImageView mImgUserAvatar;
    private final ImageLoader mImageLoader;


    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup container,
        ImageLoader imageLoader) {
        setRootView(inflater.inflate(R.layout.layout_question_details, container, false));
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
        mTxtUserDisplayName = findViewById(R.id.txt_user_display_name);
        mImgUserAvatar = findViewById(R.id.img_user_avatar);
        mImageLoader = imageLoader;
    }

    @Override
    public void bindQuestion(QuestionDetails question) {
        String questionBody = question.getBody();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
        mTxtUserDisplayName.setText(question.getUserDisplayName());
        mImageLoader.loadImage(question.getUserAvatarUrl(), mImgUserAvatar);
    }
}