package com.example.loftmoney;

import androidx.recyclerview.widget.RecyclerView;

public class ItemModel {
    private String name;
    private String price;
    private int position;

    public ItemModel(String name, String price, int position) {
        this.name = name;
        this.price = price;
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getPosition() { return position; }

}



