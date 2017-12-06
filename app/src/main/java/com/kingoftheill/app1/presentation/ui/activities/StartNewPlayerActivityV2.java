package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.storage.PlayerViewModel;

/**
 * Created by Andrade on 30/11/2017.
 */

public class StartNewPlayerActivityV2 extends AppCompatActivity {

    private static final String TAG = "StartNewPlayerActivity";
    static final int RC_SIGN_IN = 1;  // The request code

    private PlayerViewModel playerViewModel;

    private SignInButton sign_in;

    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerv2);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sign_in = (SignInButton) findViewById(R.id.sign_in_button);
        sign_in.setVisibility(View.GONE);
        sign_in.setOnClickListener((view) -> {
            signIn();
        });

        /*resg = (Button) findViewById(R.id.register);
        resg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = (EditText) findViewById(R.id.user);
                pw = (EditText) findViewById(R.id.pass);
                email = (EditText) findViewById(R.id.w_email);
                buttonpress();
            }
        });*/

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            sign_in.setVisibility(View.VISIBLE);
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void buttonpress() {
        //playerViewModel.startPlayer(user.getText().toString(), "bubonic_plague_doc_icon_3");
        //playerViewModel.delete();
        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show();
        Intent inten = new Intent(this, PickDiseaseActivityV2.class);
        startActivity(inten);
    }
}
