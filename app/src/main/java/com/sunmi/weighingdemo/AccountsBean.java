package com.sunmi.weighingdemo;

public class AccountsBean {

    private String name;
    private double price;
    private double weigh;
    private double total;
    private boolean isWeigh;

    public AccountsBean(String name, double price, double weigh, double total, boolean isWeigh) {
        this.name = name;
        this.price = price;
        this.weigh = weigh;
        this.total = total;
        this.isWeigh = isWeigh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeigh() {
        return weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isWeigh() {
        return isWeigh;
    }

    public void setWeigh(boolean weigh) {
        isWeigh = weigh;
    }
}
