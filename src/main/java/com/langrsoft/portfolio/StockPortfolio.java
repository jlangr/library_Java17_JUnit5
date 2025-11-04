package com.langrsoft.portfolio;

import java.util.HashMap;
import java.util.Map;

public class StockPortfolio {
    Map<String, Integer> stocks = new HashMap<>();

    public static final String STOCK_NAME_ERROR = "Stock name cannot be empty";
    public static final String STOCK_COUNT_ERROR = "Purchase count should be greater than zero";


    public void purchase(String stockName, int shares) {
        validateInput(stockName, shares);
        stocks.put(stockName, updateSharesForStock(stockName, shares));
    }

    private int updateSharesForStock(String stockName, int shares) {
        return stocks.getOrDefault(stockName, 0) + shares;
    }

    private static void validateInput(String stockName, int shares) {
        if (stockName.isEmpty()) {
            throw new InvalidStockException(STOCK_NAME_ERROR);
        }
        if (shares <= 0) {
            throw new InvalidStockException(STOCK_COUNT_ERROR);
        }
    }


    public int getUniqueStockCount() {
        return stocks.size();
    }

    public int getCountForGivenStock(String stockName) {
        return stocks.get(stockName);
    }
}
