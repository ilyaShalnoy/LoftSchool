package com.example.loftmoney.screens.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loftmoney.AddItemActivity;
import com.example.loftmoney.LoftApp;
import com.example.loftmoney.R;
import com.example.loftmoney.cells.ItemModel;
import com.example.loftmoney.cells.ItemsAdapter;
import com.example.loftmoney.cells.ItemsAdapterClick;
import com.example.loftmoney.screens.balance.BalanceFragment;
import com.example.loftmoney.screens.dashboard.EditModeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BudgetFragment extends Fragment implements BudgetEditListener {


    public static final String ARG_POSITION = "position";
    private RecyclerView recyclerView;
    private final ItemsAdapter itemsAdapter = new ItemsAdapter();
    private List<ItemModel> moneyItemModels = new ArrayList<>();
    private int currentPosition;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private BudgetViewModel budgetViewModel;
    private FloatingActionButton addNewItem;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_POSITION);
        }

        itemsAdapter.setItemsAdapterClick(new ItemsAdapterClick() {
            @Override
            public void onCellClick(ItemModel itemModel) {
                if (budgetViewModel.isEditMode.getValue()) {
                    itemModel.setSelected(!itemModel.isSelected());
                    itemsAdapter.updateItem(itemModel);
                    budgetViewModel.selectItem(itemModel);
                    checkSelectedCount();
                }
            }

            @Override
            public void onLongCellClick(ItemModel itemModel) {
                if (!budgetViewModel.isEditMode.getValue())
                    itemModel.setSelected(true);
                itemsAdapter.updateItem(itemModel);
                budgetViewModel.isEditMode.postValue(true);
                budgetViewModel.selectItem(itemModel);
                checkSelectedCount();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureView(view);
        configureViewModel();
    }

    private void configureView(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setAdapter(itemsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                budgetViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi, currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        addNewItem = view.findViewById(R.id.add_new_item);
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddItemActivity.class);
                intent.putExtra(ARG_POSITION, currentPosition);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoadData();
    }

    private void onLoadData() {
        budgetViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi, currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
        itemsAdapter.clearItems();
    }

    private void configureViewModel() {
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.moneyItemsList.observe(this, itemsAdapter::setData);

        budgetViewModel.isEditMode.observe(this, isEditMode -> {

            addNewItem.setVisibility(isEditMode ? View.GONE : View.VISIBLE);

            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onEditModeChanged(isEditMode);
            }
        });

        budgetViewModel.selectedCounter.observe(this, newCount -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onCounterChanged(newCount);
            }
        });


        budgetViewModel.messageString.observe(this, message -> {   //TODO: ?
            if (!message.equals("")) {
                showToast(message);
            }
        });

        budgetViewModel.messageInt.observe(this, message -> {   //TODO: ?
            if (message > 0) {
                showToast((getString(message)));
            }
        });

        budgetViewModel.isNeedLoadData.observe(this, isNeed -> {
            if (isNeed) {
                onLoadData();
            }
        });
    }

    private void checkSelectedCount() {
        int selectedItemsCount = 0;
        for (ItemModel itemModel : itemsAdapter.getMoneyItemModelList()) {
            if (itemModel.isSelected()) {
                selectedItemsCount++;
            }
        }
        budgetViewModel.selectedCounter.postValue(selectedItemsCount);
    }

    @Override
    public void onClearEdit() {  //отключаем режим EditMode.
        budgetViewModel.isEditMode.postValue(false);
        budgetViewModel.selectedCounter.postValue(-1);

        for (ItemModel itemModel : itemsAdapter.getMoneyItemModelList()) {
            if (itemModel.isSelected()) {
                itemModel.setSelected(false);
                itemsAdapter.updateItem(itemModel);
            }
        }
    }

    @Override
    public void onClearSelectedClick() {
        budgetViewModel.isEditMode.postValue(false);  // возвращение из мода редактирования
        budgetViewModel.selectedCounter.postValue(-1);

        budgetViewModel.removeItems(((LoftApp) getActivity().getApplication()).moneyApi,
                getActivity().getSharedPreferences(getString(R.string.app_name), 0),budgetViewModel.selectedItems);

        onLoadData();
    }


    private void showToast(String message) {    //TODO: ?
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void sortItems(List<ItemModel> items) {
        Collections.sort(items, new Comparator<ItemModel>() {
            @Override
            public int compare(ItemModel o1, ItemModel o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
    }
}

