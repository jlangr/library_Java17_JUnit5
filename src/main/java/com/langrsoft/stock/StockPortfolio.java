package com.langrsoft.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.sum;

public class StockPortfolio {
    List<Stock> stockList = new ArrayList<>();

    public int getNumberOfStocks() {
        int numberOfStocks = 0;
        for (Stock stock : stockList) {
            numberOfStocks += stock.getNumberOfShares();
        }
        return numberOfStocks;
    }

    public void purchase(Stock stock) throws InvalidStockQtyException {
        if (stock.getNumberOfShares() < 1) {
            throw new InvalidStockQtyException("Invalid stock quantity");
        }
        Optional<Stock> existingStock = stockList.stream()
                .filter(s -> s.getStockName().equals(stock.getStockName()))
                .findFirst();
        if (existingStock.isPresent()) {
            Stock stock1 = existingStock.get();
            stock1.setNumberOfShares(stock1.getNumberOfShares() + stock.getNumberOfShares());
        } else {
            stockList.add(stock);
        }
    }

    public boolean isManyPurchase() {
        boolean flag = false;
        String prevStock = null;
        for (Stock stock : stockList) {
            if (prevStock == null) {
                prevStock = stock.getStockName();
            }
            if (!stock.getStockName().equals(prevStock)) {
                flag = true;
            }
        }
        return flag;
    }


    public int getStockCount(String stockName) {
        return stockList.stream()
                .filter(stock -> stock.getStockName().equals(stockName))
                .map(stock -> stock.getNumberOfShares())
                .mapToInt(Integer::intValue).sum();
    }
}
