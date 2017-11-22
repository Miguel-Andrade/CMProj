package com.kodelabs.boilerplate.storage;

import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.repository.AppRepository;

/**
 * Created by Andrade on 19/11/2017.
 */

public class AppRepositoryImpl implements AppRepository{

    private static AppRepository appRepository;
    private Player player;
    private Player attacker;
    private Disease disease;

    public AppRepositoryImpl () {

    }

    public static AppRepository getInstance() {
        if (appRepository == null)
            appRepository  = new AppRepositoryImpl();

        return appRepository;

    }

    @Override
    public void insertPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void insertAttacker(Player attacker) { this.attacker = attacker; }

    @Override
    public void updatePlayer(Player player) { this.player = player; }

    @Override
    public void insertDisease(Player player, Disease disease) {

    }

    @Override
    public Player getPlayer(int playerId) {
        if (playerId == 0)
            return player;
        else
            return attacker;
    }

    @Override
    public Player getCurrentPlayer() {
        return player;
    }

    @Override
    public Player getAttacker () { return attacker; }

    @Override
    public Disease getDisease(int playerId) { return disease; }
}
