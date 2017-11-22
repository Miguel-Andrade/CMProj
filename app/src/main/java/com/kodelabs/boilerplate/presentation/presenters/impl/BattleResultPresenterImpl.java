package com.kodelabs.boilerplate.presentation.presenters.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.BattleResultInteractor;
import com.kodelabs.boilerplate.domain.interactors.impl.BattleResultInteractorImpl;
import com.kodelabs.boilerplate.domain.repository.AppRepository;
import com.kodelabs.boilerplate.presentation.presenters.BattleResultPresenter;
import com.kodelabs.boilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Andrade on 22/11/2017.
 */

public class BattleResultPresenterImpl extends AbstractPresenter implements BattleResultPresenter,
        BattleResultInteractor.Callback {

    private BattleResultPresenter.View mView;
    private AppRepository mRepository;

    public BattleResultPresenterImpl(Executor executor, MainThread mainThread,
                                     BattleResultPresenter.View view, AppRepository repository) {
        super(executor, mainThread);
        mView = view;
        mRepository = repository;
    }

    @Override
    public void battleResult( String status) {
        BattleResultInteractor interactor = new BattleResultInteractorImpl(mExecutor, mMainThread,
                this, mRepository, status);
        interactor.execute();
    }

    @Override
    public void onResults(int[] result) {
        mView.onResults(result);
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

