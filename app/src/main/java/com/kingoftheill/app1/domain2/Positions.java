package com.kingoftheill.app1.domain2;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

/**
 * Created by Andrade on 07/12/2017.
 */

public class Positions {

    private boolean status;

    private GeoPoint pos;

    private int type;

    public Positions() {
    }

    public Positions(boolean online, GeoPoint geoPoint, int type) {
        setStatus(online);
        setPos(geoPoint);
        setType(type);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LatLng getPos() {
        return new LatLng(pos.getLatitude(), pos.getLongitude());
    }

    public void setPos(GeoPoint pos) {
        this.pos = pos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
