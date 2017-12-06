package com.kingoftheill.app1.storage;

import android.content.Context;

import com.kingoftheill.app1.presentation.model.AppDatabase;
import com.kingoftheill.app1.presentation.model.ItemsDao;
import com.kingoftheill.app1.presentation.model.NewPlayerDao;

/**
 * Created by Andrade on 19/11/2017.
 */

public class AppRepositoryImpl {


    public static NewPlayerDao newPlayerDao;
    public static ItemsDao itemsDao;

    private static AppRepositoryImpl INSTANCE;

    public static AppRepositoryImpl getInstante(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppRepositoryImpl();
            newPlayerDao = AppDatabase.getDatabase(context).newPlayerDao();
            itemsDao = AppDatabase.getDatabase(context).itemsDao();
        }

        return INSTANCE;
    }

    /*public void insertPlayer(Player player) { playerDao.savePlayer(player); }

    public void insertDisease(Disease disease) { diseaseDao.saveDisease(disease); }

    public LiveData<Player> getPlayer(int playerId) {
        return playerDao.loadPlayer(playerId);
    }

    public LiveData<Player> getPlayerByName(String playerName) {
        return playerDao.loadPlayerByName(playerName);
    }

    public LiveData<Disease> getDisease(int diseaseId) { return diseaseDao.loadDis(diseaseId);}*/

}
