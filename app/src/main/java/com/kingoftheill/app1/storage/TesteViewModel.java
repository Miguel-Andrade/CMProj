package com.kingoftheill.app1.storage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.kingoftheill.app1.domain.interactors.base.AbstractInteractor;
import com.kingoftheill.app1.domain.model.NewDisease;
import com.kingoftheill.app1.domain.model.NewPlayer;

/**
 * Created by Andrade on 06/12/2017.
 */

public class TesteViewModel extends AndroidViewModel{

    private AppRepositoryImpl appRep;

    public TesteViewModel(Application application) {
        super(application);
        appRep = AppRepositoryImpl.getInstante(application);
    }

    public void init() {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                //appRep.newDiseaseDao.saveDisease(new NewDisease(1));
                appRep.newPlayerDao.savePlayer(new NewPlayer("Mee", "bubonic_plague_doc_icon_3"));
                NewPlayer pl = appRep.newPlayerDao.getPlayer("Mee");
                pl.setDisease(new NewDisease(1));
                appRep.newPlayerDao.updatePlayer(pl);
            }
        };
        abs.execute();

    }
}
