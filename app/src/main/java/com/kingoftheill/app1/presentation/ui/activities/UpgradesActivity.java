package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain.model.utilities.PlayersXPLevels;
import com.kingoftheill.app1.domain2.PlayerFC;

public class UpgradesActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "UpgradesActivity";

    // Firebase instance variables
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;

    private static DocumentReference PLAYER;

    private PlayerFC playerFC;

    private TextView deaths, infected, recoveries, hp, range, damage, resistence, defense, playerName,
            playerLevel, diseaseLevel, attack, numUpgrades;
    TextView killed;
    private int nKills, nDeaths, nInfected, nRecoveries, nHp, nRange, nDamage, nResistence, nDefense, nPLvl, nDLvl;
    private Button b1;
    private ProgressBar pbPLVL, pbDLVL;
    private int upgrades;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_upgrades);

        pb = findViewById(R.id.loading);
        pb.setVisibility(View.VISIBLE);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUsername = mFirebaseUser.getUid();

        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);

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
        attack = findViewById(R.id.attack);
        defense = findViewById(R.id.defense);
        numUpgrades = findViewById(R.id.numUpgrades);

        //UPDATE THE PLAYER
        PLAYER.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (documentSnapshot.exists()) {
                playerFC = documentSnapshot.toObject(PlayerFC.class);
                playerName.setText(playerFC.getName());
                pbPLVL.setProgress(playerFC.getCurrXP());
                pbDLVL.setProgress(playerFC.getDisCurrXP());
                hp.setText("HP: " + playerFC.getLife());
                diseaseLevel.setText("Disease Level: " + playerFC.getDisLevel());
                playerLevel.setText("Player Level: " + playerFC.getLevel()+"");
                range.setText("Range: " + playerFC.getDisRange());
                resistence.setText("Resistance: " + playerFC.getDisResistence());
                damage.setText("Damage: " + playerFC.getDisDamage());
                defense.setText("Defense: " + playerFC.getDisBtDefense());
                attack.setText("Attack: " + playerFC.getDisBtAttack());
                numUpgrades.setText(playerFC.getNumUpgrades() +"");
            }
        });

        winner(getIntent().getStringExtra("battleResult"));

        killed = findViewById(R.id.kills);
        nKills = 2;
        killed.setText("Kills: "+ nKills);

        deaths = findViewById(R.id.deaths);
        nDeaths = 0;
        deaths.setText("Deaths: "+ nDeaths);

        infected = findViewById(R.id.infected);
        nInfected = 8;
        infected.setText("Infected: "+ nInfected);

        recoveries = findViewById(R.id.recoveries);
        nRecoveries = 1;
        recoveries.setText("Recoveries: "+ nRecoveries);

        addTextViewClickListener();

        Button bt1 = findViewById(R.id.button1);
        bt1.setOnClickListener(this);

        /*v -> {
            Intent intent = new Intent(UpgradesActivity.this, MapActivity.class);
            startActivity(intent);
        });*/

    }

    @Override
    public void onClick(View v) {

        /*if (v.)

        switch (v.getId()) {

            case R.id.oneButton:
                // do your code
                break;

            case R.id.twoButton:
                // do your code
                break;

            case R.id.threeButton:
                // do your code
                break;

            default:
                break;
        }
*/
    }

    private void addTextViewClickListener() {
        hp.setOnClickListener(view -> {
            if(upgrades > 0) {
                nHp += 10;
                hp.setText("HP: " + nHp);
                upgrades-=1;
                PLAYER.update("life", playerFC.getLife()+1)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(this, "Stat Upgraded", Toast.LENGTH_SHORT))
                        .addOnFailureListener(e -> {
                                Toast.makeText(this, "Stat Upgraded", Toast.LENGTH_SHORT);
                                Log.e(TAG, e.getMessage());
                                });
            }
        });
        range.setOnClickListener(view -> {
            if(upgrades > 0) {
                nRange += 2;
                range.setText("Range: " + nRange);
                upgrades-=1;
            }
        });
        damage.setOnClickListener(view -> {
            if(upgrades > 0) {
                nDamage += 2;
                damage.setText("Damage: " + nDamage);
                upgrades-=1;
            }
        });
        resistence.setOnClickListener(view -> {
            if(upgrades > 0) {
                nResistence += 2;
                resistence.setText("Resistence: " + nResistence);
                upgrades-=1;
            }
        });
        attack.setOnClickListener(view -> {
            if(upgrades > 0) {
                //nAttack += 2;
                //attack.setText("Attack: " + nAttack);
                upgrades-=1;
            }
        });
        defense.setOnClickListener(view -> {
            if(upgrades > 0) {
                nDefense += 2;
                defense.setText("Defense: " + nDefense);
                upgrades-=1;
            }
        });

    }

    public void winner(String status) {
        int[] result = new int[2];
        result[0] = 0;
        if (name.equals(attacker.getName()) && status.equals("winner")) {
            winner = attacker;
            looser = defender;
            result[0] = 10 + looser.getLevel();

        } else if (name.equals(defender.getName()) && status.equals("winner")){
            winner = defender;
            looser = attacker;
            result[0] = 10 + looser.getLevel();

        } else {
            result[0] = 10;
            result[1] = 0;
            return result;
        }

        winner.setCurrXP(result[0]);
        PlayersXPLevels plxp = PlayersXPLevels.valueOf("LEVEL_" + playerFC.getLevel());
        while (playerFC.getCurrXP() >= plxp.highBound()) {
            winner.levelUp();

            winner.getDisease().setCurrXP(result[0]);
            Disease dis = winner.getDisease();
            result[1] = 0;
            while (dis.getCurrXP() >= (10*dis.getLevel())) {
                int xpNextLevel = dis.getCurrXP() - (10*dis.getLevel());
                dis.setCurrXPZero();
                dis.levelUp();
                dis.setCurrXP(xpNextLevel);
                result[1]++;
            }
        }
        looser.setInfected(new Infection(winner.getName(), winner.getDisease().getName(), winner.getTotalDamage()));
        return result;
    }
}
