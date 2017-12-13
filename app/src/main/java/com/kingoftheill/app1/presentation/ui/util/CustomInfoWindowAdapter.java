package com.kingoftheill.app1.presentation.ui.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.kingoftheill.app1.R;


public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_infowindow, null);
    }

    private void rendowWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.item);

        if(!title.equals("")) {
            tvTitle.setText(title);
        }

        //String snippet = marker.getSnippet();
        //TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!title.equals("")) {
            tvTitle.setText(title);
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
