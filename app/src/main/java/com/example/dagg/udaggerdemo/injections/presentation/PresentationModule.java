package com.example.dagg.udaggerdemo.injections.presentation;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.dagg.udaggerdemo.screen.common.ImageLoader;
import com.example.dagg.udaggerdemo.screen.common.dialogue.DialogManager;
import com.example.dagg.udaggerdemo.screen.common.mvcview.ViewMvcFactoy;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private final FragmentActivity mActivity;

    public PresentationModule(FragmentActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    public FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    public DialogManager getDialogueManager(FragmentManager manager) {
        return new DialogManager(manager);
    }

    @Provides
    public ImageLoader getImageLoader() {
        return new ImageLoader(mActivity);
    }
}
