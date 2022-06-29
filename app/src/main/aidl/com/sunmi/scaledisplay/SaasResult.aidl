package com.sunmi.scaledisplay;

interface SaasResult {

    /**
    *  返回对应的数量/重量以及计算的价格
    */
    oneway void priceResult(boolean isAvailable, int value, double totalPrice);
}