package com.kingoftheill.app1.presentation.ui.util;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kingoftheill.app1.R;

public class SmallpoxPick extends AppCompatActivity {

    TextView tx;
    TextView tx2;
    TextView tx3;
    TextView tx4;
    TextView tx5;
    TextView tx6;
    TextView tx7;
    TextView tx8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smallpox_pick);

        tx = (TextView)findViewById(R.id.button);
        tx2 = (TextView)findViewById(R.id.disease_name);
        tx3 = (TextView)findViewById(R.id.desc);
        tx4 = (TextView)findViewById(R.id.desc2);
        tx5 = (TextView)findViewById(R.id.desc3);
        tx6 = (TextView)findViewById(R.id.desc4);
        tx7 = (TextView)findViewById(R.id.characteristics);
        tx8 = (TextView)findViewById(R.id.characs);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/BN-NoFear.ttf");
        tx.setTypeface(custom_font);
        tx2.setTypeface(custom_font);
        tx3.setTypeface(custom_font);
        tx4.setTypeface(custom_font);
        tx5.setTypeface(custom_font);
        tx6.setTypeface(custom_font);
        tx7.setTypeface(custom_font);
        tx8.setTypeface(custom_font);

        ImageButton bubonic = (ImageButton)findViewById(R.id.imageButton);
        ImageButton influenza = (ImageButton)findViewById(R.id.imageButton2);
        bubonic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent openActivity= new Intent(SmallpoxPick.this, BubonicPick.class);
                openActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openActivity, 0);
            }
        });
        influenza.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent openActivity= new Intent(SmallpoxPick.this, InfluenzaPick.class);
                openActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openActivity, 0);
            }
        });

    }
}