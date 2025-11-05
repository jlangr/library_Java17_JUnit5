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
      applyShareDelta(symbol, shares);
   }

   public void sell(String symbol, int shares) {
      applyShareDelta(symbol, -shares);
   }

   private void throwWhenNotPositive(int shares) {
      if (shares <= 0)
         throw new InvalidPurchaseException();
   }

   public int shares(String symbol) {
      return sharesMap.getOrDefault(symbol, 0);
   }

   public boolean isEmpty() {
      return sharesMap.isEmpty();
   }

   private void applyShareDelta(String symbol, int shares) {
      sharesMap.put(symbol, shares + shares(symbol));
   }
}
