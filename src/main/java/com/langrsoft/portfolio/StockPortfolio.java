package com.langrsoft.portfolio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockPortfolio {
    Set<String> stocks = new HashSet<>();

    public void purchase(String stockName) {
        if (stockName.isEmpty()) {
            throw new RuntimeException(new InvalidStockNameException());
        }
        stocks.add(stockName);
    }


    public int getUniqueStockCount() {
        return stocks.size();
    }
}
