package com.sunmi.weighingdemo.bean;

public class FruitBean {
    private String name;
    private int icon;
    private double price;
    private String priceUnit;
    private boolean isWeigh;

    public FruitBean(String name, int icon, double price,String priceUnit,boolean isWeigh) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.priceUnit = priceUnit;
        this.isWeigh = isWeigh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public boolean isWeigh() {
        return isWeigh;
    }

    public void setWeigh(boolean weigh) {
        isWeigh = weigh;
    }
}

