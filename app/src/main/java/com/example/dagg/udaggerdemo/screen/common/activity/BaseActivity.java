package com.example.dagg.udaggerdemo.screen.common.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.dagg.udaggerdemo.injections.Application;
import com.example.dagg.udaggerdemo.injections.PresentationCompositionRoot;
import com.example.dagg.udaggerdemo.injections.application.ApplicationComponent;

public class BaseActivity extends AppCompatActivity{

    private PresentationCompositionRoot mPresentationCompositionRoot;

    public ApplicationComponent getCompositionRoot() {
        return ((Application) getApplication()).getApplicationComponent();
    }

    public PresentationCompositionRoot getPresentationCompositionRoot() {
        if (mPresentationCompositionRoot == null) {
            mPresentationCompositionRoot = new PresentationCompositionRoot(getSupportFragmentManager(),
                LayoutInflater.from(this), this);
        }
        return mPresentationCompositionRoot;
    }
}
