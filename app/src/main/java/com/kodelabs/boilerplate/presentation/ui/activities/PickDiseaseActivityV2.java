package com.kodelabs.boilerplate.presentation.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
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
import com.kodelabs.boilerplate.presentation.ui.util.SectionsPageAdapter;
import com.kodelabs.boilerplate.presentation.ui.util.Tab1Fragment;
import com.kodelabs.boilerplate.presentation.ui.util.Tab2Fragment;
import com.kodelabs.boilerplate.presentation.ui.util.Tab3Fragment;
import com.kodelabs.boilerplate.storage.PlayerViewModel;

/**
 * Created by Andrade on 01/12/2017.
 */

public class PickDiseaseActivityV2 extends AppCompatActivity {

    private static final String TAG = "PickDiseaseActivityV2";

    private PlayerViewModel playerViewModel;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_disease);

        Log.d(TAG, "onCreate: Starting.");

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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

    public void onbuttonpressed() {
        playerViewModel.startDisease(mViewPager.getCurrentItem());
        Toast.makeText(this, "Picked!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

}
