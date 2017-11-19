package com.kodelabs.boilerplate.domain.model.utilities;

/**
 * Created by Andrade on 07/11/2017.
 */

public enum PlayerLevels {
	LEVEL_1 (1, 100, 2, 5, 3),
	LEVEL_2 (2, 105, 3, 7, 5),
	LEVEL_3 (3, 110, 3, 7, 5),
	LEVEL_4 (4, 115, 4, 11, 9),
	LEVEL_5 (5, 120, 4, 11, 9),
	LEVEL_6 (6, 125, 5, 13, 11),
	LEVEL_7 (7, 130, 5, 13, 11),
	LEVEL_8 (8, 135, 6, 15, 13),
	LEVEL_9 (9, 140, 6, 15, 13),
	LEVEL_10 (10, 150, 7, 17, 15);
	
	private final int level;
	private final int life;
	private final int range;
	private final int damage;
	private final int resistance;
	
	PlayerLevels (int level, int life, int range, int damage, int resistance) {
		this.level = level;
		this.life = life;
		this.range = range;
		this.damage = damage;
		this.resistance = resistance;
		
	}
	
	public int level () { return level;	}
	
	public int life () { return life; }
	
	public int damage () { return damage; }
	
	public int range () { return range; }
	
	public int resistance () { return resistance; }
}
