package com.kodelabs.boilerplate.presentation.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kodelabs.boilerplate.R;

import java.util.Timer;
import java.util.TimerTask;

public class BattleActivity extends AppCompatActivity {

    private static final String TAG = "BattleActivity";

    private ProgressBar pb;
    private int value;
    private TextView tField;
    private ImageButton but1;
    private Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Starting.");

        setContentView(R.layout.battle);
        pb = (ProgressBar)findViewById(R.id.barra);
        value = 70;
        pb.setProgress(value);

        but1 = (ImageButton)findViewById(R.id.butt);
        //but2 = (Button)findViewById(R.id.butt2);
        addButtonClickListener();
        //addButtonClickListener2();

        tField = (TextView)findViewById(R.id.mTextField);
        new CounterTask().execute();

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

    private class CounterTask extends AsyncTask<Void, Integer, Void> {

        protected void onPreExecute() {
            tField.setText(30 + "");
        }

        protected Void doInBackground(Void... params) {
            for (int i = 30; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... vals) {
            tField.setText(vals[0].intValue());
        }

        protected void onPostExecute(Void result) {
            but1.setEnabled(false);

            if (value > 50) {
                tField.setText("Victory!");
            }
            if (value < 50) {
                tField.setText("Defeat!");
            }
            if (value == 50) {
                tField.setText("Tie!");
            }

            new Timer().schedule(new TimerTask() {
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
    }

}
