package com.langrsoft.portfolio;

import java.util.HashSet;
import java.util.Set;

public class StockPortfolio {

    Set<String> stocks = new HashSet<>();
    public void purchase(String symbol, int count){
        stocks.add(symbol);
    }

    public int getUniqueCount(){
        return stocks.size();
    }
}
