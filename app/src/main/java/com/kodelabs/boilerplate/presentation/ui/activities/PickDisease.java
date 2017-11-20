package com.kodelabs.boilerplate.presentation.ui.activities;

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

public class PickDisease extends AppCompatActivity {

    private static final String TAG = "PickDiseaseImpl";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TESTING BUTTON CLICK 2", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "TAB1");
        adapter.addFragment(new Tab2Fragment(), "TAB2");
        adapter.addFragment(new Tab3Fragment(), "TAB3");
        viewPager.setAdapter(adapter);
    }

}
