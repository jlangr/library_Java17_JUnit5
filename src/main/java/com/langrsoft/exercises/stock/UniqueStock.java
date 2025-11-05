package com.langrsoft.exercises.stock;

import java.util.HashMap;
import java.util.Map;

public class UniqueStock {
    private final Map<String, Integer> stockHolds = new HashMap<>();

    public void purchaseStock(String symbol, int shares) {
        String upperCase = initialValidation(symbol, shares);
        stockHolds.merge(upperCase, shares, Integer::sum);
    }

    public boolean sellStock(String symbol, int shareAmount) {
        String symbolUpperCase = initialValidation(symbol, shareAmount);
        int existingStock = stockHolds.getOrDefault(symbolUpperCase, 0);

        if (existingStock == 0) throw new StockNotAvailable("Stock not available in wallet.");
        if (shareAmount > existingStock) throw new WrongShareAmount("Not enough shares to sell.");

        if (shareAmount == existingStock) {
            stockHolds.remove(symbolUpperCase);
        } else {
            stockHolds.put(symbolUpperCase, existingStock - shareAmount);
        }
        return true;
    }

    public int checkStockCount(String symbol) {
        return stockHolds.getOrDefault(symbol.toUpperCase(), 0);
    }

    public int checkUniqueStockSymbolCount() {
        return stockHolds.size();
    }

    public boolean checkEmptyWallet() {
        return stockHolds.isEmpty();
    }

    private String initialValidation(String symbol, int shares) {
        if (shares <= 0) throw new WrongShareAmount("Number of shares must be positive.");
        if (!symbol.matches("[a-zA-Z]+")) throw new StockNotAvailable("Wrong symbol.");
        return symbol.toUpperCase();
    }
}
