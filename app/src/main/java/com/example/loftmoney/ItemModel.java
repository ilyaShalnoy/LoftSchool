package com.example.loftmoney;

import androidx.recyclerview.widget.RecyclerView;

public class ItemModel {
    private String name;
    private String price;



    public ItemModel(String name, String price) {
        this.name = name;
        this.price = price;
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

}



