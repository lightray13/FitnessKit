package ru.anna.dev.fitnesskit.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import ru.anna.dev.fitnesskit.R;
import ru.anna.dev.fitnesskit.base.BaseActivity;
import ru.anna.dev.fitnesskit.di.components.DaggerFitnessComponent;
import ru.anna.dev.fitnesskit.di.module.FitnessModule;
import ru.anna.dev.fitnesskit.modules.details.DetailActivity;
import ru.anna.dev.fitnesskit.modules.home.adapter.FitnessAdapter;
import ru.anna.dev.fitnesskit.mvp.model.Fitness;
import ru.anna.dev.fitnesskit.mvp.presenter.FitnessPresenter;
import ru.anna.dev.fitnesskit.mvp.view.MainView;
import ru.anna.dev.fitnesskit.utilities.NetworkUtils;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.fitness_list) protected RecyclerView mFitnessList;
    @Inject
    protected FitnessPresenter mPresenter;
    private FitnessAdapter mFitnessAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        loadFitness();
    }

    private void loadFitness() {
        if(NetworkUtils.isNetAvailable(this)) {
            mPresenter.getFitnesses();
        } else {
            mPresenter.getFitnessFromDatabase();
        }
    }

    private void initializeList() {
        mFitnessList.setHasFixedSize(true);
        mFitnessList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFitnessAdapter = new FitnessAdapter(getLayoutInflater());
        mFitnessAdapter.setFitnessClickListener(mFitnessClickListener);
        mFitnessList.setAdapter(mFitnessAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerFitnessComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fitnessModule(new FitnessModule(this))
                .build().inject(this);
    }

    @Override
    public void onFitnessLoaded(List<Fitness> fitnesses) {
        mFitnessAdapter.addFitnesses(fitnesses);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearItems() {
        mFitnessAdapter.clearFitnesses();
    }

    private FitnessAdapter.OnFitnessClickListener mFitnessClickListener = new FitnessAdapter.OnFitnessClickListener() {
        @Override
        public void onClick(String name, String description, int position) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.NAME, name);
            intent.putExtra(DetailActivity.DESCRIPTION, description);
            startActivity(intent);
        }
    };
}
