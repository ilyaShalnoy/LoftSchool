package com.example.loftmoney.cell;

import com.example.loftmoney.remote.MoneyRemoteItem;

public class ItemModel {
    private String name;
    private String price;
    private int position;

    public ItemModel(String name, String price, int position) {
        this.name = name;
        this.price = price;
        this.position = position;
    }

    public ItemModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getPosition() { return position; }

    public static ItemModel getInstance (MoneyRemoteItem moneyRemoteItem) {
        return new ItemModel(moneyRemoteItem.getName(), moneyRemoteItem.getPrice());
    }

}



