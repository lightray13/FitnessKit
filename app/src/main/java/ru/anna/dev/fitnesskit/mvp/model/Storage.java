package ru.anna.dev.fitnesskit.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Storage extends SQLiteOpenHelper {

    private static final String TAG = Storage.class.getSimpleName();

    @Inject
    public Storage(Context context) {
        super(context, "fitness_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addFitness(int state, String name, Fitness fitness) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, fitness.getName());
        values.put(DESCRIPTION, fitness.getDescription());
        values.put(START_TIME, fitness.getStartTime());
        values.put(END_TIME, fitness.getEndTime());
        values.put(WEEK_DAY, fitness.getWeekDay());
        values.put(TEACHER, fitness.getTeacher());
        values.put(PLACE, fitness.getPlace());

        if (state == 1) {
            try {
                db.insert(TABLE_NAME, null, values);
            } catch(SQLException e) {
                Log.d(TAG, e.getMessage());
            }
        } else if (state == 2) {
            db.update(TABLE_NAME, values, "name = ?", new String[]{name});
        }

        db.close();
    }

    public List<Fitness> getSavedFitness() {
        List<Fitness> fitnessList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Fitness fitness = new Fitness();
                            fitness.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                            fitness.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                            fitness.setStartTime(cursor.getString(cursor.getColumnIndex(START_TIME)));
                            fitness.setEndTime(cursor.getString(cursor.getColumnIndex(END_TIME)));
                            fitness.setWeekDay(cursor.getInt(cursor.getColumnIndex(WEEK_DAY)));
                            fitness.setTeacher(cursor.getString(cursor.getColumnIndex(TEACHER)));
                            fitness.setPlace(cursor.getString(cursor.getColumnIndex(PLACE)));
                            fitnessList.add(fitness);

                        } while (cursor.moveToNext());
                    }
                }
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return fitnessList;
    }



    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String WEEK_DAY = "weekDay";
    private static final String TEACHER = "teacher";
    private static final String PLACE = "place";
    private static final String TABLE_NAME = "fitness";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            NAME + " text not null," +
            DESCRIPTION + " text not null," +
            START_TIME + " text not null," +
            END_TIME + " text not null," +
            WEEK_DAY + " int not null," +
            TEACHER + " text not null," +
            PLACE + " text not null)";
}
