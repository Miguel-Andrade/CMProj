package com.kodelabs.boilerplate.domain.interactors.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.PickDiseaseInteractor;
import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.repository.AppRepository;

/**
 * Created by Andrade on 20/11/2017.
 */

public class PickDiseaseInteractorImpl extends AbstractInteractor implements PickDiseaseInteractor {

    private PickDiseaseInteractor.Callback mCallback;
    private AppRepository mRepository;
    private Player mPlayer;
    private int mDisease;

    public PickDiseaseInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     PickDiseaseInteractor.Callback callback, AppRepository repository,
                                     int disease) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mDisease = disease;
    }

    @Override
    public void run() {

        mPlayer = mRepository.getCurrentPlayer();
        mPlayer.setDisease(new Disease(mDisease));

        mRepository.updatePlayer(mPlayer);

        // notify on the main thread that we have inserted this item
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPickedDisease();
            }
        });
    }
}
