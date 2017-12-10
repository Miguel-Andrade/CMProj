package com.kingoftheill.app1.domain2;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

/**
 * Created by Andrade on 10/12/2017.
 */

public class ItemPositions {

    private GeoPoint pos;

    private int itemId;

    private String itemName;

    public  ItemPositions(){}

    public ItemPositions(GeoPoint pos, int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.pos = pos;
    }

    public LatLng getPos() {
        return new LatLng(pos.getLatitude(), pos.getLongitude());
    }

    public void setPos(GeoPoint pos) {
        this.pos = pos;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
