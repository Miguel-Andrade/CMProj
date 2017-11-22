package com.kodelabs.boilerplate.presentation.presenters;

import com.kodelabs.boilerplate.presentation.presenters.base.BasePresenter;
import com.kodelabs.boilerplate.presentation.ui.BaseView;

/**
 * Created by Andrade on 22/11/2017.
 */

public interface BattleResultPresenter extends BasePresenter {

    interface View extends BaseView {

        void onResults(int[] result);
    }

    void battleResult (String status);
}
