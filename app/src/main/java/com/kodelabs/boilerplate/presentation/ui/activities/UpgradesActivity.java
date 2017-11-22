package com.kodelabs.boilerplate.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kodelabs.boilerplate.R;
import com.kodelabs.boilerplate.domain.model.Player;
import com.kodelabs.boilerplate.domain.repository.AppRepository;
import com.kodelabs.boilerplate.storage.AppRepositoryImpl;

public class UpgradesActivity extends AppCompatActivity{

    private TextView deaths, infected, recoveries, hp, range, damage, resistence, attack, defense, playerName, playerLevel, diseaseLevel;
    TextView killed;
    private int nKills, nDeaths, nInfected, nRecoveries, nHp, nRange, nDamage, nResistence, nAttack, nDefense, nPLvl, nDLvl;
    private int numUpgrades;
    private String pName;
    private RelativeLayout rl;
    private ProgressBar pbPLVL, pbDLVL;
    private AppRepository mRepository = AppRepositoryImpl.getInstance();

    private Player mPlayer = mRepository.getCurrentPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_upgrades);

        pbPLVL = (ProgressBar) findViewById(R.id.pbPLVL);
        pbPLVL.setProgress(mPlayer.getCurrXP());

        pbDLVL = (ProgressBar) findViewById(R.id.pbDLVL);
        pbDLVL.setProgress(mPlayer.getDisease().getCurrXP());

        playerName = (TextView) findViewById(R.id.playerName);
        pName = mPlayer.getName();
        //playerName.setTextColor(Color.parseColor("#EFEFEF"));
        playerName.setText(pName);

        playerLevel = (TextView) findViewById(R.id.Plevel);
        nPLvl = mPlayer.getLevel();
        playerLevel.setText("Player Lvl: "+nPLvl);

        diseaseLevel = (TextView) findViewById(R.id.Dlevel);
        nDLvl = mPlayer.getDisease().getLevel();
        diseaseLevel.setText("Disease Lvl: "+nDLvl);

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

        attack = (TextView) findViewById(R.id.attack);
        nAttack = 8;
        attack.setText("Attack: "+nAttack);

        defense = (TextView) findViewById(R.id.defense);
        nDefense = 2;
        defense.setText("Defense: "+ nDefense);

        rl = (RelativeLayout)findViewById(R.id.relativeLayoutID);


        addTextViewClickListener();
        numUpgrades = mPlayer.getDisease().getNumUpgrades();
        //numUpgrades = 3;

    }

    private void addTextViewClickListener() {
        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numUpgrades > 0) {
                    nHp += 10;
                    hp.setText("HP: " + nHp);
                    numUpgrades-=1;
                }
            }
        });
        range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numUpgrades > 0) {
                    nRange += 2;
                    range.setText("Range: " + nRange);
                    numUpgrades-=1;
                }
            }
        });
        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numUpgrades > 0) {
                    nDamage += 2;
                    damage.setText("Damage: " + nDamage);
                    numUpgrades-=1;
                }
            }
        });
        resistence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numUpgrades > 0) {
                    nResistence += 2;
                    resistence.setText("Resistence: " + nResistence);
                    numUpgrades-=1;
                }
            }
        });
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numUpgrades > 0) {
                    nAttack += 2;
                    attack.setText("Attack: " + nAttack);
                    numUpgrades-=1;
                }
            }
        });
        defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numUpgrades > 0) {
                    nDefense += 2;
                    defense.setText("Defense: " + nDefense);
                    numUpgrades-=1;
                }
            }
        });

    }
}
