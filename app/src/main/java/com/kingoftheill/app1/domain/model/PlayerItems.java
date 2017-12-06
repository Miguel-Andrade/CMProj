package com.kingoftheill.app1.domain.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by Andrade on 06/12/2017.
 */

@Entity(primaryKeys = {"itemId", "quantity"})
public class PlayerItems {

    @Embedded @NonNull
    private Items item;

    private int quantity;


    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
