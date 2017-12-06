package com.kingoftheill.app1.domain2;

/**
 * Created by Andrade on 06/12/2017.
 */

public class Item {

    private String name;
    private String description;
    private String stat;
    private int value;
    private String image;

    public Item() {
    }

    public Item(String name, String description, String stat, int value, String image) {
        this.name = name;
        this.description = description;
        this.stat = stat;
        this.value = value;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStat() {
        return stat;
    }

    public int getValue() {
        return value;
    }

    public String getImage() {
        return image;
    }
}
