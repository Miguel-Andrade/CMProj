package com.kingoftheill.app1.domain2;

import com.google.firebase.firestore.DocumentReference;

/**
 * Created by Andrade on 06/12/2017.
 */

public class PlayerItem {

    private DocumentReference itemId;
    private int quantity;
    private String image;


    public PlayerItem() {
    }

    public PlayerItem(DocumentReference itemId, int quantity, String image) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.image = image;
    }

    public DocumentReference getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }


}
