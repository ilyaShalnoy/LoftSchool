package com.example.loftmoney.screens.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.loftmoney.R;
import com.example.loftmoney.screens.balance.BalanceFragment;
import com.example.loftmoney.screens.dashboard.adapter.FragmentAdapter;
import com.example.loftmoney.screens.dashboard.adapter.FragmentItem;
import com.example.loftmoney.screens.money.MoneyFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<FragmentItem> fragments = new ArrayList<>();
        fragments.add(new FragmentItem(new MoneyFragment(), getString(R.string.expenses)));
        fragments.add(new FragmentItem(new MoneyFragment(), getString(R.string.incomes)));
        fragments.add(new FragmentItem(new BalanceFragment(), getString(R.string.balance)));

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabView   = view.findViewById(R.id.tab_view);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragments, getChildFragmentManager(),0 );
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabView.setupWithViewPager(viewPager);
    }
}
