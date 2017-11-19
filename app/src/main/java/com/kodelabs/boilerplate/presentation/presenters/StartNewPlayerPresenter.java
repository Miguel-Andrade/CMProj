package com.kodelabs.boilerplate.presentation.presenters;

import com.kodelabs.boilerplate.presentation.presenters.base.BasePresenter;
import com.kodelabs.boilerplate.presentation.ui.BaseView;

/**
 * Created by Andrade on 19/11/2017.
 */

public interface StartNewPlayerPresenter extends BasePresenter{

    interface View extends BaseView {

        void onStartedPlayer();
    }

    void startNewPlayer (String userName, String email, String pw, String foto);
}
