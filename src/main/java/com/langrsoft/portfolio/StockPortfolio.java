package com.langrsoft.portfolio;

import java.util.HashMap;
import java.util.Map;

public class StockPortfolio {
   private Map<String, Integer> sharesMap = new HashMap<>();

   public int uniqueSymbols() {
      return sharesMap.size();
   }

   public void purchase(String symbol, int shares) {
      throwWhenNotPositive(shares);
      sharesMap.put(symbol, shares + shares(symbol));
   }

   private void throwWhenNotPositive(int shares) {
      if (shares <= 0)
         throw new InvalidPurchaseException();
   }

   public int shares(String symbol) {
      return sharesMap.getOrDefault(symbol, 0);
   }
}
