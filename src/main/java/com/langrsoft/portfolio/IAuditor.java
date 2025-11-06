package com.langrsoft.portfolio;

public interface IAuditor {
    void logPurchase(String stockSymbol, int quantity);
    void logSale(String stockSymbol, int quantity);
}
