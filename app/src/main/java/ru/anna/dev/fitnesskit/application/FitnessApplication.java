package ru.anna.dev.fitnesskit.application;

import android.app.Application;

import ru.anna.dev.fitnesskit.di.components.ApplicationComponent;
import ru.anna.dev.fitnesskit.di.components.DaggerApplicationComponent;
import ru.anna.dev.fitnesskit.di.module.ApplicationModule;

public class FitnessApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, "https://sample.fitnesskit-admin.ru"))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
