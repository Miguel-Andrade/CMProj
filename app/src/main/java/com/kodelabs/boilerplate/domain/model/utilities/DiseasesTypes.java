package com.kodelabs.boilerplate.domain.model.utilities;

public enum DiseasesTypes {
	BUBONIC_PLAGUE ("bubonic plague", "damage", 1),
	SMALLPOX ("smallpox", "resistance", 1),
	INFLUENZA ("influenza", "range", 1);
	
	private final String name;
	private final String stat;
	private final int value;
	
	DiseasesTypes (String name, String stat, int value) {
		this.name = name;
		this.stat = stat;
		this.value = value;
	}
	
	public String getName () { return name; }
	
	public String getStat () { return stat; }
	
	public int getValue () { return value; }

}
