package com.example.dagg.udaggerdemo.screen.common.activity;

import android.support.v7.app.AppCompatActivity;

import com.example.dagg.udaggerdemo.injections.Application;
import com.example.dagg.udaggerdemo.injections.CompositionRoot;
import com.example.dagg.udaggerdemo.injections.PresentationCompositionRoot;

public class BaseActivity extends AppCompatActivity{

    private PresentationCompositionRoot mPresentationCompositionRoot;

    public CompositionRoot getCompositionRoot() {
        return ((Application) getApplication()).getCompositionRoot();
    }

    public PresentationCompositionRoot getPresentationCompositionRoot() {
        if (mPresentationCompositionRoot == null) {
            mPresentationCompositionRoot = new PresentationCompositionRoot(getSupportFragmentManager());
        }
        return mPresentationCompositionRoot;
    }
}
