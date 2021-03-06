package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private int nHp, nRange, nDamage, nResistence, nDefense, nPLvl, nDLvl;
    private Button b1;
    private ProgressBar pbPLVL, pbDLVL;

    // Firebase instance variables
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String mUsername;
    private static DocumentReference PLAYER;

    private static DocumentReference ATTACKER;

    private PlayerFC playerFC;
    private PlayerFC playerME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_info);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUsername = mFirebaseUser.getUid();

        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
        /*PLAYER.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                playerME = documentSnapshot.toObject(PlayerFC.class);
            }
        });*/

        ATTACKER = mFirebaseFirestore.document("Users/" + getIntent().getStringExtra("ref"));

        pbPLVL = findViewById(R.id.pbPLVL);
        playerName = findViewById(R.id.playerName);
        playerLevel = findViewById(R.id.Plevel);
        diseaseLevel = findViewById(R.id.Dlevel);
        hp = findViewById(R.id.hp);
        range = findViewById(R.id.range);
        pbDLVL = findViewById(R.id.pbDLVL);
        damage = findViewById(R.id.damage);
        resistence = findViewById(R.id.resistence);
        defense = findViewById(R.id.defense);
        killed = findViewById(R.id.kills);
        deaths = findViewById(R.id.deaths);
        recoveries = findViewById(R.id.recoveries);
        infected = findViewById(R.id.infected);

        b1 = findViewById(R.id.infect);

        //UPDATE THE PLAYER
        ATTACKER.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (documentSnapshot.exists()) {
                playerFC = documentSnapshot.toObject(PlayerFC.class);
                playerName.setText(playerFC.getName());
                pbPLVL.setProgress(playerFC.getCurrXP());
                pbDLVL.setProgress(playerFC.getDisCurrXP());
                hp.setText("HP: "+playerFC.getLife());
                diseaseLevel.setText("DLvl "+playerFC.getDisLevel());
                playerLevel.setText("PLvl "+playerFC.getLevel());
                range.setText("Range "+(playerFC.getRange() + playerFC.getDisDamage()));
                resistence.setText("Resistance "+ (playerFC.getResistance() + playerFC.getDisResistence()));
                damage.setText("Damage " + (playerFC.getDamage() + playerFC.getDisDamage()));
                defense.setText("Defense " + playerFC.getDisBtDefense());
                killed.setText("Kills: " + playerFC.getKills());
                deaths.setText("Deaths: " + playerFC.getDeaths());
                recoveries.setText("Recoveries: " + playerFC.getRecoveries());
                infected.setText("Infected: " + playerFC.getInfected());
                if (playerFC.getType() != getIntent().getIntExtra("type", 0)) {
                if (!getIntent().getExtras().getBoolean("attack")) {
                    b1.setEnabled(false);
                    b1.setText("Out of range!!!");
                } else {
                    b1.setEnabled(true);
                    b1.setText("INFECT!!!");
                    b1.setOnClickListener(view -> onbuttonpressed());
                }
                }
            }
        });

    }

    private void onbuttonpressed() {
        Log.w("InfoPlayer", "Ref: " + getIntent().getStringExtra("ref"));
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("ref", getIntent().getStringExtra("ref"));
        intent.putExtra("defender", false);
        startActivity(intent);
    }

}
