package com.langrsoft.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Portfolio {
   private Map<String, Integer> holdings = new HashMap<>();
   private StockLookupService lookupService;

   public void purchase(String symbol, int shares) {
      if (shares <= 0)
         throw new InvalidPurchaseException();
      holdings.put(symbol, shares(symbol) + shares);
   }

   public int uniqueSymbolCount() {
      return holdings.size();
   }

   public int shares(String symbol) {
      return holdings.getOrDefault(symbol, 0);
   }

   public boolean isEmpty() {
      return holdings.isEmpty();
   }

   public void sell(String symbol, int shares) {
      if (isSelloff(symbol, shares))
         holdings.remove(symbol);
      else
         holdings.put(symbol, shares(symbol) - shares);
   }

   private boolean isSelloff(String symbol, int shares) {
      return shares(symbol) == shares;
   }

   public int value() {
      if (isEmpty())
         return 0;

      int totalValue = 0;
      for (var symbol : holdings.keySet()) {
         int currentPrice = lookupService.currentPrice(symbol);
         totalValue += currentPrice * shares(symbol);
      }
      return totalValue;
   }

   public void setLookupService(StockLookupService lookupService) {
      this.lookupService = lookupService;
   }
}
