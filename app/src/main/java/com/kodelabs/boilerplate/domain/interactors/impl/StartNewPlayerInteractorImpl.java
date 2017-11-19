package com.kodelabs.boilerplate.domain.interactors.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.StartNewPlayerInteractor;
import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.repository.AppRepository;
import com.kodelabs.boilerplate.domain.repository.Repository;

/**
 * Created by Andrade on 19/11/2017.
 */

public class StartNewPlayerInteractorImpl extends AbstractInteractor implements StartNewPlayerInteractor {

    private StartNewPlayerInteractor.Callback mCallback;
    private AppRepository mRepository;
    private Player mPlayer;
    private String mUserName;
    private String mEmail;
    private String mPw;
    private String mFoto;

    public StartNewPlayerInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        StartNewPlayerInteractor.Callback callback, AppRepository repository,
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
