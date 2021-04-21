package com.example.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsView;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecyclerView();

        generateMoney();
    }

        private void generateMoney() {
            List<Item> moneyItems = new ArrayList<>();
            moneyItems.add(new Item("PS5", "45000"));
            moneyItems.add(new Item("salary","300000"));
            moneyItems.add(new Item("food", "3000"));
            moneyItems.add(new Item("car", "2000"));
            moneyItems.add(new Item("PS5", "45000"));
            moneyItems.add(new Item("salary","300000"));
            moneyItems.add(new Item("food", "3000"));
            moneyItems.add(new Item("car", "2000"));
            moneyItems.add(new Item("PS5", "45000"));
            moneyItems.add(new Item("salary","300000"));
            moneyItems.add(new Item("food", "3000"));
            moneyItems.add(new Item("car", "2000"));

            itemsAdapter.setItems(moneyItems);
        }

        private void configureRecyclerView() {
            itemsView = findViewById(R.id.rv_items);
            itemsView.setAdapter(itemsAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);

            itemsView.setLayoutManager(layoutManager);
    }
}