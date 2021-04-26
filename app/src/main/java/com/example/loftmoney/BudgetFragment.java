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

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 0;
    public static final String ARG_POSITION = "position";

    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();
    private List<ItemModel> moneyItemModels = new ArrayList<>();
    private int currentPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);
        recyclerView = view.findViewById(R.id.recycler);
        btnAdd = view.findViewById(R.id.add_new_expense);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddItemActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        //TODO "Продумать"
//        itemsAdapter.setData(moneyItemModels);
        recyclerView.setAdapter(itemsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String nameAdd = data.getStringExtra("name");
        String priceAdd = data.getStringExtra("price");

        moneyItemModels.add(new ItemModel(nameAdd, priceAdd, currentPosition));
        itemsAdapter.setData(moneyItemModels);
    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }
}
