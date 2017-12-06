package com.kingoftheill.app1.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Andrade on 06/12/2017.
 */

@Entity
public class Items {

    @PrimaryKey @NonNull
    private int itemId;

    private String name;
    private String description;
    private String stat;
    private String image;

    public Items(int itemId, String name, String description, String stat, String image) {
        setItemId(itemId);
        setName(name);
        setDescription(description);
        setStat(stat);
        setImage(image);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
