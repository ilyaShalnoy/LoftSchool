package com.example.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText PriceEditText;
    private Button addButton;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onResume() {
        super.onResume();
        configureButton();
    }


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        NameEditText = (EditText) findViewById(R.id.et_name);
        PriceEditText = (EditText) findViewById(R.id.et_price);
        addButton =(Button) findViewById(R.id.btn_add);

        configureButton();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }


    private void configureButton() {
        addButton.setOnClickListener(v -> {
            if (NameEditText.getText().equals("") || PriceEditText.getText().equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.fill_fields), Toast.LENGTH_LONG);
                return;
            } else {
                Intent intent = new Intent();
                intent.putExtra("name", NameEditText.getText().toString());
                intent.putExtra("price", PriceEditText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();

                Disposable disposable = ((LoftApp) getApplication()).moneyApi.postMoney(
                        Integer.parseInt(PriceEditText.getText().toString()),
                        NameEditText.getText().toString(),
                        "income")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Toast.makeText(getApplicationContext(), getString(R.string.success_added), Toast.LENGTH_LONG).show();
                            finish();
                        }, throwable -> Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

}
