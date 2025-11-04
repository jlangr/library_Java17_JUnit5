package com.langrsoft.portfolio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockPortfolio {
   private Set<String> symbols = new HashSet<>();
   private int shares;
   private Map<String, Integer> sharesMap = new HashMap<>();

   public int uniqueSymbols() {
      return symbols.size();
   }

   public void purchase(String symbol, int shares) {
      symbols.add(symbol);
      this.shares += shares;
      sharesMap.put(symbol, shares + shares(symbol));
   }

   public int shares(String symbol) {
      return sharesMap.getOrDefault(symbol, 0);
   }
}
