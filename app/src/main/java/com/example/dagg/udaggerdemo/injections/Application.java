package com.example.dagg.udaggerdemo.injections;

import com.example.dagg.udaggerdemo.injections.application.ApplicationComponent;
import com.example.dagg.udaggerdemo.injections.application.ApplicationModule;
import com.example.dagg.udaggerdemo.injections.application.DaggerApplicationComponent;
import com.example.dagg.udaggerdemo.injections.application.NetworkModule;

public class Application extends android.app.Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule())
            .networkModule(new NetworkModule())
            .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
