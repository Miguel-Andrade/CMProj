package com.kodelabs.boilerplate.presentation.presenters.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.PickDiseaseInteractor;
import com.kodelabs.boilerplate.domain.interactors.impl.PickDiseaseInteractorImpl;
import com.kodelabs.boilerplate.domain.repository.AppRepository;
import com.kodelabs.boilerplate.presentation.presenters.PickDiseasePresenter;
import com.kodelabs.boilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Andrade on 20/11/2017.
 */

public class PickDiseasePresenterImpl extends AbstractPresenter implements PickDiseasePresenter,
        PickDiseaseInteractor.Callback{

    private PickDiseasePresenter.View mView;
    private AppRepository mRepository;

    public PickDiseasePresenterImpl(Executor executor, MainThread mainThread,
                                    PickDiseasePresenter.View view, AppRepository costRepository) {
        super(executor, mainThread);
        mView = view;
        mRepository = costRepository;
    }

    @Override
    public void pickDisease(int disease) {
        PickDiseaseInteractor interactor = new PickDiseaseInteractorImpl(mExecutor, mMainThread,
                this, mRepository, disease);
        interactor.execute();
    }

    @Override
    public void onPickedDisease() {
        mView.onPickedDisease();
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
