package com.kodelabs.boilerplate.domain.model;


public class Infecting implements Runnable {

	//Rate that life is update, in minutes
	private final static int UPDATE_RATE = 30; 
	private Infection infection;
	private Player player;
	
	public Infecting (Infection infection, Player player) {
		this.infection = infection;
		this.player = player;
	}
	
	@Override
	public void run() {
		player.reduceLife(infection.getDamageGiven()/UPDATE_RATE);

	}

}
