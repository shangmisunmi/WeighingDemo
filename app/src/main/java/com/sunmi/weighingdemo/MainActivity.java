package com.sunmi.weighingdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.sunmi.scaledisplay.SaasResult;
import com.sunmi.scaledisplay.SaasService;
import com.sunmi.weighingdemo.adapter.AccountsAdapter;
import com.sunmi.weighingdemo.adapter.MyAdapter;
import com.sunmi.weighingdemo.bean.AccountsBean;
import com.sunmi.weighingdemo.bean.FruitBean;
import com.sunmi.weighingdemo.util.DialogUtil;
import com.sunmi.weighingdemo.util.PrintUtils;
import com.sunmi.weighingdemo.util.TimeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvFruit;
    private MyAdapter adapter;
    private List<FruitBean> list = new ArrayList<>();
    private TextView tvVersion;

    private RecyclerView rvSettleAccounts;
    private AccountsAdapter accountsAdapter;
    private List<AccountsBean> accountsList = new ArrayList<>();

    private TextView tvTotal;
    private TextView tvSettlement;

    private int currPosition;
    private double total = 0;

    private TextView tvReque;

    /**
     * 电子秤服务
     */
    private SaasService saasService;

    private String TAG = "MainActivity";


    private Handler handler = new Handler();

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            saasService = SaasService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            saasService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent("ACTION_SCALE_SERVICE");
        intent.setPackage("com.sunmi.scaledisplay");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
        if (displays != null && displays.length > 0) {
            WeighPresentation presentation = new WeighPresentation(this, displays[0]);
            presentation.show();
        }

        tvReque = findViewById(R.id.tv_reque);
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
                getWeigh(list.get(position).getPriceUnit(), list.get(position).getPrice(), position);
            } else {
                showPcsDialog(currPosition);
            }

        });

        rvSettleAccounts = findViewById(R.id.rv_settle_accounts);
        rvSettleAccounts.setLayoutManager(new LinearLayoutManager(this));
        accountsAdapter = new AccountsAdapter(accountsList, this);
        rvSettleAccounts.setAdapter(accountsAdapter);

        accountsAdapter.setOnClickListener(position -> {
            changeTotal(accountsList.get(position).getTotal() * -1);
            accountsList.remove(position);
            accountsAdapter.notifyDataSetChanged();
        });


        tvTotal = findViewById(R.id.tv_total);
        tvSettlement = findViewById(R.id.tv_settlement);
        tvSettlement.setOnClickListener(view -> {
            if (!accountsList.isEmpty()) {
                new Thread(this::printInfo).start();
                clearSettle();
            }
        });

        tvReque.setOnClickListener(view -> clearSettle());
    }

    private void showPcsDialog(int position) {
        DialogUtil.showDialog(this, pcs -> {
            try {
                if (saasService == null) {
                    Log.i(TAG, "服务未连接--->saasService==null");
                    return;
                }
                saasService.requestPriceByAmount(list.get(position).getPrice(), pcs, new SaasResult.Stub() {
                    @Override
                    public void priceResult(boolean isAvailable, int value, double totalPrice) throws RemoteException {
                        Log.i(TAG, "requestPriceByAmount--->isAvailable-->" + isAvailable + " value--->" + value + " ---- totalPrice--->" + totalPrice);
                        if (isAvailable) {
                            handler.post(() -> {
                                insertAccountData(position, value, totalPrice);
                            });
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void getWeigh(String unit, double price, int position) {
        if (saasService == null) {
            Log.i(TAG, "服务未连接--->saasService==null");
            return;
        }
        if (unit.equals("/kg")) {
            try {
                saasService.requestPriceByKGram(price, 0, new SaasResult.Stub() {
                    @Override
                    public void priceResult(boolean isAvailable, int value, double totalPrice) throws RemoteException {
                        Log.i(TAG, "requestPriceByKGram--->isAvailable-->" + isAvailable + " value--->" + value + " ---- totalPrice--->" + totalPrice);
                        if (isAvailable) {
                            handler.post(() -> {
                                insertAccountData(position, new BigDecimal(Double.parseDouble(String.valueOf(value)) / 1000).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(), totalPrice);
                            });
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {
                saasService.requestPriceByHGram(price, 0, new SaasResult.Stub() {
                    @Override
                    public void priceResult(boolean isAvailable, int value, double totalPrice) throws RemoteException {
                        Log.i(TAG, "requestPriceByHGram--->isAvailable-->" + isAvailable + " value--->" + value + " ---- totalPrice--->" + totalPrice);
                        if (isAvailable) {
                            handler.post(() -> {
                                insertAccountData(position, new BigDecimal(Double.parseDouble(String.valueOf(value)) / 1000).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(), totalPrice);
                            });
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void initData() {
        list.add(new FruitBean(getString(R.string.apple), R.drawable.icon_apple, 1.80, "kg", true));
        list.add(new FruitBean(getString(R.string.pear), R.drawable.icon_pear, 1.75, "kg", true));
        list.add(new FruitBean(getString(R.string.persimmon), R.drawable.icon_persimmon, 2.89, "kg", true));
        list.add(new FruitBean(getString(R.string.pomegranate), R.drawable.icon_pomegranate, 0.88, "pcs", false));
        list.add(new FruitBean(getString(R.string.cherry), R.drawable.icon_cherry, 1.20, "100g", true));
        list.add(new FruitBean(getString(R.string.orange), R.drawable.icon_orange, 1.20, "kg", true));
        list.add(new FruitBean(getString(R.string.banana), R.drawable.icon_banana, 1.70, "kg", true));
        list.add(new FruitBean(getString(R.string.blueberry), R.drawable.icon_blueberry, 0.99, "100g", true));
        list.add(new FruitBean(getString(R.string.pitaya), R.drawable.icon_pitaya, 0.89, "pcs", false));
        list.add(new FruitBean(getString(R.string.lichee), R.drawable.icon_lichee, 1.20, "100g", true));
        list.add(new FruitBean(getString(R.string.watermelon), R.drawable.icon_watermelon, 2.60, "pcs", false));
        list.add(new FruitBean(getString(R.string.honeydew), R.drawable.icon_honeydew, 2.80, "pcs", false));
        list.add(new FruitBean(getString(R.string.scallions), R.drawable.icon_scallions, 0.40, "pcs", false));
        list.add(new FruitBean(getString(R.string.endive), R.drawable.icon_endive, 0.80, "pcs", false));
        list.add(new FruitBean(getString(R.string.beet_leaves), R.drawable.icon_beet_leaves, 1.50, "kg", true));
        list.add(new FruitBean(getString(R.string.beetroot), R.drawable.icon_beetroot, 1.10, "kg", true));
        list.add(new FruitBean(getString(R.string.cassava), R.drawable.icon_cassava, 0.77, "kg", true));
        list.add(new FruitBean(getString(R.string.artichoke), R.drawable.icon_artichoke, 0.98, "kg", true));
        adapter.setData(list);
    }

    private void insertAccountData(int position, double weigh, double total) {
        accountsList.add(new AccountsBean(list.get(position).getName(), list.get(position).getPrice(), list.get(position).getPriceUnit(), weigh, total, list.get(position).isWeigh()));
        accountsAdapter.setData(accountsList);
        changeTotal(total);
    }

    private void changeTotal(double change) {
        total += change;
        if (total < 0) {
            total = 0;
        }
        tvTotal.setText(String.format(Locale.getDefault(), "%.2f", total));
    }

    private void printInfo() {
        try {
            String line = "-----------------------------";
            SunmiPrinterService printer = PrintUtils.getInstance().getPrinter(this);
            if (printer != null) {
                printer.printerInit(null);
                printer.setFontSize(25f, null);
                printer.sendRAWData(new byte[]{0x1b, 0x61, 0x01}, null);
                printer.printText(getString(R.string.print_title) + "\n\n", null);
                printer.printColumnsString(new String[]{getString(R.string.print_cashier), getString(R.string.print_address)}, new int[]{1, 1}, new int[]{0, 2}, null);
                printer.printColumnsString(new String[]{getString(R.string.print_receipt), "20220408123649186001"}, new int[]{2, 5}, new int[]{0, 2}, null);
                long timeMillis = System.currentTimeMillis();
                String time = TimeUtils.formatDate(timeMillis, TimeUtils.FORMAT_TIME_ALL);
                printer.printColumnsString(new String[]{getString(R.string.print_time), time}, new int[]{1, 2}, new int[]{0, 2}, null);
                printer.printText("\n" + line + "\n", null);
                printer.printColumnsString(new String[]{getString(R.string.print_name), getString(R.string.print_price), getString(R.string.print_pcs), getString(R.string.print_subtotal)}, new int[]{2, 1, 2, 1}, new int[]{0, 1, 1, 1}, null);
                for (int i = 0; i < accountsList.size(); i++) {
                    printer.printColumnsString(new String[]{accountsList.get(i).getName(), accountsList.get(i).getPrice() + getString(R.string.money_unit) + "/" + accountsList.get(i).getPriceUnit(), accountsList.get(i).getWeigh() + accountsList.get(i).getPriceUnit(), accountsList.get(i).getTotal() + getString(R.string.money_unit)}, new int[]{2, 2, 2, 2}, new int[]{1, 1, 2, 2}, null);
                }
                printer.printText(line + "\n", null);
                printer.printColumnsString(new String[]{getString(R.string.print_original_price, tvTotal.getText().toString() + getString(R.string.money_unit)), getString(R.string.print_total, accountsList.size())}, new int[]{1, 1}, new int[]{0, 2}, null);
                printer.printColumnsString(new String[]{getString(R.string.print_current_price, tvTotal.getText().toString() + getString(R.string.money_unit)), getString(R.string.print_pay)}, new int[]{1, 1}, new int[]{0, 2}, null);
                printer.printColumnsString(new String[]{getString(R.string.print_received, tvTotal.getText().toString() + getString(R.string.money_unit)), getString(R.string.print_change)}, new int[]{1, 1}, new int[]{0, 2}, null);
                printer.printText(line + "\n", null);
                printer.printColumnsString(new String[]{getString(R.string.print_remark)}, new int[]{1}, new int[]{0}, null);
                printer.printColumnsString(new String[]{getString(R.string.print_null)}, new int[]{1}, new int[]{0}, null);
                printer.lineWrap(5, null);
                printer.cutPaper(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSettle() {
        accountsList.clear();
        accountsAdapter.setData(accountsList);
        changeTotal(total * -1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
            connection = null;
        }
    }
}