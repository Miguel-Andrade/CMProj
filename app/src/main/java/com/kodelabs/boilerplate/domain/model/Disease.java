package com.kodelabs.boilerplate.domain.model;

/**
 * Created by Andrade on 07/11/2017.
 */

public class Disease {

    private int id;
    private String name;
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

    public Disease (int id) {
    	//this.image = image;
    	this.id = id;
    	this.level = 1;
    	this.currXP = 0;
    	this.range = 0;
    	this.damage = 0;
    	this.resistence = 0;
    	this.btAttack = 1;
    	this.btDefense = 1;
    	this.numUpgrades = 0;
    }

    public int getLevel () { return level; }
    
    public int getCurrXP () { return currXP; }
    
    public void setCurrXPZero () {
    	currXP = 0;
    }
    
    public void setCurrXP (int xpGained) {
    	currXP += xpGained;
    }

	public int getRange () { return range; }

    public int getDamage () { return damage; }

    public int getResistence () { return resistence; }
    
    public String getImage () { return image; }

    public int getId() { return id; }

    public String getName () { return name; }
    
    public int getBtAttack () { return btAttack; }
    
    public int getBtDefense () { return btDefense; }

    public int getNumUpgrades() { return numUpgrades; }

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
