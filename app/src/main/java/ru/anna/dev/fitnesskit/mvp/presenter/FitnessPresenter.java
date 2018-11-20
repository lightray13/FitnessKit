package ru.anna.dev.fitnesskit.mvp.presenter;

import java.util.List;

import javax.inject.Inject;

import ru.anna.dev.fitnesskit.api.FitnessApiService;
import ru.anna.dev.fitnesskit.base.BasePresenter;
import ru.anna.dev.fitnesskit.mapper.FitnessMapper;
import ru.anna.dev.fitnesskit.mvp.model.Fitness;
import ru.anna.dev.fitnesskit.mvp.model.Storage;
import ru.anna.dev.fitnesskit.mvp.view.MainView;
import rx.Observable;
import rx.Observer;

public class FitnessPresenter extends BasePresenter<MainView> implements Observer<List<Fitness>> {

    @Inject
    protected FitnessApiService mApiService;
    @Inject protected FitnessMapper mFitnessMapper;
    @Inject protected Storage mStorage;

    @Inject
    public FitnessPresenter() {
    }

    public void getFitnesses() {
        getView().onShowDialog("Загрузка расписания....");
        Observable<List<Fitness>> fitnessResponseObservable = mApiService.getFitnesses();
        subscribe(fitnessResponseObservable, this);
    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
    }

    @Override
    public void onNext(List<Fitness> fitnessResponces) {
        List<Fitness> fitnesses = mFitnessMapper.mapFitness(mStorage, fitnessResponces);
        getView().onClearItems();
        getView().onFitnessLoaded(fitnesses);
    }


    public void getFitnessFromDatabase() {
        List<Fitness> fitnesses = mStorage.getSavedFitness();
        getView().onClearItems();
        getView().onFitnessLoaded(fitnesses);
    }
}
