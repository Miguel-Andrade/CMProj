package com.kodelabs.boilerplate.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kodelabs.boilerplate.R;

public class PlayerInfoActivity extends AppCompatActivity {

    private TextView deaths, infected, recoveries, hp, range, damage, resistence, defense;
    TextView killed;
    private int nKills, nDeaths, nInfected, nRecoveries, nHp, nRange, nDamage, nResistence, nDefense;
    private RelativeLayout rl;
    private Button b1;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(78);

        killed = (TextView) findViewById(R.id.kills);
        nKills = 2;
        killed.setText("Kills: "+ nKills);

        deaths = (TextView) findViewById(R.id.deaths);
        nDeaths = 0;
        deaths.setText("Deaths: "+ nDeaths);

        infected = (TextView) findViewById(R.id.infected);
        nInfected = 8;
        infected.setText("Infected: "+ nInfected);

        recoveries = (TextView) findViewById(R.id.recoveries);
        nRecoveries = 1;
        recoveries.setText("Recoveries: "+ nRecoveries);

        hp = (TextView) findViewById(R.id.hp);
        nHp = 100;
        hp.setText("HP: "+ nHp);

        range = (TextView) findViewById(R.id.range);
        nRange = 10;
        range.setText("Range: "+ nRange);

        damage = (TextView) findViewById(R.id.damage);
        nDamage = 15;
        damage.setText("Damage: "+ nDamage);

        resistence = (TextView) findViewById(R.id.resistence);
        nResistence = 4;
        resistence.setText("Resistence: "+ nResistence);

        defense = (TextView) findViewById(R.id.defense);
        nDefense = 2;
        defense.setText("Defense: "+ nDefense);

        rl = (RelativeLayout)findViewById(R.id.relativeLayoutID);
        b1 = (Button)findViewById(R.id.button1);

    }

    private void addButtonClickListener() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defense.setText("PILAAAASSSSSSSSSSSSSSSSSSSSss");
                rl.setBackgroundResource(R.drawable.bubonic);

            }
        });

    }

}
