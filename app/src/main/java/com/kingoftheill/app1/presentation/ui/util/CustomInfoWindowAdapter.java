package com.kingoftheill.app1.presentation.ui.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.kingoftheill.app1.R;

import java.util.HashMap;


public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private View mWindow;
    private Context mContext;
    private boolean mUser;
    private String image;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
    }

    private void rendowWindowText(Marker marker, View view){

        if (mUser) {
            String title = marker.getTitle();
            TextView tvTitle = view.findViewById(R.id.infoPName);

            if (!title.equals("")) {
                tvTitle.setText(title);
            }
        }
        else {
            TextView tvTitle = view.findViewById(R.id.item_name);
            tvTitle.setText(marker.getTitle());

            view.findViewById(R.id.item_image).setBackgroundResource(mContext.getResources()
                    .getIdentifier(image, "drawable", mContext.getPackageName()));
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        HashMap<String, Object> temp = (HashMap<String, Object>) marker.getTag();
        image = (String) temp.get("image");
        boolean user = (boolean) temp.get("user");
        if (user) {
            mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_infowindow_player, null);
            mUser = user;
        }
        else {
            mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_infowindow_item, null);
            mUser = false;
        }
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
