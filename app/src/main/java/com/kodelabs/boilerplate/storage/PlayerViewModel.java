package com.kodelabs.boilerplate.storage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;

import static java.lang.Math.round;

/**
 * Created by Andrade on 30/11/2017.
 */

public class PlayerViewModel extends AndroidViewModel {

    private LiveData<Player> player;
    private int playerId;
    private int attackerId;
    private AppRepositoryImpl appRep;


    public PlayerViewModel (Application application) {
        super(application);
        this.appRep = AppRepositoryImpl.getInstante(getApplication().getApplicationContext());
    }

    public LiveData<Player> getPlayer() {
        return this.player;
    }

    public void startPlayer(String name, String image) {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                appRep.insertPlayer(new Player(name, image));
                appRep.insertPlayer(new Player("attacker", image));
                player = appRep.getPlayerByName(name);
                playerId = appRep.playerDao.getPlayerIdByName(name);
                attackerId = appRep.playerDao.getPlayerIdByName("attacker");
            }
        };

        abs.execute();
    }

    public void startDisease(int disease) {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                appRep.insertDisease(new Disease(disease, playerId));
                appRep.insertDisease(new Disease(round((disease+3)%3), attackerId));
            }
        };
        abs.execute();
    }

    public void delete() {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                appRep.playerDao.nukeAll();
            }
        };
        abs.execute();
    }
}
