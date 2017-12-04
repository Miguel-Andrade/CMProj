package com.kodelabs.boilerplate.presentation.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kodelabs.boilerplate.domain.model.Player;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Andrade on 28/11/2017.
 */

@Dao
public interface PlayerDao {

    @Insert(onConflict = REPLACE)
    void savePlayer(Player player);

    @Query("SELECT * FROM player WHERE id = :playerId")
    LiveData<Player> loadPlayer(int playerId);

    @Query("SELECT * FROM player WHERE name = :playerName")
    LiveData<Player> loadPlayerByName(String playerName);

    @Query("SELECT id FROM player WHERE name = :playerName")
    int getPlayerIdByName(String playerName);

    @Query("DELETE FROM player")
    void nukeAll();

    //@Query("SELECT  FROM player WHERE id = :playerId")
    //LiveData
}
