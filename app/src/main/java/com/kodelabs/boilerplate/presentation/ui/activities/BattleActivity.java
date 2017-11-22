package com.kodelabs.boilerplate.presentation.ui.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kodelabs.boilerplate.R;

public class BattleActivity extends AppCompatActivity {

    private ProgressBar pb;
    private int value;
    private TextView tField;
    private ImageButton but1;
    //private Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.battle);
        pb = (ProgressBar)findViewById(R.id.barra);
        value = 50;
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
                //but2.setEnabled(false);
                if(value > 50)
                    tField.setText("Victory!");
                if(value < 50)
                    tField.setText("Defeat!");
                if(value == 50)
                    tField.setText("Tie!");
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

//    private void addButtonClickListener2() {
//        but2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(value > 0) {
//                    value -= 1;
//                    pb.setProgress(value);
//                }
//            }
//        });
//    }
}
