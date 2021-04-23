package com.example.loftmoney;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;
    private RecyclerView itemsView;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    private List<Item> moneyItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecyclerView();
        generateMoney(moneyItems);

        btnAdd = findViewById(R.id.addNewExpense);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivityForResult(intent, 0);
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            String nameAdd = data.getStringExtra("name");
            String priceAdd = data.getStringExtra("price");

            moneyItems.add(new Item(nameAdd, priceAdd));
            itemsAdapter.setData(moneyItems);
        }

        private void generateMoney(List<Item> moneyItems) {
            itemsAdapter.setData(moneyItems);
        }

        private void configureRecyclerView() {
            itemsView = findViewById(R.id.rv_items);
            itemsView.setAdapter(itemsAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);
            itemsView.setLayoutManager(layoutManager);


            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                    (itemsView.getContext(),
                    layoutManager.getOrientation());
           itemsView.addItemDecoration(dividerItemDecoration);
    }
}