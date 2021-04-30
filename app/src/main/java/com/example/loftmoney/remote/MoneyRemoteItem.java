package com.example.loftmoney.remote;

import com.google.gson.annotations.SerializedName;

public class MoneyRemoteItem {

    @SerializedName("id")
    private String itemId;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;


    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
