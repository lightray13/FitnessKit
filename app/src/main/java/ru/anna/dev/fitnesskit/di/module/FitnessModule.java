package ru.anna.dev.fitnesskit.di.module;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.anna.dev.fitnesskit.api.FitnessApiService;
import ru.anna.dev.fitnesskit.di.scope.PerActivity;
import ru.anna.dev.fitnesskit.mvp.view.MainView;

@Module
public class FitnessModule {

    private MainView mView;

    public FitnessModule(MainView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    FitnessApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(FitnessApiService.class);
    }

    @PerActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
