package com.sunmi.weighingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvFruit;
    private MyAdapter adapter;
    private List<FruitBean> list = new ArrayList<>();
    private TextView tvVersion;

    private RecyclerView rvSettleAccounts;
    private AccountsAdapter accountsAdapter;
    private List<AccountsBean> accountsList = new ArrayList<>();

    private TextView tvTotal;

    private int currPosition;
    private double total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getString(R.string.version_name, BuildConfig.VERSION_NAME));


        rvFruit = findViewById(R.id.rv_fruit);
        rvFruit.setLayoutManager(new GridLayoutManager(this, 5));
        adapter = new MyAdapter(list, this);
        rvFruit.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            currPosition = position;
            adapter.setPosition(currPosition, true);

            if (list.get(position).isWeigh()) {

            } else {

            }
        });

        rvSettleAccounts = findViewById(R.id.rv_settle_accounts);
        rvSettleAccounts.setLayoutManager(new LinearLayoutManager(this));
        accountsAdapter = new AccountsAdapter(accountsList, this);
        rvSettleAccounts.setAdapter(accountsAdapter);

        tvTotal = findViewById(R.id.tv_total);
    }

    private void initData() {
        list.add(new FruitBean(getString(R.string.apple), R.drawable.icon_apple, 1.80, true));
        list.add(new FruitBean(getString(R.string.pear), R.drawable.icon_pear, 1.75, true));
        list.add(new FruitBean(getString(R.string.persimmon), R.drawable.icon_persimmon, 2.89, true));
        list.add(new FruitBean(getString(R.string.pomegranate), R.drawable.icon_pomegranate, 0.88, false));
        list.add(new FruitBean(getString(R.string.cherry), R.drawable.icon_cherry, 1.20, false));
        list.add(new FruitBean(getString(R.string.orange), R.drawable.icon_orange, 1.20, false));
        list.add(new FruitBean(getString(R.string.banana), R.drawable.icon_banana, 1.70, false));
        list.add(new FruitBean(getString(R.string.blueberry), R.drawable.icon_blueberry, 0.99, false));
        list.add(new FruitBean(getString(R.string.pitaya), R.drawable.icon_pitaya, 0.89, false));
        list.add(new FruitBean(getString(R.string.lichee), R.drawable.icon_lichee, 1.20, false));
        list.add(new FruitBean(getString(R.string.watermelon), R.drawable.icon_watermelon, 2.60, false));
        list.add(new FruitBean(getString(R.string.honeydew), R.drawable.icon_honeydew, 2.80, false));
        list.add(new FruitBean(getString(R.string.scallions), R.drawable.icon_scallions, 0.40, false));
        list.add(new FruitBean(getString(R.string.endive), R.drawable.icon_endive, 0.80, false));
        list.add(new FruitBean(getString(R.string.beet_leaves), R.drawable.icon_beet_leaves, 1.50, false));
        list.add(new FruitBean(getString(R.string.beetroot), R.drawable.icon_beetroot, 1.10, false));
        list.add(new FruitBean(getString(R.string.cassava), R.drawable.icon_cassava, 0.77, false));
        list.add(new FruitBean(getString(R.string.artichoke), R.drawable.icon_artichoke, 0.98, false));
        adapter.setData(list);
    }


    private void insertAccountData(int position) {
        accountsList.add(new AccountsBean(list.get(position).getName(), list.get(position).getPrice(), 1.52, list.get(position).getPrice() * 1.52, list.get(position).isWeigh()));
        accountsAdapter.setData(accountsList);

    }
}