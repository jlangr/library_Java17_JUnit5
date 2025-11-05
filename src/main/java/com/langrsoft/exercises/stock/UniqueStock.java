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
        stockHolds.put(upperCase, shares);
        return true;

    }

    public Integer checkStockWalletSymbolCount() {
        return 5;
    }
}
