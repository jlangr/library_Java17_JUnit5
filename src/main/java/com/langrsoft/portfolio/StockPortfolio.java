package com.langrsoft.portfolio;

import java.util.HashMap;
import java.util.Map;

public class StockPortfolio {

    Map<String, Integer> stocks = new HashMap<>();
    public void purchase(String symbol, int count){
        // Check if the symbol already exists in the portfolio
        if(stocks.containsKey(symbol)){
            // If it exists, update the count
            int existingCount = stocks.get(symbol);
            stocks.put(symbol, existingCount + count);
        } else {
            // If it doesn't exist, add a new entry
            stocks.put(symbol, count);
        }
    }

    public int getUniqueCount(){
        return stocks.size();
    }
}
