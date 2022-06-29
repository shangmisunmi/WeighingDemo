package com.sunmi.scaledisplay;

import com.sunmi.scaledisplay.SaasResult;

interface SaasService {

    /**
    *  按数量单价获取当前的总价
    */
    void requestPriceByAmount(double unitPrice, int amount, in SaasResult result);

    /**
    *  按100g单价获取当前的总价
    */
    void requestPriceByHGram(double unitPrice, int tareWeight, in SaasResult result);

    /**
    * 按1000g单价获取当前的总价
    */
    void requestPriceByKGram(double unitPrice, int tareWeight, in SaasResult result);
}