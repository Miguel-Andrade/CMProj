package com.kingoftheill.app1.presentation.ui.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingoftheill.app1.R;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    TextView tx;
    TextView tx2;
    TextView tx3;
    TextView tx4;
    TextView tx5;
    TextView tx6;
    TextView tx7;
    TextView tx8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        return view;
    }
}
