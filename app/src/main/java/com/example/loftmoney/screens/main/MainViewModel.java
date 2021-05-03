package com.example.loftmoney.screens.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loftmoney.R;
import com.example.loftmoney.cell.ItemModel;
import com.example.loftmoney.cell.ItemsAdapter;
import com.example.loftmoney.remote.MoneyApi;
import com.example.loftmoney.remote.MoneyRemoteItem;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<ItemModel>> moneyItemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadIncomes(MoneyApi moneyApi, int currentPosition) {

        String typeRequest;
        if (currentPosition == 0) {
            typeRequest = "expense";
        } else {
            typeRequest = "income";
        }

        compositeDisposable.add(moneyApi.getMoneyItems(typeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                        List<ItemModel> moneyItemModels = new ArrayList<>();

                        for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
                            moneyItemModels.add(new ItemModel(moneyRemoteItem.getName(), moneyRemoteItem.getPrice(), currentPosition));
                        }
                        moneyItemsList.postValue(moneyItemModels);
                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }
}
