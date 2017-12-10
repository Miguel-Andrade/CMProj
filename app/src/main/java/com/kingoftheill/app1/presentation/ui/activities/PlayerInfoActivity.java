package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerFC;

public class PlayerInfoActivity extends AppCompatActivity {

    private TextView deaths, infected, recoveries, hp, range, damage, resistence, defense, playerName, playerLevel, diseaseLevel;
    TextView killed;
    private int nKills, nDeaths, nInfected, nRecoveries, nHp, nRange, nDamage, nResistence, nDefense, nPLvl, nDLvl;
    private String pName;
    private Button b1;
    private ProgressBar pbPLVL, pbDLVL;

    // Firebase instance variables
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String mUsername;

    private static DocumentReference ATTACKER;

    private PlayerFC playerFC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_info);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUsername = mFirebaseUser.getEmail();

        ATTACKER = mFirebaseFirestore.document("Users/" + getIntent().getStringExtra("ref"));

        pbPLVL = findViewById(R.id.pbPLVL);
        playerName =  findViewById(R.id.playerName);
        playerLevel = (TextView) findViewById(R.id.Plevel);
        diseaseLevel = findViewById(R.id.Dlevel);
        hp = findViewById(R.id.hp);
        range = findViewById(R.id.range);
        pbDLVL =  findViewById(R.id.pbDLVL);
        damage = (TextView) findViewById(R.id.damage);
        resistence = (TextView) findViewById(R.id.resistence);
        defense = (TextView) findViewById(R.id.defense);

        //UPDATE THE PLAYER
        ATTACKER.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (documentSnapshot.exists()) {
                playerFC = documentSnapshot.toObject(PlayerFC.class);
                playerName.setText(playerFC.getName());
                pbPLVL.setProgress(playerFC.getCurrXP());
                pbDLVL.setProgress(playerFC.getDisCurrXP());
                hp.setText(playerFC.getLife() +"");
                diseaseLevel.setText(playerFC.getDisLevel()+"");
                playerLevel.setText(playerFC.getLevel()+"");
                range.setText(playerFC.getTotalRange()+"");
                resistence.setText(playerFC.getTotalResistance()+"");
                damage.setText(playerFC.getTotalDamage()+"");
                defense.setText(playerFC.getTotalBtDefense()+"");
            }
        });

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


        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(view -> onbuttonpressed());

    }

    private void onbuttonpressed() {
        Intent intent = new Intent(this,BattleActivity.class);
        startActivity(intent);
    }

}
