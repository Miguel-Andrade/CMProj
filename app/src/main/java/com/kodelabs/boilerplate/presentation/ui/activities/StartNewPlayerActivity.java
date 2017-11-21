package com.kodelabs.boilerplate.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kodelabs.boilerplate.R;
import com.kodelabs.boilerplate.storage.AppRepositoryImpl;
import com.kodelabs.boilerplate.domain.executor.impl.ThreadExecutor;
import com.kodelabs.boilerplate.presentation.presenters.StartNewPlayerPresenter;
import com.kodelabs.boilerplate.presentation.presenters.impl.StartNewPlayerPresenterImpl;
import com.kodelabs.boilerplate.threading.MainThreadImpl;

/**
 * Created by Andrade on 19/11/2017.
 */

public class StartNewPlayerActivity extends AppCompatActivity
        implements StartNewPlayerPresenter.View {

    private StartNewPlayerPresenter mPresenter;

    private EditText user;
    private EditText email;
    private EditText pw;
    private Button resg;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        resg = (Button) findViewById(R.id.register);
        resg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = (EditText) findViewById(R.id.user);
                pw = (EditText) findViewById(R.id.pass);
                email = (EditText) findViewById(R.id.w_email);
                buttonpress();
            }
        });

        mPresenter = new StartNewPlayerPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, AppRepositoryImpl.getInstance());
    }

    @Override
    public void onStartedPlayer() {
        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show();
        Intent inten = new Intent(this, PickDiseaseActivity.class);
        startActivity(inten);
    }

    public void buttonpress() {
        mPresenter.startNewPlayer(user.getText().toString(), email.getText().toString(),
                pw.getText().toString(),"bubonic_plague_doc_icon_3");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
