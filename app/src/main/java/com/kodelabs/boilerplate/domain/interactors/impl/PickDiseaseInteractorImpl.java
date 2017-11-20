package com.kodelabs.boilerplate.domain.interactors.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.PickDiseaseInteractor;
import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.repository.AppRepository;

/**
 * Created by Andrade on 20/11/2017.
 */

public class PickDiseaseInteractorImpl extends AbstractInteractor implements PickDiseaseInteractor {

    private PickDiseaseInteractor.Callback mCallback;
    private AppRepository mRepository;
    private Player mPlayer;

    public PickDiseaseInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     PickDiseaseInteractor.Callback callback, AppRepository repository,
                                        String userName, String email, String pw, String foto) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mUserName = userName;
        mEmail = email;
        mPw = pw;
        mFoto = foto;
    }

    @Override
    public void run() {

        mPlayer = new Player(mUserName, mFoto);

        mRepository.insertPlayer(mPlayer);

        // notify on the main thread that we have inserted this item
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onStartedPlayer();
            }
        });
    }
}
