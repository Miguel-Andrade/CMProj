package com.kodelabs.boilerplate.presentation.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Infection;
import com.kodelabs.boilerplate.domain.model.Player;

/**
 * Created by Andrade on 28/11/2017.
 */

@Database(entities = {Player.class, Disease.class, Infection.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db")
                            .build();

        }
        return INSTANCE;
    }


    public abstract PlayerDao playerDao();

    public abstract DiseaseDao disDao();

    public abstract InfectionDao infectionDao();
}
