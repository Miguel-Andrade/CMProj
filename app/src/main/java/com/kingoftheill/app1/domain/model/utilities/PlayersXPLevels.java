package com.kingoftheill.app1.domain.model.utilities;

public enum PlayersXPLevels {
	LEVEL_1 (1, 1, 10),
	LEVEL_2 (2, 11, 25),
	LEVEL_3 (3, 25, 37),
	LEVEL_4 (4, 38, 54),
	LEVEL_5 (5, 55, 71),
	LEVEL_6 (6, 71, 81),
	LEVEL_7 (7, 81, 91),
	LEVEL_8 (8, 91, 101),
	LEVEL_9 (9, 101, 111),
	LEVEL_10 (10, 111, 121),
	LEVEL_11 (11, 121, 131);
	
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
