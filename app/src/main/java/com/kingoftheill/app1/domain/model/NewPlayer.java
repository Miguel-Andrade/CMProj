package com.kingoftheill.app1.domain.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.kingoftheill.app1.domain.model.utilities.PlayerLevels;

/**
 * Created by Andrade on 06/12/2017.
 */

@Entity
public class NewPlayer {

    @PrimaryKey
    @NonNull
    private String name;

    private String image;

    private int idN;

    //CORE_STATS
    private int life;
    private int range;
    private int damage;
    private int resistance;

    //BATTLE
    private int btAttack;
    private int btDefense;

    //COMMONS_STATS
    private int level;
    private int currXP;

    //Disease
    @Embedded
    private NewDisease disease;

    public NewPlayer(String name, String image) {
        setName(name);
        setImage(image);
        setLevel(PlayerLevels.LEVEL_1.level());
        setCurrXP(0);
        setDamage(PlayerLevels.LEVEL_1.damage());
        setLife(PlayerLevels.LEVEL_1.life());
        setRange(PlayerLevels.LEVEL_1.range());
        setResistance(PlayerLevels.LEVEL_1.resistance());
    }

    public void levelUp () {
        String l = "LEVEL_"+level;
        PlayerLevels pl = PlayerLevels.valueOf(l);
        level++;
        life+= pl.life();
        range+= pl.range();
        resistance += pl.resistance();
        damage += pl.damage();
    }

    public int getIdN() {
        return idN;
    }

    public void setIdN(int id) {
        this.idN = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getBtAttack() {
        return btAttack;
    }

    public void setBtAttack(int btAttack) {
        this.btAttack = btAttack;
    }

    public int getBtDefense() {
        return btDefense;
    }

    public void setBtDefense(int btDefense) {
        this.btDefense = btDefense;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrXP() {
        return currXP;
    }

    public void setCurrXP (int xpGained) {
        currXP += xpGained;
    }

    public NewDisease getDisease() {
        return disease;
    }

    public void setDisease(NewDisease disease) {
        this.disease = disease;
    }

    public void reduceLife (int reduction) {
        life-=reduction;
    }
}

