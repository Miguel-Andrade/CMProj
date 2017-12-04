package com.kodelabs.boilerplate.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"playerId", "disease"},
        foreignKeys = @ForeignKey(entity = Player.class,
                                  parentColumns = "id",
                                  childColumns = "playerId",
                                  onDelete = ForeignKey.CASCADE))
public class Infection {


    private int playerId;
    @NonNull
    private String disease;

	private String playerName;
	private int damageGiven;
	
	public Infection (int playerId, String playerName, String disease, int damageGiven) {
		setPlayerId(playerId);
	    setPlayerName(playerName);
		setDisease(disease);
		setDamageGiven(damageGiven);
	}

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) { this.playerId = playerId; }

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
