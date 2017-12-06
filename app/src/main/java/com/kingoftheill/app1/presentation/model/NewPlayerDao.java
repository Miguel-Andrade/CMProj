package com.kingoftheill.app1.presentation.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kingoftheill.app1.domain.model.NewPlayer;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Andrade on 06/12/2017.
 */

@Dao
public interface NewPlayerDao {

    @Insert(onConflict = REPLACE)
    void savePlayer(NewPlayer player);

    @Update
    void updatePlayer(NewPlayer player);

    @Query("SELECT * FROM newplayer WHERE name = :playerName")
    NewPlayer getPlayer(String playerName);

}
