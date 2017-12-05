package com.kodelabs.boilerplate.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"playerId", "disease"},
        foreignKeys = @ForeignKey(entity = Player.class,
                                  parentColumns = "name",
                                  childColumns = "playerId",
                                  onDelete = ForeignKey.CASCADE))
public class Infection {

	@NonNull
    private String playerId;
    @NonNull
    private String disease;

	private String playerName;
	private int damageGiven;
	
	public Infection (String playerId, String playerName, String disease, int damageGiven) {
		setPlayerId(playerId);
	    setPlayerName(playerName);
		setDisease(disease);
		setDamageGiven(damageGiven);
	}

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) { this.playerId = playerId; }

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
