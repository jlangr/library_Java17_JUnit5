package com.langrsoft.util;

public interface StockLookupService {
   int currentPrice(String symbol);
   int historicalClosingPrice(String symbol, int daysAgo);
   Exchange exchangeLookup(String symbol);
}
