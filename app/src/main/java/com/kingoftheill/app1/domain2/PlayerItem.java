package com.kingoftheill.app1.domain2;

/**
 * Created by Andrade on 06/12/2017.
 */

public class PlayerItem {

    private String itemId;
    private int quantity;

    public PlayerItem() {
    }

    public PlayerItem(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
