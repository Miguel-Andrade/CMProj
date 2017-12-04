package com.kodelabs.boilerplate.presentation.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kodelabs.boilerplate.domain.model.Disease;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Andrade on 28/11/2017.
 */

@Dao
public interface DiseaseDao {

    @Insert(onConflict = REPLACE)
    void saveDisease(Disease disease);

    @Query("SELECT * FROM disease WHERE disId = :disId")
    LiveData<Disease> loadDis(int disId);

    @Query("SELECT * FROM disease")
    LiveData<List<Disease>> loadAllDiseases();
}
