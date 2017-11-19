package com.kodelabs.boilerplate.Storage;

import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.repository.AppRepository;

/**
 * Created by Andrade on 19/11/2017.
 */

public class AppRepositoryImpl implements AppRepository {

    private Player player;
    private Disease disease;

    public AppRepositoryImpl () {

    }

    @Override
    public void insertPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void insertDisease(Player player, Disease disease) {

    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Disease getDisease() {
        return disease;
    }
}
