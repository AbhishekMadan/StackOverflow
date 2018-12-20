package com.example.dagg.udaggerdemo.injections;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.dagg.udaggerdemo.screen.common.ImageLoader;
import com.example.dagg.udaggerdemo.screen.common.dialogue.DialogManager;
import com.example.dagg.udaggerdemo.screen.common.mvcview.ViewMvcFactoy;

public class PresentationCompositionRoot {

    private FragmentManager mFragmentManager;
    private LayoutInflater mLayoutInflater;
    private Activity mActivity;

    public PresentationCompositionRoot(FragmentManager fragmentManager,
                                       LayoutInflater inflater,
                                       Activity activity) {
        mFragmentManager = fragmentManager;
        mLayoutInflater = inflater;
        mActivity = activity;
    }

    public DialogManager getDialogueManager() {
        return new DialogManager(mFragmentManager);
    }

    public ViewMvcFactoy getViewMvcFactory() {
        return new ViewMvcFactoy(mLayoutInflater);
    }

    public ImageLoader getImageLoader() {
        return new ImageLoader(mActivity);
    }
}
