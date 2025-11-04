package com.langrsoft.portfolio;

public class StockPortfolio {
   private int uniqueSymbols;

   public int uniqueSymbols() {
      return uniqueSymbols;
   }

   public void purchase(String symbol, int shares) {
      uniqueSymbols = 1;
   }
}
