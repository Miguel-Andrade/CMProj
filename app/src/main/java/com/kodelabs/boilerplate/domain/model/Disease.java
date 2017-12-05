package com.kodelabs.boilerplate.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Andrade on 07/11/2017.
 */

@Entity
public class Disease {

    @PrimaryKey
    private int disId;

    private int type;
    private String image;

    //COMMONS_STATS
    private int level;
    private int currXP;
    
    //Stats
    private int range;
    private int damage;
    private int resistence;
    private int btAttack;
    private int btDefense;

    private int numUpgrades;

    public Disease (int type, int disId) {
        setDisId(disId);
        setType(type);
        setLevel(1);
        setCurrXP(0);
        setRange(0);
        setDamage(0);
        setResistence(0);
        setBtAttack(1);
        setBtDefense(1);
        setNumUpgrades(0);
    }

    public int getDisId() {
        return disId;
    }

    public void setDisId(int disId) {
        this.disId = disId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getResistence() {
        return resistence;
    }

    public void setResistence(int resistence) {
        this.resistence = resistence;
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

    public int getNumUpgrades() {
        return numUpgrades;
    }

    public void setCurrXP (int xpGained) {
    	currXP += xpGained;
    }

    public void setNumUpgrades(int numUpgrades) { this.numUpgrades += numUpgrades; }

    public void levelUp () {
    	level++;
    }
    
    public void upgrade (String stat) {
    	switch (stat) {
    		case "range":
    			range++;
    			break;
    			
    		case "damage":
    			damage++;
    			break;
    			
    		case "resistence":
    			resistence++;
    			break;
    			
    		case "btAttack":
    			btAttack++;
    			break;
    			
    		case "btDefense":
    			btDefense++;
    			break;
    	}
    }
    
}
