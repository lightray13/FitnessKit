package ru.anna.dev.fitnesskit.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.anna.dev.fitnesskit.mvp.model.Fitness;
import ru.anna.dev.fitnesskit.mvp.model.Storage;

public class FitnessMapper {

    @Inject
    public FitnessMapper() {
    }

    public List<Fitness> mapFitness(Storage storage, List<Fitness> response) {
        List<Fitness> fitnessList = new ArrayList<>();

        if (response != null) {
            Fitness[] responseFitness = response.toArray(new Fitness[response.size()]);
            int state;
            if (responseFitness != null) {
                if (storage.getSavedFitness().isEmpty()) {
                    state = 1;
                } else {
                    state = 2;
                }
                for (Fitness fitness : responseFitness) {
                    Fitness myFitness = new Fitness();
                    myFitness.setName(fitness.getName());
                    myFitness.setDescription(fitness.getDescription());
                    myFitness.setStartTime(fitness.getStartTime());
                    myFitness.setEndTime(fitness.getEndTime());
                    myFitness.setWeekDay(fitness.getWeekDay());
                    myFitness.setTeacher(fitness.getTeacher());
                    myFitness.setPlace(fitness.getPlace());
                    Log.d("myLogs", "BD: " + storage.getSavedFitness().isEmpty());
                    storage.addFitness(state, fitness.getName(), myFitness);
                    fitnessList.add(myFitness);
                }

            }
        }
        return fitnessList;

    }

}
