package com.langrsoft.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockPortfolio {
    List<Stock> stockList = new ArrayList<>();

    IStockExchange stockExchange;

    public StockPortfolio() {

    }

    public StockPortfolio(IStockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    public int getUniqueNumberOfStocks() {
        return stockList.size();
    }

    public void purchase(Stock stock) throws InvalidStockQtyException {
        validateStockQty(stock);
        Optional<Stock> existingStock = stockList.stream()
                .filter(s -> s.getStockName().equals(stock.getStockName()))
                .findFirst();
        if (existingStock.isPresent()) {
            Stock stock1 = existingStock.get();
            stock1.setStockQty(stock1.getStockQty() + stock.getStockQty());
        } else {
            stockList.add(stock);
        }
    }

    private static void validateStockQty(Stock stock) throws InvalidStockQtyException {
        if (stock.getStockQty() < 1) {
            throw new InvalidStockQtyException("Invalid stock quantity");
        }
    }

    public boolean isManyPurchase() {
        return getUniqueNumberOfStocks() > 1;
    }


    public int getStockCount(String stockName) {
        return stockList.stream()
                .filter(stock -> stock.getStockName().equals(stockName))
                .map(stock -> stock.getStockQty())
                .mapToInt(Integer::intValue).sum();
    }

    public int getPortfolioValue() {
        return stockList.stream()
                .map(stock -> stock.getStockQty() * stockExchange.getPrice(stock.getStockName()))
                .mapToInt(Integer::intValue).sum();
    }
}
