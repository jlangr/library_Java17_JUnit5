package com.langrsoft.exercises.stock;

import java.util.HashMap;
import java.util.Map;

public class UniqueStock {
    private Map<String, Integer> stockHolds;

    public UniqueStock() {
        this.stockHolds = new HashMap<>();
    }

    public boolean purchaseStock(String symbol, int shares){
        if (shares <= 0) {
                throw new WrongShareAmount("Number of shares must be positive.");
        }

        if (!symbol.matches("[a-zA-Z]+")) {
            throw new StockNotAvailable("Wrong symbol.");
        }

        String upperCase = symbol.toUpperCase();

        int existingStock = stockHolds.getOrDefault(upperCase, 0);

        if (existingStock > 0) {
            stockHolds.put(upperCase, shares + existingStock);
        } else {
            stockHolds.put(upperCase, shares);
        }
        return true;
    }

    public Integer checkStockCount(String symbol) {
        return stockHolds.getOrDefault(symbol.toUpperCase(), 0);
    }

    public Integer checkUniqueStockSymbolCount() {
        return stockHolds.keySet().size();
    }


    public boolean checkEmptyWallet() {
        return false;
    }

    public boolean sellStock(String nok, int i) {
        return false;
    }
}
