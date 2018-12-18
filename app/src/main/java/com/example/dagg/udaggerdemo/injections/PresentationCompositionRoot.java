package com.example.dagg.udaggerdemo.injections;

import android.support.v4.app.FragmentManager;

import com.example.dagg.udaggerdemo.screen.common.dialogue.DialogManager;

public class PresentationCompositionRoot {

    private FragmentManager mFragmentManager;

    public PresentationCompositionRoot(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public DialogManager getDialogueManager() {
        return new DialogManager(mFragmentManager);
    }
}
