package com.kingoftheill.app1.presentation.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.kingoftheill.app1.domain.model.Infection;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Andrade on 30/11/2017.
 */

@Dao
public interface InfectionDao {

    @Insert(onConflict = REPLACE)
    void saveInfection(Infection infection);
}
