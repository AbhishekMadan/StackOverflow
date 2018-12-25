package com.example.dagg.udaggerdemo.screen.common.activity;

import android.support.v7.app.AppCompatActivity;

import com.example.dagg.udaggerdemo.injections.Application;
import com.example.dagg.udaggerdemo.injections.application.ApplicationComponent;
import com.example.dagg.udaggerdemo.injections.presentation.DaggerPresentationComponent;
import com.example.dagg.udaggerdemo.injections.presentation.PresentationComponent;
import com.example.dagg.udaggerdemo.injections.presentation.PresentationModule;

public class BaseActivity extends AppCompatActivity{

    private boolean mIsInjectorUsed;

    public ApplicationComponent getCompositionRoot() {
        return ((Application) getApplication()).getApplicationComponent();
    }

    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("Activity/Fragment already injected.");
        }
        mIsInjectorUsed = true;
        return DaggerPresentationComponent.builder()
            .presentationModule(new PresentationModule(this, getCompositionRoot()))
            .build();
    }
}
