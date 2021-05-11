package com.example.loftmoney.screens.balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loftmoney.R;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BalanceFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BalanceView balanceView = view.findViewById(R.id.balanceView);
       Button randomView =  view.findViewById(R.id.randomButtonValue);
       randomView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               balanceView.update(new Random().nextFloat(), new Random().nextFloat() );
           }
       });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance,container,false);
    }
}
