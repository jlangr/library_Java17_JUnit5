package com.langrsoft.portfolio;

import java.util.HashSet;
import java.util.Set;

public class StockPortfolio {
   private Set<String> symbols = new HashSet<>();

   public int uniqueSymbols() {
      return symbols.size();
   }

   public void purchase(String symbol, int shares) {
      symbols.add(symbol);
   }
}
