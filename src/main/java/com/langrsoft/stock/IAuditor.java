package com.langrsoft.stock;

public interface IAuditor {
    void logPurchase(String symbol, int shares);
}
