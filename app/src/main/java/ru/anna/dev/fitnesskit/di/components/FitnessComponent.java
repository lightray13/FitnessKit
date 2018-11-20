package ru.anna.dev.fitnesskit.di.components;

import dagger.Component;
import ru.anna.dev.fitnesskit.di.module.FitnessModule;
import ru.anna.dev.fitnesskit.modules.home.MainActivity;
import ru.anna.dev.fitnesskit.di.scope.PerActivity;

@PerActivity
@Component(modules = FitnessModule.class, dependencies = ApplicationComponent.class)
public interface FitnessComponent {

    void inject(MainActivity activity);
}
