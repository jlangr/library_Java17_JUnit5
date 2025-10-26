package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class APortfolioValue {
   private static final int NOKIA_CURRENT_PRICE = 7;
   Portfolio portfolio = new Portfolio();

   @Test
   void isWorthNothingWhenCreated() {
      assertThat(portfolio.value()).isEqualTo(0);
   }

   @Test
   void isWorthSymbolPriceForSingleSharePurchase() {
      var service = new StockLookupService() {
         @Override
         public int currentPrice(String symbol) {
            return NOKIA_CURRENT_PRICE;
         }
      };
      portfolio.setLookupService(service);

      portfolio.purchase("NOK", 1);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE);
   }
}
