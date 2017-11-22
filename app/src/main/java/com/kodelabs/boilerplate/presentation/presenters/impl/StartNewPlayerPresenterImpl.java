package com.kodelabs.boilerplate.presentation.presenters.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.StartNewPlayerInteractor;
import com.kodelabs.boilerplate.domain.interactors.impl.StartNewPlayerInteractorImpl;
import com.kodelabs.boilerplate.domain.repository.AppRepository;
import com.kodelabs.boilerplate.presentation.presenters.StartNewPlayerPresenter;
import com.kodelabs.boilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Andrade on 19/11/2017.
 */

public class StartNewPlayerPresenterImpl extends AbstractPresenter implements StartNewPlayerPresenter,
        StartNewPlayerInteractor.Callback {

    private StartNewPlayerPresenter.View mView;
    private AppRepository mCostRepository;

    public StartNewPlayerPresenterImpl(Executor executor, MainThread mainThread,
                                       View view, AppRepository costRepository) {
        super(executor, mainThread);
        mView = view;
        mCostRepository = costRepository;
    }

    @Override
    public void startNewPlayer(String userName, String email, String pw, String foto) {
        StartNewPlayerInteractor interactor = new StartNewPlayerInteractorImpl(mExecutor, mMainThread,
                this, mCostRepository, userName, email, pw, foto);
        interactor.execute();
    }

    @Override
    public void onStartedPlayer() {
        mView.onStartedPlayer();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
