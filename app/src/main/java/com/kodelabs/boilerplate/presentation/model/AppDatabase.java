package com.kodelabs.boilerplate.presentation.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Infection;
import com.kodelabs.boilerplate.domain.model.Player;

/**
 * Created by Andrade on 28/11/2017.
 */

@Database(entities = {Player.class, Disease.class, Infection.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new AbstractInteractor() {
                                        @Override
                                        public void run() {
                                            INSTANCE.disDao().saveDisease(new Disease(1, 0));
                                            INSTANCE.disDao().saveDisease(new Disease(2, 1));
                                            INSTANCE.playerDao().savePlayer(new Player("Me", "bubonic_plague_doc_icon_3"));
                                            INSTANCE.playerDao().savePlayer(new Player("attacker", "logo_pic_smallpox_alce", 1));
                                        }
                                    }.execute();
                                }
                            })
                            .build();

        }
        return INSTANCE;
    }


    public abstract PlayerDao playerDao();

    public abstract DiseaseDao disDao();

    public abstract InfectionDao infectionDao();
}
