package com.kodelabs.boilerplate.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kodelabs.boilerplate.R;
import com.kodelabs.boilerplate.storage.AppRepositoryImpl;
import com.kodelabs.boilerplate.domain.executor.impl.ThreadExecutor;
import com.kodelabs.boilerplate.presentation.presenters.PickDiseasePresenter;
import com.kodelabs.boilerplate.presentation.presenters.impl.PickDiseasePresenterImpl;
import com.kodelabs.boilerplate.presentation.ui.util.SectionsPageAdapter;
import com.kodelabs.boilerplate.presentation.ui.util.Tab1Fragment;
import com.kodelabs.boilerplate.presentation.ui.util.Tab2Fragment;
import com.kodelabs.boilerplate.presentation.ui.util.Tab3Fragment;
import com.kodelabs.boilerplate.threading.MainThreadImpl;

public class PickDiseaseActivity extends AppCompatActivity
        implements PickDiseasePresenterImpl.View{

    private static final String TAG = "PickDiseaseAct";

    private PickDiseasePresenter mPresenter;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mPresenter = new PickDiseasePresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, AppRepositoryImpl.getInstance());


        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onbuttonpressed();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Influenza");
        adapter.addFragment(new Tab2Fragment(), "Bubonic Plague");
        adapter.addFragment(new Tab3Fragment(), "SmallPox");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPickedDisease() {
        Toast.makeText(this, "Picked!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void onbuttonpressed() {
        mPresenter.pickDisease(mViewPager.getCurrentItem());
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
