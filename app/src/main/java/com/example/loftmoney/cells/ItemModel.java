package com.example.loftmoney.cells;

public class ItemModel {
    private String id;
    private String name;
    private String price;
    private int position;
    private boolean isSelected; // для выделения ячейки

    public ItemModel(String id, String name, String price, int position) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.position = position;
        this.isSelected = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getPosition() {
        return position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}



