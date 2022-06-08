package com.sunmi.weighingdemo.util;

import android.content.Context;

import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

public class PrintUtils {

    private volatile static PrintUtils printUtils;
    private SunmiPrinterService sunmiPrinterService;

    public static PrintUtils getInstance() {
        if (printUtils == null) {
            synchronized (PrintUtils.class) {
                if (printUtils == null) {
                    printUtils = new PrintUtils();
                }
            }
        }
        return printUtils;
    }

    private InnerPrinterCallback callback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            sunmiPrinterService = service;
        }

        @Override
        protected void onDisconnected() {
            sunmiPrinterService = null;
        }
    };

    /**
     * 检查打印机是否存在
     */
    public boolean hasPrinter(Context context) {
        try {
            if (sunmiPrinterService == null) {
                InnerPrinterManager.getInstance().bindService(context, callback);
                for (int i = 0; i < 5; i++) {
                    if (sunmiPrinterService != null) {
                        break;
                    }
                    Thread.sleep(500);
                }
            }
            return sunmiPrinterService != null && InnerPrinterManager.getInstance().hasPrinter(sunmiPrinterService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public SunmiPrinterService getPrinter(Context context) {
        try {
            if (sunmiPrinterService == null) {
                InnerPrinterManager.getInstance().bindService(context, callback);
            } else {
                return sunmiPrinterService;
            }
            for (int i = 0; i < 5; i++) {
                if (sunmiPrinterService != null) {
                    return sunmiPrinterService;
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取打印机纸张规格
     */
    public int getPrinterPaper(Context context) {
        try {
            if (sunmiPrinterService == null) {
                InnerPrinterManager.getInstance().bindService(context, callback);
            } else {
                return sunmiPrinterService.getPrinterPaper();
            }
            for (int i = 0; i < 5; i++) {
                if (sunmiPrinterService != null) {
                    return sunmiPrinterService.getPrinterPaper();
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 58;
    }
}
