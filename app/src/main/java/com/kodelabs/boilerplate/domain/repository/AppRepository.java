package com.kodelabs.boilerplate.domain.repository;

import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;

/**
 * Created by Andrade on 19/11/2017.
 */

public interface AppRepository {

    void insertPlayer (Player player);

    void insertDisease (Player player, Disease disease);

    Player getPlayer ();

    Disease getDisease ();
}
