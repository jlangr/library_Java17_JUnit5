package com.langrsoft.exercises.stock;

import java.util.HashMap;
import java.util.Map;

public class UniqueStock {
    private final Map<String, Integer> stockHolds;

    public UniqueStock() {
        this.stockHolds = new HashMap<>();
    }

    public void purchaseStock(String symbol, int shares){
        String upperCase = initialValidation(symbol, shares);

        int existingStock = stockHolds.getOrDefault(upperCase, 0);

        if (existingStock > 0) {
            stockHolds.put(upperCase, shares + existingStock);
        } else {
            stockHolds.put(upperCase, shares);
        }
    }

    public boolean sellStock(String symbol, int shareAmount) {
        String symbolUpperCase = initialValidation(symbol, shareAmount);

        int existingStock = stockHolds.getOrDefault(symbolUpperCase, 0);

        if (existingStock == 0) throw new StockNotAvailable("Stock not available in wallet.");

        if (existingStock - shareAmount > 0) {
            stockHolds.put(symbolUpperCase, existingStock - shareAmount);
        }
        else if (existingStock - shareAmount == 0) {
            stockHolds.remove(symbolUpperCase);
        }
        else {
            throw new WrongShareAmount("Not enough shares to sell.");
        }
        return true;
    }

    public Integer checkStockCount(String symbol) {
        return stockHolds.getOrDefault(symbol.toUpperCase(), 0);
    }

    public Integer checkUniqueStockSymbolCount() {
        return stockHolds.size();
    }

    public boolean checkEmptyWallet() {
        return stockHolds.isEmpty();
    }

    private String initialValidation(String symbol, int shares) {
        if (shares <= 0) {
            throw new WrongShareAmount("Number of shares must be positive.");
        }

        if (!symbol.matches("[a-zA-Z]+")) {
            throw new StockNotAvailable("Wrong symbol.");
        }

        return symbol.toUpperCase();
    }
}
