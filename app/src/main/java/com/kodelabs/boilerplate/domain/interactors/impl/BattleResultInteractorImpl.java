package com.kodelabs.boilerplate.domain.interactors.impl;

import com.kodelabs.boilerplate.domain.executor.Executor;
import com.kodelabs.boilerplate.domain.executor.MainThread;
import com.kodelabs.boilerplate.domain.interactors.BattleResultInteractor;
import com.kodelabs.boilerplate.domain.interactors.base.AbstractInteractor;
import com.kodelabs.boilerplate.domain.model.Disease;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.model.utilities.Infection;
import com.kodelabs.boilerplate.domain.model.utilities.PlayersXPLevels;
import com.kodelabs.boilerplate.domain.repository.AppRepository;

/**
 * Created by Andrade on 22/11/2017.
 */

public class BattleResultInteractorImpl extends AbstractInteractor implements BattleResultInteractor{

    private BattleResultInteractor.Callback mCallback;
    private AppRepository mRepository;
    private Player mPlayer;
    private Player mEnimie;
    private Player winner;
    private Player looser;
    private String mStatus;

    public BattleResultInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                      BattleResultInteractorImpl.Callback callback, AppRepository repository,
                                      String status) {

        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mStatus = status;
    }

    /*
	 * Return is an array[2] with the first position as the XP gained
	 * and with the second position with the number of disease's upgrades
	 */
    @Override
    public void run() {

        mEnimie = mRepository.getAttacker();
        mPlayer = mRepository.getCurrentPlayer();

        final int[] result = new int[2];
        result[0] = 0;
        result[1] = 0;
        if (mStatus.equals("winner")) {
            winner = mPlayer;
            looser = mEnimie;
            result[0] = 10 + looser.getLevel();

        } else if (mStatus.equals("looser")){
            winner = mEnimie;
            looser = mPlayer;
            result[0] = 10 + looser.getLevel();

        } else {
            result[0] = 10;
            result[1] = 0;
        }

        mPlayer.setCurrXP(result[0]);

        PlayersXPLevels plxp = PlayersXPLevels.valueOf("LEVEL_" + mPlayer.getLevel());
        while (mPlayer.getCurrXP() >= plxp.highBound()) {
            plxp = PlayersXPLevels.valueOf("LEVEL_" + mPlayer.getLevel());
            mPlayer.levelUp();

            mPlayer.getDisease().setCurrXP(result[0]);
            Disease dis = mPlayer.getDisease();
            while (dis.getCurrXP() >= (10*dis.getLevel())) {
                int xpNextLevel = dis.getCurrXP() - (10*dis.getLevel());
                dis.setCurrXPZero();
                dis.levelUp();
                dis.setCurrXP(xpNextLevel);
                result[1]++;
            }
        }
        winner.getDisease().setNumUpgrades(result[1]);
        looser.setInfected(new Infection(winner.getName(), winner.getDisease().getName(), winner.getTotalDamage()));

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onResults(result);
            }
        });
    }
}
