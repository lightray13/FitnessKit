package ru.anna.dev.fitnesskit.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import ru.anna.dev.fitnesskit.R;
import ru.anna.dev.fitnesskit.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    @Bind(R.id.fitness_name) protected TextView mFitnessName;
    @Bind(R.id.fitness_description) protected TextView mFitnessDescription;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        showBackArrow();

        String name = intent.getStringExtra(NAME);
        String description = intent.getStringExtra(DESCRIPTION);
        setTitle(name);

        mFitnessName.setText(name);
        mFitnessDescription.setText(description);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}