package com.example.loftmoney.screens.balance;

import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loftmoney.LoftApp;
import com.example.loftmoney.cell.ItemModel;
import com.example.loftmoney.remote.BalanceResponse;
import com.example.loftmoney.remote.MoneyApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BalanceViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<ItemModel>> moneyItemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isEditMode = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectedCounter = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isNeedLoadData = new MutableLiveData<>(false);
    public MutableLiveData<BalanceResponse> balanceResp = new MutableLiveData<>();


    public void loadBalance(MoneyApi moneyApi, SharedPreferences sharedPreferences) {

        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.getBalance(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balanceResponse ->
                                balanceResp.setValue(balanceResponse)
                        , throwable -> {
                            messageString.setValue(throwable.getLocalizedMessage());
                        }
                ));
    }
}
