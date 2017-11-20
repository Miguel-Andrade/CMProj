package com.kodelabs.boilerplate.domain.interactors;

import com.kodelabs.boilerplate.domain.interactors.base.Interactor;

/**
 * Created by Andrade on 20/11/2017.
 */

public interface PickDiseaseInteractor extends Interactor {

    interface Callback {
        void onStartedPlayer();
    }
}
