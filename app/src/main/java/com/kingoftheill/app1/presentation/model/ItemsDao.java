package com.kingoftheill.app1.presentation.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.kingoftheill.app1.domain.model.Items;

/**
 * Created by Andrade on 06/12/2017.
 */

@Dao
public interface ItemsDao {

    @Insert
    void saveItem(Items ... items);

    @Update
    void updateItem(Items ... items);
}
