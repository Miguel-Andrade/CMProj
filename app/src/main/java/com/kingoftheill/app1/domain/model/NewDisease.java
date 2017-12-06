package com.kingoftheill.app1.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Andrade on 06/12/2017.
 */

@Entity
public class NewDisease {

    @PrimaryKey @NonNull
    private int disId;

    private int type;
    private String disImage;

    //COMMONS_STATS
    private int disLevel;
    private int disCurrXP;

    //Stats
    private int disRange;
    private int disDamage;
    private int disResistence;
    private int disBtAttack;
    private int disBtDefense;

    private int numUpgrades;

    public NewDisease (int type) {
        setDisId(0);
        setType(type);
        setDisLevel(1);
        setDisCurrXP(0);
        setDisRange(0);
        setDisDamage(0);
        setDisResistence(0);
        setDisBtAttack(1);
        setDisBtDefense(1);
        setNumUpgrades(0);
    }

    @NonNull
    public int getDisId() {
        return disId;
    }

    public void setDisId(@NonNull int disId) {
        this.disId = disId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDisImage() {
        return disImage;
    }

    public void setDisImage(String disImage) {
        this.disImage = disImage;
    }

    public int getDisLevel() {
        return disLevel;
    }

    public void setDisLevel(int disLevel) {
        this.disLevel = disLevel;
    }

    public int getDisCurrXP() {
        return disCurrXP;
    }

    public int getDisRange() {
        return disRange;
    }

    public void setDisRange(int disRange) {
        this.disRange = disRange;
    }

    public int getDisDamage() {
        return disDamage;
    }

    public void setDisDamage(int disDamage) {
        this.disDamage = disDamage;
    }

    public int getDisResistence() {
        return disResistence;
    }

    public void setDisResistence(int disResistence) {
        this.disResistence = disResistence;
    }

    public int getDisBtAttack() {
        return disBtAttack;
    }

    public void setDisBtAttack(int disBtAttack) {
        this.disBtAttack = disBtAttack;
    }

    public int getDisBtDefense() {
        return disBtDefense;
    }

    public void setDisBtDefense(int disBtDefense) {
        this.disBtDefense = disBtDefense;
    }

    public int getNumUpgrades() {
        return numUpgrades;
    }

    public void setDisCurrXP(int xpGained) {
        disCurrXP += xpGained;
    }

    public void setNumUpgrades(int numUpgrades) { this.numUpgrades += numUpgrades; }

    public void levelUp () {
        disLevel++;
    }

    public void upgrade (String stat) {
        switch (stat) {
            case "disRange":
                disRange++;
                break;

            case "disDamage":
                disDamage++;
                break;

            case "disResistence":
                disResistence++;
                break;

            case "disBtAttack":
                disBtAttack++;
                break;

            case "disBtDefense":
                disBtDefense++;
                break;
        }
    }
}
