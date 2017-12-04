package com.kodelabs.boilerplate.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.kodelabs.boilerplate.domain.model.utilities.PlayerLevels;

/**
 * Created by Andrade on 07/11/2017.
 */

@Entity
public class Player {

    //COMMONS
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String image;

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
    
    //INFECTED
    //private Infection[] infected;
    
    
    /*public Player(String name, String image) {
    	this.id = new Random().nextInt();
        this.name = name;
        this.image = image;
        this.level = PlayerLevels.LEVEL_1.level();
        this.currXP = 0;
        this.damage = PlayerLevels.LEVEL_1.damage();
        this.life = PlayerLevels.LEVEL_1.life();
        this.range = PlayerLevels.LEVEL_1.range();
        this.resistance = PlayerLevels.LEVEL_1.resistance();
        //this.infected = new Infection[2];
    }*/

    public Player(String name, String image) {
        setName(name);
        setImage(image);
        setLevel(PlayerLevels.LEVEL_1.level());
        setCurrXP(0);
        setDamage(PlayerLevels.LEVEL_1.damage());
        setLife(PlayerLevels.LEVEL_1.life());
        setRange(PlayerLevels.LEVEL_1.life());
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	/*public Infection[] getInfected() {
		return infected;
	}

	public void setInfected(Infection infected) {
		if (this.infected[0] != null)
			this.infected[0] = infected;
		else
			this.infected[1] = infected; 
	}*/
	
	public void reduceLife (int reduction) {
		life-=reduction;
	}

}
