package com.kodelabs.boilerplate.presentation.presenters;

import com.kodelabs.boilerplate.presentation.presenters.base.BasePresenter;
import com.kodelabs.boilerplate.presentation.ui.BaseView;

/**
 * Created by Andrade on 20/11/2017.
 */

public interface PickDiseasePresenter extends BasePresenter {

    interface View extends BaseView {

        void onPickedDisease();
    }

    void pickDisease (int disease);
}
