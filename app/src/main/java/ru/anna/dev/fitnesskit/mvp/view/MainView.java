package ru.anna.dev.fitnesskit.mvp.view;

import java.util.List;

import ru.anna.dev.fitnesskit.mvp.model.Fitness;

public interface MainView extends BaseView {

    void onFitnessLoaded(List<Fitness> fitnesses);

    void onShowDialog(String message);

    void onHideDialog();

    void onShowToast(String message);

    void onClearItems();
}
