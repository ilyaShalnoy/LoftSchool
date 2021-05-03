package com.example.loftmoney.screens;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loftmoney.R;
import com.example.loftmoney.cell.ItemModel;
import com.example.loftmoney.remote.MoneyApi;
import com.example.loftmoney.remote.MoneyRemoteItem;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<ItemModel>> moneyItemsList;
    public MutableLiveData<String> messageString;
    public MutableLiveData<Integer> messageInt;

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadIncomes(MoneyApi moneyApi) {
        compositeDisposable.add(moneyApi.getMoneyItems("income")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    if (moneyResponse.getStatus().equals("success")) {
                        List<ItemModel> moneyItemModels = new ArrayList<>();

                        /*for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
                            moneyItemModels.add(ItemModel.getInstance(moneyRemoteItem));
                        }*/

                        moneyItemsList.postValue(moneyItemModels);
                    } else {
                        messageInt.postValue(R.string.connection_lost);

                    }

                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }

}
