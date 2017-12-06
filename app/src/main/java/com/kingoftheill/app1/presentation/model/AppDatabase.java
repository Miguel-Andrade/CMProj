package com.kingoftheill.app1.presentation.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.kingoftheill.app1.domain.interactors.base.AbstractInteractor;
import com.kingoftheill.app1.domain.model.Infection;
import com.kingoftheill.app1.domain.model.Items;
import com.kingoftheill.app1.domain.model.NewDisease;
import com.kingoftheill.app1.domain.model.NewPlayer;
import com.kingoftheill.app1.domain.model.PlayerItems;

/**
 * Created by Andrade on 28/11/2017.
 */

@Database(entities = {Infection.class, NewPlayer.class, Items.class, PlayerItems.class}, version = 12, exportSchema = false)
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
                                            //INSTANCE.disDao().saveDisease(new Disease(1, 0));
                                            //INSTANCE.disDao().saveDisease(new Disease(2, 1));
                                            //INSTANCE.playerDao().savePlayer(new Player("Me", "bubonic_plague_doc_icon_3"));
                                            //INSTANCE.playerDao().savePlayer(new Player("attacker", "logo_pic_smallpox_alce", 1));
                                            INSTANCE.newPlayerDao().savePlayer(new NewPlayer("MEE", "bubonic_plague_doc_icon_3"));
                                            NewPlayer player = INSTANCE.newPlayerDao().getPlayer("MEE");
                                            player.setDisease(new NewDisease(1));
                                            INSTANCE.newPlayerDao().updatePlayer(player);
                                        }
                                    }.execute();
                                }
                            })
                            .build();

        }
        return INSTANCE;
    }

    public abstract InfectionDao infectionDao();

    public abstract NewPlayerDao newPlayerDao();

    public abstract ItemsDao itemsDao();
}
