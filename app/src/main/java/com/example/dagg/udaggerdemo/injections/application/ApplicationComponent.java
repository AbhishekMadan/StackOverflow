package com.example.dagg.udaggerdemo.injections.application;

import com.example.dagg.udaggerdemo.injections.presentation.PresentationComponent;
import com.example.dagg.udaggerdemo.injections.presentation.PresentationModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
