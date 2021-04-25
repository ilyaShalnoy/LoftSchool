package com.example.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment_Income extends Fragment {
    private FloatingActionButton btnAdd;
    private RecyclerView itemsView;
    private ItemsAdapter_Income itemsAdapter_income = new ItemsAdapter_Income();
    private List<ItemModel> moneyItemModels = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_budget, null);
        itemsView = view.findViewById(R.id.rv_items);
        btnAdd = view.findViewById(R.id.addNewExpense);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddItemActivity.class);
            startActivityForResult(intent, 0);
        });

        itemsAdapter_income.setData(moneyItemModels);

        itemsView.setAdapter(itemsAdapter_income);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        itemsView.setLayoutManager(layoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (itemsView.getContext(),
                        layoutManager.getOrientation());
        itemsView.addItemDecoration(dividerItemDecoration);




        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String nameAdd = data.getStringExtra("name");
        String priceAdd = data.getStringExtra("price" );

        moneyItemModels.add(new ItemModel(nameAdd, priceAdd));
        itemsAdapter_income.setData(moneyItemModels);
    }
}
