package com.kingoftheill.app1.presentation.ui.activities;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.Battle;
import com.kingoftheill.app1.domain2.PlayerFC;

import java.util.Timer;
import java.util.TimerTask;

public class BattleActivity extends AppCompatActivity {

    private static final String TAG = "BattleActivity";

    private ProgressBar pb;
    private int battleValue;
    private int progressValue;
    private TextView tField;
    private ImageButton but1;
    private Button but2;

    // Firebase instance variables
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;

    private static DocumentReference PLAYER;
    private static DocumentReference ENIMIE;
    private static DocumentReference BATTLE;

    private Battle battle;

    private boolean flag = false;
    private boolean flag2 = true;

    private boolean attacker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        Log.d(TAG, "onCreate: Starting.");

        but1 = findViewById(R.id.butt);
        pb = findViewById(R.id.barra);
        tField = findViewById(R.id.mTextField);

        ProgressBar loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        but1.setEnabled(false);

        //ShimmerFrameLayout container = findViewById(R.id.shimmer_view_container1);
        //cont.startShimmerAnimation();
        //container.startShimmerAnimation();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUsername = mFirebaseUser.getUid();

        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
        ENIMIE = mFirebaseFirestore.document("Users/" + getIntent().getStringExtra("ref"));

        if (getIntent().getExtras().getBoolean("defender")) {
            attacker = false;
            BATTLE = mFirebaseFirestore.document("Battles/" + getIntent().getExtras().get("ref"));
            PLAYER.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    PlayerFC p = documentSnapshot.toObject(PlayerFC.class);
                    battleValue = p.getBtDefense();
                }
            });
        }
        else {
            battle = new Battle(PLAYER, ENIMIE);
            attacker = true;
            PLAYER.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    PlayerFC p = documentSnapshot.toObject(PlayerFC.class);
                    battleValue = p.getBtAttack();
                }
            });
        }

        if (attacker) {
            // SET BATTLE IN FIRESTORE
            mFirebaseFirestore.collection("Battles").add(battle).addOnSuccessListener(documentReference -> {
                loading.setVisibility(View.GONE);
                //but1.setEnabled(true);
                BATTLE = documentReference;

                Log.w(TAG, "Battle ID: " + BATTLE.getId());

                // GET BATTLE PROGRESS
                documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
                    if (documentSnapshot.exists()) {
                        //battle = documentSnapshot.toObject(Battle.class);
                        //Map<String, Object> ad = documentSnapshot.getData();
                        //pb.setProgress(battle.getValue());
                        progressValue =  documentSnapshot.getLong("value").intValue();
                        pb.setProgress(progressValue);

                        if (documentSnapshot.getBoolean("defenderOnline") && !flag) {
                            loading.setVisibility(View.INVISIBLE);
                            but1.setEnabled(true);
                            flag = true;
                            new CounterTask().execute();
                        }
                    } else
                        Log.e(TAG, "Error on battle details" + e.getMessage());
                });
        });}

        else {
            // GET BATTLE PROGRESS
            Log.w(TAG, "Battle ID: " + BATTLE.getId());
            BATTLE.addSnapshotListener(this, (documentSnapshot, e) -> {
                if (documentSnapshot.exists()) {

                    progressValue = documentSnapshot.getLong("value").intValue();
                    pb.setProgress(progressValue);

                    if (!flag) {
                        loading.setVisibility(View.INVISIBLE);
                        but1.setEnabled(true);
                        flag = true;
                        BATTLE.update("defenderOnline", true);
                        new CounterTask().execute();
                    }
                } else
                    Log.e(TAG, "Error on battle details" + e.getMessage());
            });
        }

        // UPDATE BATTLE
        but1.setOnClickListener(view -> {
            if (attacker) {
                if (progressValue + battleValue > 100)
                    BATTLE.update("value", 100);

                else
                    BATTLE.update("value", (progressValue + battleValue))
                            .addOnFailureListener(e -> Log.e(TAG, e.getMessage()));

                Log.w(TAG, "Battle value attack: " + (battleValue));
            }
            else  {
                if (progressValue - battleValue < 0)
                    BATTLE.update("value", 0);
                else
                    BATTLE.update("value", (progressValue - battleValue));
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
            tField.setText(vals[0]+"");
        }

        protected void onPostExecute(Void result) {
            but1.setEnabled(false);

            if (attacker) {
                if (battleValue > 50)
                    tField.setText("Victory!");
                else
                    if (battleValue < 50)
                        tField.setText("Defeat!");
                    else
                        tField.setText("Tie!");
            }

            else {
                if (battleValue < 50)
                    tField.setText("Victory!");
                else if (battleValue > 50)
                    tField.setText("Defeat!");
                else
                    tField.setText("Tie!");
            }

            new Timer().schedule(new TimerTask() {
                public void run() {
                    BattleActivity.this.runOnUiThread(() -> {
                        Intent intent = new Intent(BattleActivity.this, UpgradesActivity.class);
                        startActivity(intent);
                    });
                }
            }, 4000);
        }
    }

}
