package com.kodelabs.boilerplate.domain.model.utilities;

public enum DiseasesTypes {
	BUBONIC_PLAGUE (0, "bubonic plague", "damage", 1),
	SMALLPOX (1, "smallpox", "resistance", 1),
	INFLUENZA (2, "influenza", "range", 1);

	private final int id;
	private final String name;
	private final String stat;
	private final int value;
	
	DiseasesTypes (int id, String name, String stat, int value) {
		this.id = id;
		this.name = name;
		this.stat = stat;
		this.value = value;
	}

	public int getId () { return id; }

	public String getName () { return name; }
	
	public String getStat () { return stat; }
	
	public int getValue () { return value; }

}
