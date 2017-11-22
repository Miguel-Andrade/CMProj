package com.kodelabs.boilerplate.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kodelabs.boilerplate.R;
import com.kodelabs.boilerplate.domain.executor.impl.ThreadExecutor;
import com.kodelabs.boilerplate.presentation.presenters.BattleResultPresenter;
import com.kodelabs.boilerplate.presentation.presenters.impl.BattleResultPresenterImpl;
import com.kodelabs.boilerplate.storage.AppRepositoryImpl;
import com.kodelabs.boilerplate.threading.MainThreadImpl;

import java.util.Timer;
import java.util.TimerTask;

public class BattleActivity extends AppCompatActivity
        implements BattleResultPresenter.View {

    private static final String TAG = "BattleActivity";

    private BattleResultPresenter mPresenter;

    private ProgressBar pb;
    private int value;
    private TextView tField;
    private ImageButton but1;
    private Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Starting.");

        mPresenter = new BattleResultPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, AppRepositoryImpl.getInstance());

        setContentView(R.layout.battle);
        pb = (ProgressBar)findViewById(R.id.barra);
        value = 70;
        pb.setProgress(value);

        but1 = (ImageButton)findViewById(R.id.butt);
        //but2 = (Button)findViewById(R.id.butt2);
        addButtonClickListener();
        //addButtonClickListener2();

        tField = (TextView)findViewById(R.id.mTextField);
        //60000 = 1min 1000 = 1s
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tField.setText(millisUntilFinished / 1000 + "");
            }

            public void onFinish() {
                but1.setEnabled(false);

                if(value > 50) {
                    tField.setText("Victory!");
                    mPresenter.battleResult("winner");
                }
                if(value < 50) {
                    tField.setText("Defeat!");
                    mPresenter.battleResult("looser");
                }
                if(value == 50) {
                    tField.setText("Tie!");
                    mPresenter.battleResult("tie");
                }

                new Timer().schedule(new TimerTask(){
                    public void run() {
                        BattleActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(BattleActivity.this, UpgradesActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }, 4000);


            }
        }.start();

    }

    private void addButtonClickListener() {
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(value < 100) {
                    value += 1;
                    pb.setProgress(value);
                }
            }
        });
    }

    @Override
    public void onResults(int[] result) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
