package com.kingoftheill.app1.storage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

/**
 * Created by Andrade on 30/11/2017.
 */

public class PlayerViewModel extends AndroidViewModel {

    private int playerId;
    private int attackerId;
    private AppRepositoryImpl appRep;


    public PlayerViewModel (Application application) {
        super(application);
        this.appRep = AppRepositoryImpl.getInstante(getApplication().getApplicationContext());
    }

    /*public void init(String name) {
        if (this.player != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        player = appRep.playerDao.loadPlayerByName(name);
    }*/

    /*public LiveData<Player> getPlayer() {
        return this.player;
    }*/

}
