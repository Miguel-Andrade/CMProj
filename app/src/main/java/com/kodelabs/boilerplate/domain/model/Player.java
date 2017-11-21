package com.kodelabs.boilerplate.domain.model;

import com.kodelabs.boilerplate.domain.model.utilities.DiseasesTypes;
import com.kodelabs.boilerplate.domain.model.utilities.Infection;
import com.kodelabs.boilerplate.domain.model.utilities.PlayerLevels;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Andrade on 07/11/2017.
 */

public class Player {

    private final int XP_MULTIPLIER = 10;

    //CORE_STATS
    private Disease disease;
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

    //COMMONS
	private int id;
    private String name;
    private String image;
    
    //INFECTED
    private Infection[] infected;
    
    
    public Player(String name, String image) {
    	this.id = new Random().nextInt();
        this.name = name;
        this.image = image;
        this.level = PlayerLevels.LEVEL_1.level();
        this.currXP = 0;
        this.damage = PlayerLevels.LEVEL_1.damage();
        this.life = PlayerLevels.LEVEL_1.life();
        this.range = PlayerLevels.LEVEL_1.range();
        this.resistance = PlayerLevels.LEVEL_1.resistance();
        this.infected = new Infection[2];
    }
    
    public Player (int level) {
    	name = "Adverserie";
    	for (int i = level; i > 0; i--) {
    		String l = "LEVEL_"+level;
        	PlayerLevels pl = PlayerLevels.valueOf(l);
        	level++;
        	life+= pl.life();
        	range+= pl.range();
        	resistance += pl.resistance();
        	damage += pl.damage();
    	}
    	disease = new Disease(DiseasesTypes.INFLUENZA.getId());
    	for (int i = level; i > 0; i--) {

    			disease.levelUp();
				disease.upgrade("damage");

    	}
    	this.infected = new Infection[2];
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

    public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public int getXP_MULTIPLIER() {
		return XP_MULTIPLIER;
	}

	public int getRange() {
		return range;
	}

	public int getDamage() {
		return damage;
	}

	public int getResistence() {
		return resistance;
	}

	public int getBtAttack() {
		return btAttack;
	}

	public int getBtDefense() {
		return btDefense;
	}

	public int getCurrXP() {
		return currXP;
	}
	
	public void setCurrXP (int xpGained) {
		currXP += xpGained;
	}

	public String getName() {
		return name;
	}

    public int getId() { return id; }

    public String getImage() {
		return image;
	}

	public Disease getDisease() {
        return disease;
    }

    public int getLife() {
        return life;
    }

    public int getTotalRange() {
        return range+disease.getRange();
    }

    public int getTotalDamage() {
        return damage+disease.getDamage();
    }

    public int getTotalResistence() {
        return resistance+disease.getResistence();
    }

    public int getLevel() {
        return level;
    }

	public Infection[] getInfected() {
		return infected;
	}

	public void setInfected(Infection infected) {
		if (this.infected[0] != null)
			this.infected[0] = infected;
		else
			this.infected[1] = infected; 
	}
	
	public void reduceLife (int reduction) {
		life-=reduction;
	}

}
