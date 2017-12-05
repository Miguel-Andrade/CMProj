package com.kodelabs.boilerplate.storage;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.presentation.model.AppDatabase;
import com.kodelabs.boilerplate.presentation.model.DiseaseDao;
import com.kodelabs.boilerplate.presentation.model.PlayerDao;

/**
 * Created by Andrade on 19/11/2017.
 */

public class AppRepositoryImpl {

    public static PlayerDao playerDao;
    public static DiseaseDao diseaseDao;

    private static AppRepositoryImpl INSTANCE;

    public static AppRepositoryImpl getInstante(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppRepositoryImpl();
            playerDao = AppDatabase.getDatabase(context).playerDao();
            diseaseDao = AppDatabase.getDatabase(context).disDao();
        }

        return INSTANCE;
    }

    public void insertPlayer(Player player) { playerDao.savePlayer(player); }

    public void insertDisease(Disease disease) { diseaseDao.saveDisease(disease); }

    public LiveData<Player> getPlayer(int playerId) {
        return playerDao.loadPlayer(playerId);
    }

    public LiveData<Player> getPlayerByName(String playerName) {
        return playerDao.loadPlayerByName(playerName);
    }

    public LiveData<Disease> getDisease(int diseaseId) { return diseaseDao.loadDis(diseaseId);}

}
