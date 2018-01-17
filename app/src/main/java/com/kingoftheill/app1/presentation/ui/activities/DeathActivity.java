package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerFC;
import com.kingoftheill.app1.domain2.PlayerItem;

public class DeathActivity extends AppCompatActivity {

    private static final String TAG = "DeathActivity";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    private String mUsername;
    private static DocumentReference PLAYER;
    private static CollectionReference PLAYER_ITEMS;

    private Button ContinueB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mUsername = mFirebaseUser.getUid();
        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
        PLAYER_ITEMS = mFirebaseFirestore.collection("Users/" + mUsername + "/Items");

        WriteBatch batch = mFirebaseFirestore.batch();
        PLAYER.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                DocumentReference l = mFirebaseFirestore.document("Items/Item1");
                //UPDATE PLAYER AFTER DEATH
                batch.update(PLAYER, "life", 100);
                batch.update(PLAYER, "level", 1);
                batch.update(PLAYER, "currXP", 0);
                batch.update(PLAYER, "infection1", FieldValue.delete());
                batch.update(PLAYER, "infection2", FieldValue.delete());
                batch.update(PLAYER, "disCurrXP", 0);
                for (int i = 0; i <= 29; i++) {
                    if (i <= 9) {
                        DocumentReference item = mFirebaseFirestore.document("Users/" + mFirebaseUser.getUid() + "/Items/0" + i);
                        batch.set(item, new PlayerItem(l, 0, ""));
                    } else {
                        DocumentReference item = mFirebaseFirestore.document("Users/" + mFirebaseUser.getUid() + "/Items/" + i);
                        batch.set(item, new PlayerItem(l, 0, ""));
                    }
                }
                batch.commit();
            }
        });

        ContinueB = findViewById(R.id.ContinueB);
        ContinueB.setOnClickListener(view -> onbuttonpressed());
    }

    private void onbuttonpressed() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
