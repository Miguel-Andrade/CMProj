package com.kodelabs.boilerplate.domain.interactors;

import com.kodelabs.boilerplate.domain.interactors.base.Interactor;

/**
 * Created by Andrade on 19/11/2017.
 */

public interface StartNewPlayerInteractor extends Interactor {

    interface Callback {
        void onStartedPlayer();
    }
}
