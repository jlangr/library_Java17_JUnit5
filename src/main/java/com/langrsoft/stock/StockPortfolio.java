package com.langrsoft.stock;

import java.util.ArrayList;
import java.util.List;

public class StockPortfolio {
    List<Stock> stockList  = new ArrayList<>();

    public int getNumberOfStocks() {
        int numberOfStocks = 0;
        for(Stock stock:stockList){
            numberOfStocks += stock.getNumberOfShares();
        }
        return numberOfStocks;
    }

    public void purchase(Stock stock) {
        stockList.add(stock);
    }

    public  boolean isManyPurchase(){
        boolean flag = false;
        String prevStock = null;
        for(Stock stock:stockList){
            if(prevStock == null){
                prevStock = stock.getStockName();
            }
            if(!stock.getStockName().equals(prevStock)){
                flag = true;
            }
        }
        return flag;
    }


}
