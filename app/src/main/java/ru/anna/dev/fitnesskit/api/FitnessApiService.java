package ru.anna.dev.fitnesskit.api;

import java.util.List;

import retrofit2.http.GET;
import ru.anna.dev.fitnesskit.mvp.model.Fitness;
import rx.Observable;

public interface FitnessApiService {

    @GET("/schedule/get_group_lessons_v2/4/")
    Observable<List<Fitness>> getFitnesses();

}
