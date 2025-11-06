package com.langrsoft.portfolio;

import com.langrsoft.util.NotYetImplementedException;

public class NYSE implements StockLookupService {
   @Override
   public int price(String symbol) {
      throw new NotYetImplementedException();
   }
}
