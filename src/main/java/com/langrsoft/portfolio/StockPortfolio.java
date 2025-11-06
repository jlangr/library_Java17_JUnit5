package com.langrsoft.portfolio;

import com.langrsoft.util.NotYetImplementedException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockPortfolio {

    final IStockPortFolioService stockPortFolioService;
    StockPortfolio(IStockPortFolioService stockPortFolioService) {
        this.stockPortFolioService = stockPortFolioService;
    }
    Map<String, Integer> stocks = new HashMap<>();

    public void purchase(String symbol, int count){
        int existingCount = 0;

        validateStocksCount(count);
        if(stocks.containsKey(symbol)){
            existingCount = stocks.get(symbol);
        }

        stocks.put(symbol, (existingCount + count));

    }

    private static void validateStocksCount(int count) {
        if(count <1){
            throw new InvalidParameterException("Purchase count should be more than 0");
        }
    }

    public int getUniqueCount(){
        return stocks.size();
    }

    public int getShareCount(String symbol) {
        if(                stocks.containsKey(symbol)        )
        {
            return stocks.get(symbol);
        }
        return 0;
        /*if (stocks.get(symbol)) {
            return 1;
        } else {
            return 0;
        }*/
    }

    public int getTotalValue() {
        int totalPrice = 0;
        for( var entrySet : stocks.entrySet()){
            String symbol = entrySet.getKey();
            int count = entrySet.getValue();
            totalPrice += stockPortFolioService.getCurrentPrice(symbol)*count;
        }
        return totalPrice;
    }
}
