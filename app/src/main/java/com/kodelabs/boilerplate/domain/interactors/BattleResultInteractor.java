package com.kodelabs.boilerplate.domain.interactors;

import com.kodelabs.boilerplate.domain.interactors.base.Interactor;

/**
 * Created by Andrade on 22/11/2017.
 */

public interface BattleResultInteractor extends Interactor{

    interface Callback {
        void onResults(int[] result);
    }
}
