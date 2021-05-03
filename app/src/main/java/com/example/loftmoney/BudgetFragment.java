package com.example.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loftmoney.cell.ItemModel;
import com.example.loftmoney.cell.ItemsAdapter;
import com.example.loftmoney.remote.MoneyRemoteItem;
import com.example.loftmoney.remote.MoneyResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 0;
    public static final String ARG_POSITION = "position";

    private RecyclerView recyclerView;
    private final ItemsAdapter itemsAdapter = new ItemsAdapter();
    private List<ItemModel> moneyItemModels = new ArrayList<>();
    private int currentPosition;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SwipeRefreshLayout swipeRefreshLayout;

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

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> loadItems());

        recyclerView.setAdapter(itemsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        loadItems();

        return view;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String nameAdd = data.getStringExtra("name");
        String priceAdd = data.getStringExtra("price");

        moneyItemModels.add(new ItemModel(nameAdd, priceAdd, currentPosition));
    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadItems() {
        String typeRequest;
        if (currentPosition == 0) {
            typeRequest = "expense";
        } else {
            typeRequest = "income";
        }


        Disposable disposable = ((LoftApp) getActivity().getApplication()).moneyApi.getMoneyItems(typeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    itemsAdapter.clearItems();
                    swipeRefreshLayout.setRefreshing(false);
                    if (moneyResponse.getStatus().equals("success")) {
                        List<ItemModel> moneyItemModels = new ArrayList<>();
                        for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
                            moneyItemModels.add(new ItemModel(moneyRemoteItem.getName(), moneyRemoteItem.getPrice(), currentPosition));
                        }
                        itemsAdapter.setData(moneyItemModels);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.connection_lost), Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity().getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });


        compositeDisposable.add(disposable);
    }
}
