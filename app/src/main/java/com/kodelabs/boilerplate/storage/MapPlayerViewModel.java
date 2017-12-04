package com.kodelabs.boilerplate.storage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;

/**
 * Created by Andrade on 03/12/2017.
 */

public class MapPlayerViewModel extends AndroidViewModel {

    private LiveData<Player> player;
    private AppRepositoryImpl appRep;

    public MapPlayerViewModel(Application application) {
        super(application);
        this.appRep = AppRepositoryImpl.getInstante(application);
    }

    public void init() {
        if (this.player != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        player = appRep.getPlayerByName("Me");
    }

    public void start() {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                appRep.insertPlayer(new Player("Me", "bubonic_plague_doc_icon_3"));
                appRep.insertPlayer(new Player("attacker", "logo_pic_smallpox_alce"));
                int playerId = appRep.playerDao.getPlayerIdByName("Me");
                int attackerId = appRep.playerDao.getPlayerIdByName("attacker");
                appRep.insertDisease(new Disease(1, playerId));
                appRep.insertDisease(new Disease(2, attackerId));
            }
        };
        abs.execute();
    }

    public LiveData<Player> getPlayer() {
        return this.player;
    }



}
