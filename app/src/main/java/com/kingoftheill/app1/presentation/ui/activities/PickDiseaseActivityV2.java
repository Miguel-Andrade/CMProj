package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerFC;
import com.kingoftheill.app1.presentation.ui.util.SectionsPageAdapter;
import com.kingoftheill.app1.presentation.ui.util.Tab1Fragment;
import com.kingoftheill.app1.presentation.ui.util.Tab2Fragment;
import com.kingoftheill.app1.presentation.ui.util.Tab3Fragment;

/**
 * Created by Andrade on 01/12/2017.
 */

public class PickDiseaseActivityV2 extends AppCompatActivity {

    private static final String TAG = "PickDiseaseActivityV2";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_disease);

        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(view -> onbuttonpressed());

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Influenza");
        adapter.addFragment(new Tab2Fragment(), "Bubonic Plague");
        adapter.addFragment(new Tab3Fragment(), "SmallPox");
        viewPager.setAdapter(adapter);
    }

    public void onbuttonpressed() {
        mFirebaseFirestore.document("Users/" + mFirebaseUser.getEmail())
                .update(PlayerFC.setDis(mViewPager.getCurrentItem()));
        Toast.makeText(this, "Picked!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

}
