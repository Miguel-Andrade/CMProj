/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerFC;
import com.kingoftheill.app1.domain2.PlayerItem;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private SignInButton mSignInButton;

    private GoogleApiClient mGoogleApiClient;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseUser mFirebaseUser;

    private EditText tx;

    private ProgressBar loading;
    private ShimmerFrameLayout s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerv2);

        // Battle Notification
        if (getIntent().getExtras() != null) {
            String p = (String) getIntent().getExtras().get("ref");
            if (p != null) {
                Log.w(TAG, "Payload: " + p);
                Intent intent = new Intent(this, BattleActivity.class);
                intent.putExtra("ref", p);
                startActivity(intent);
            }
        }

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();


        if (mFirebaseUser != null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, MapActivity.class));
            finish();
            return;
        }

        s = findViewById(R.id.s);

        // Assign fields
        mSignInButton = findViewById(R.id.sign_in_button);

        // Set click listeners
        mSignInButton.setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        tx = findViewById(R.id.name);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                //loading.setVisibility(View.VISIBLE);
                s.startShimmerAnimation();
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign-In failed
                Log.e(TAG, "Google Sign-In failed."+ result.getStatus());
                s.stopShimmerAnimation();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        WriteBatch batch = mFirebaseFirestore.batch();
                        DocumentReference reference = mFirebaseFirestore.document("Users/"+ mFirebaseUser.getUid());
                        reference.get().addOnSuccessListener(documentSnapshot -> {
                            if (!documentSnapshot.exists()) {

                                DocumentReference l = mFirebaseFirestore.document("Items/Item1");
                                String token = FirebaseInstanceId.getInstance().getToken();
                                batch.set(reference, new PlayerFC(tx.getText().toString(), "bubonic_plague_doc_icon_3", token));
                                for (int i = 0; i <= 29; i++) {
                                    if (i <= 9) {
                                        DocumentReference item = mFirebaseFirestore.document("Users/" + mFirebaseUser.getUid() + "/Items/0" + i);
                                        batch.set(item, new PlayerItem(l,0, ""));
                                    }
                                    else {
                                        DocumentReference item = mFirebaseFirestore.document("Users/" + mFirebaseUser.getUid() + "/Items/" + i);
                                        batch.set(item, new PlayerItem(l,0, ""));
                                    }
                                }
                                batch.commit()
                                        .addOnSuccessListener(accao -> {
                                            Log.d(TAG, "Player created.");
                                            startActivity(new Intent(SignInActivity.this, PickDiseaseActivityV2.class));
                                            finish();})
                                        .addOnFailureListener(accao -> {
                                            Log.d(TAG, "Player created fail.");
                                            Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT);});
                            }

                            else {
                                Toast.makeText(this, "Wellcome Back", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, MapActivity.class));
                            }
                        });
                            }
                        });

        //loading.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
