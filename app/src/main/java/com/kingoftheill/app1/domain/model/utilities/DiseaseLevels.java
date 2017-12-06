package com.kingoftheill.app1.domain.model.utilities;

public enum DiseaseLevels {
	RANGE (1,1,1,1,1,1,1,1,1,1),
	DAMAGE (2,2,2,2,2,2,2,2,2,2),
	RESISTENCE (2,2,2,2,2,2,2,2,2,2),
	BTATTACK (1,1,1,1,1,1,1,1,1,1),
	BTDEFENSE (1,1,1,1,1,1,1,1,1,1);
	
	private final int[] level;
	
	private DiseaseLevels(int... level) {
		this.level = level;
	}
	
	public int getPointByLevel (int level) {
		return this.level[level];
	}
}
