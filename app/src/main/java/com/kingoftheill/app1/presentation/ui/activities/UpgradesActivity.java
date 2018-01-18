package com.kingoftheill.app1.presentation.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain.model.utilities.PlayerLevels;
import com.kingoftheill.app1.domain.model.utilities.PlayersXPLevels;
import com.kingoftheill.app1.domain2.PlayerFC;

import java.util.Date;

public class UpgradesActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "UpgradesActivity";

    // Firebase instance variables
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;

    private static DocumentReference PLAYER;
    private static DocumentReference ENIMIE;

    private PlayerFC playerFC;

    private TextView deaths, infected, recoveries, hp, playerName,
            playerLevel, diseaseLevel,  numUpgrades;
    TextView killed;
    private Button b1, damage, resistence, defense, attack, range;
    private ProgressBar pbPLVL, pbDLVL;
    private ObjectAnimator progressAnimator;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_upgrades);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUsername = mFirebaseUser.getUid();

        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
        ENIMIE = mFirebaseFirestore.document("Users/" + getIntent().getStringExtra("enimie"));

        pbPLVL = findViewById(R.id.pbPLVL);
        playerName = findViewById(R.id.playerName);
        playerLevel = findViewById(R.id.Plevel);
        diseaseLevel = findViewById(R.id.Dlevel);
        hp = findViewById(R.id.hp);
        range = findViewById(R.id.disRange);
        pbDLVL = findViewById(R.id.pbDLVL);
        damage = findViewById(R.id.disDamage);
        resistence = findViewById(R.id.disResistence);
        defense = findViewById(R.id.defense);
        attack = findViewById(R.id.attack);
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
                killed.setText("Kills: " + playerFC.getKills());
                deaths.setText("Deaths: " + playerFC.getDeaths());
                recoveries.setText("Recoveries: " + playerFC.getRecoveries());
                infected.setText("Infected: " + playerFC.getInfected());

                if (!flag) {
                    winner(getIntent().getStringExtra("battleResult"));
                    flag = true;
                }
            }
        });
        Button bt1 = findViewById(R.id.button1);
        bt1.setOnClickListener(this);
        range.setOnClickListener(this);
        damage.setOnClickListener(this);
        resistence.setOnClickListener(this);
        attack.setOnClickListener(this);
        defense.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        WriteBatch batch = mFirebaseFirestore.batch();
        if (v.getId() == R.id.button1) {
            startActivity(new Intent(this, MapActivity.class));
        }
        else {
            if (playerFC.getNumUpgrades() > 0) {
                switch (v.getId()) {
                    case R.id.range:
                        batch.update(PLAYER, "disRange", playerFC.getDisRange() + 1);
                        break;

                    case R.id.resistence:
                        batch.update(PLAYER, "disResistence", playerFC.getDisResistence() + 1);
                        break;

                    case R.id.damage:
                        batch.update(PLAYER, "disDamage", playerFC.getDisDamage() + 1);
                        break;

                    case R.id.attack:
                        batch.update(PLAYER, "disAttack", playerFC.getDisBtAttack() + 1);
                        break;

                    case R.id.defense:
                        batch.update(PLAYER, "disDefense", playerFC.getDisBtDefense() + 1);
                        break;
                    default:
                        break;
                }
                batch.update(PLAYER, "numUpgrades", playerFC.getNumUpgrades() - 1);
                batch.commit().addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Stat Upgraded", Toast.LENGTH_SHORT))
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error on Upgrading Stat", Toast.LENGTH_SHORT);
                            Log.e(TAG, e.getMessage());
                        });
            }
        }
    }

    public void winner(String status) {
        int[] result = new int[2];
        result[0] = 0;
        WriteBatch batch = mFirebaseFirestore.batch();
        switch (status) {
            case "winner":
                result[0] = 10 + Integer.parseInt(getIntent().getStringExtra("level"));
                break;

            case "looser":
                result[0] = 5;
                break;

            default:
                result[0] = 10;
                break;
        }

        int currxp = playerFC.getCurrXP()+result[0];
        int level = playerFC.getLevel();
        int disXp = playerFC.getDisCurrXP()+result[0];
        int dislevel = playerFC.getDisLevel();
        dislevel += disXp/10;
        disXp = disXp % 10;

        int ya = PlayersXPLevels.valueOf("LEVEL_" + level).highBound();
        while (true){
            if (currxp >= ya) {
                level++;
                ya = PlayersXPLevels.valueOf("LEVEL_" + level).highBound();
            }
            else
                break;
        }

        pbPLVL.setMax(ya);
        pbDLVL.setMax(10*dislevel);
        progressAnimator = ObjectAnimator.ofInt(pbPLVL, "progress", currxp);
        progressAnimator.setDuration(500);
        progressAnimator.setInterpolator(new DecelerateInterpolator());
        progressAnimator.start();

        batch.update(PLAYER, "currXP", currxp);
        batch.update(PLAYER, "level",level);
        batch.update(PLAYER, "disCurrXP", disXp);
        batch.update(PLAYER, "disLevel", dislevel);
        batch.update(PLAYER, "numUpgrades", disXp/10);
        if (status.equals("looser") && (playerFC.getInfection1() != null || playerFC.getInfection2() != null)) {
            ENIMIE.get().addOnSuccessListener(documentSnapshot -> {
               if (documentSnapshot.exists()) {
                   PlayerFC ene = documentSnapshot.toObject(PlayerFC.class);
                   int val = 2*ene.getDisDamage()+ PlayerLevels.valueOf("LEVEL_" + ene.getLevel()).damage();
                   if (playerFC.getInfection1() == null && playerFC.getInfection2() == null) {

                       batch.update(PLAYER, PlayerFC.newInfection1(val, documentSnapshot.getId(), ene.getType()));
                       batch.update(ENIMIE, "infected",ene.getInfected()+1);
                   } else if (playerFC.getInfection1() != null && (((Long)playerFC.getInfection2().get("type")).intValue() != ene.getType())) {

                       batch.update(PLAYER, PlayerFC.newInfection1(val, documentSnapshot.getId(), ene.getType()));
                       batch.update(ENIMIE, "infected",ene.getInfected()+1);
                   } else {

                       batch.update(PLAYER, PlayerFC.newInfection2(val, documentSnapshot.getId(), ene.getType()));
                       batch.update(ENIMIE, "infected",ene.getInfected()+1);
                   }
               }
            });
        }

        //Update timestamp
        batch.update(PLAYER, "lifeCKTimestamp", new Date());
        batch.commit().addOnSuccessListener(aVoid -> {
                if (status.equals("winner"))
                    Toast.makeText(this, "You Infected Your Opponent", Toast.LENGTH_SHORT);
                else if (status.equals("looser"))
                    Toast.makeText(this, "You've Been Infected", Toast.LENGTH_SHORT);
                else
                    Toast.makeText(this, "Try Harder Next Time", Toast.LENGTH_SHORT);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error on Battle Result", Toast.LENGTH_SHORT);
                    Log.e(TAG, e.getMessage());
        });
    }
}
