package com.langrsoft.exercises.stock;

import java.util.HashMap;
import java.util.Map;

public class UniqueStock {
    private Map<String, Integer> stockHolds;

    public UniqueStock() {
        this.stockHolds = new HashMap<>();
    }

    public boolean purchaseStock(String symbol, int shares){
        if (shares <= 0) return false;

        String upperCase = symbol.toUpperCase();

        int existingStock = stockHolds.getOrDefault(upperCase, 0);
        if (existingStock > 0) {
            stockHolds.put(upperCase, shares + existingStock);
        } else {
            stockHolds.put(upperCase, shares);
        }
        return true;
    }

    public Integer checkStockWalletSymbolCount(String symbol) {
        return stockHolds.getOrDefault(symbol.toUpperCase(), 0);
    }
}
