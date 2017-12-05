package com.kodelabs.boilerplate.storage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;

/**
 * Created by Andrade on 02/12/2017.
 */

public class DiseaseViewModel extends AndroidViewModel {

    private AppRepositoryImpl appRep;
    private LiveData<Disease> disease;

    public void init(int disId) {
        if (this.disease != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        disease = appRep.diseaseDao.loadDis(disId);
    }

    public DiseaseViewModel (Application application) {
        super(application);
        this.appRep = AppRepositoryImpl.getInstante(application);
    }

    public LiveData<Disease> getDisease() {
        return disease;
    }

    public void startDisease() {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                appRep.playerDao.savePlayer(new Player("Me", "bubonic_plague_doc_icon_3"));
                appRep.playerDao.savePlayer(new Player("attacker", "logo_pic_smallpox_alce"));
                //appRep.diseaseDao.saveDisease(new Disease(1, "Me"));
                //appRep.diseaseDao.saveDisease(new Disease(2, "attacker"));


            }
        };
        abs.execute();

    }

    public void st() {
        AbstractInteractor abs = new AbstractInteractor() {
            @Override
            public void run() {
                appRep.diseaseDao.saveDisease(new Disease(1, 0));
                appRep.diseaseDao.saveDisease(new Disease(2, 1));
                appRep.playerDao.savePlayer(new Player("Me", "bubonic_plague_doc_icon_3"));
                appRep.playerDao.savePlayer(new Player("attacker", "logo_pic_smallpox_alce", 1));
                }
        };
        abs.execute();
    }
}
