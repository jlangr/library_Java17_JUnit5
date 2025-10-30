package com.langrsoft.util;

public interface Auditor {
   void logSale(String symbol, int shares);
   void logPurchase(TradeTransaction transaction);
}
