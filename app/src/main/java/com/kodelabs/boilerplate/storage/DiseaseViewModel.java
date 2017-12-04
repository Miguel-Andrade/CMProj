package com.kodelabs.boilerplate.storage;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.kodelabs.boilerplate.domain.model.Disease;

import java.util.List;

/**
 * Created by Andrade on 02/12/2017.
 */

public class DiseaseViewModel extends ViewModel{

    private AppRepositoryImpl appRep;
    private LiveData<Disease> disease;
    private LiveData<List<Disease>> alldis;

    public void init(String userId) {
        if (this.disease != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        //disease = appRep.getUser(userId);
    }

    public DiseaseViewModel (AppRepositoryImpl appRepository) {
        this.appRep = appRepository;
    }

    public LiveData<Disease> getDisease() {
        return disease;
    }

    public LiveData<List<Disease>> ya() {
        return appRep.diseaseDao.loadAllDiseases();
    }

    public void startDisease(int id, int disease) {
        //appRep.insertPlayer(new Disease(id, disease));
        //appRep.insertPlayer(new Player("attacker", image));
        //player = appRep.getPlayerByName(name);
    }
}
