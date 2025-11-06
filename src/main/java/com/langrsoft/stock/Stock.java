package com.langrsoft.stock;

public class Stock {

    String stockName;
    int stockQty;

    public String getStockName() {
        return stockName;
    }

    public Stock setStockName(String stockName) {
        this.stockName = stockName;
        return this;
    }

    public int getStockQty() {
        return stockQty;
    }

    public Stock setStockQty(int stockQty) {
        this.stockQty = stockQty;
        return this;
    }
}
