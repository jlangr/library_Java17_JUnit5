package com.langrsoft.util;

import java.time.Clock;
import java.util.*;
import java.util.function.Supplier;

public class Portfolio {
   private Map<String, Integer> holdings = new HashMap<>();
   private StockLookupService lookupService;
   private Auditor auditor;
   private List<TradeTransaction> transactions = new ArrayList<>();
   private Clock clock = Clock.systemUTC();
   private Supplier<String> uuidSource;
   private String brokerageId;

   public void purchase(String symbol, int shares) {
      if (shares <= 0)
         throw new InvalidPurchaseException();
      auditor.logPurchase(createTransaction(symbol, shares));
      holdings.put(symbol, shares(symbol) + shares);
   }

   private TradeTransaction createTransaction(String symbol, int shares) {
      return new TradeTransaction(symbol,
         shares,
         lookupService.exchangeLookup(symbol).exchange());
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
      auditor.logSale(symbol, shares);
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

      try {
         return holdings.keySet().stream()
            .map(symbol -> lookupService.currentPrice(symbol) * shares(symbol))
            .reduce(0, Integer::sum);
      }
      catch (SecurityException e) {
         throw new ServiceException();
      }
   }

   public void setLookupService(StockLookupService lookupService) {
      this.lookupService = lookupService;
   }

   public List<TradeTransaction> transactions(String nok) {
      return transactions;
   }

   public void setClock(Clock clock) {
      this.clock = clock;
   }

   public void setBrokerageId(String brokerageId) {
      this.brokerageId = brokerageId;

   }
}
