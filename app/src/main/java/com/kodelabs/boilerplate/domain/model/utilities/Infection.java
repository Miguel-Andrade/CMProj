package com.kodelabs.boilerplate.domain.model.utilities;

public class Infection {
	
	private String playerName;
	private String disease;
	private int damageGiven;
	
	public Infection (String playerName, String disease, int damageGiven) {
		this.setPlayerName(playerName);
		this.setDisease(disease);
		this.setDamageGiven(damageGiven);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getDamageGiven() {
		return damageGiven;
	}

	public void setDamageGiven(int damageGiven) {
		this.damageGiven = damageGiven;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

}
