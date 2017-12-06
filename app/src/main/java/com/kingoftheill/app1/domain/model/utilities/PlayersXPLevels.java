package com.kingoftheill.app1.domain.model.utilities;

public enum PlayersXPLevels {
	LEVEL_1 (1, 1, 10),
	LEVEL_2 (2, 11, 25),
	LEVEL_3 (3, 25, 37),
	LEVEL_4 (4, 38, 54),
	LEVEL_5 (5, 55, 71),
	LEVEL_6 (6, 1, 10),
	LEVEL_7 (7, 1, 10);
	
	private int level;
	private int lowBound;
	private int highBound;
	
	private PlayersXPLevels(int level, int lowBound, int highBound) {
		this.level = level;
		this.lowBound = lowBound;
		this.highBound = highBound;
	}
	
	
	public int level () { return level; }
	
	public int lowBound () { return lowBound; }
	
	public int highBound () { return highBound; }
}
