package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class APortfolioValue {
   private static final int NOKIA_CURRENT_PRICE = 7;
   private static final int APPLE_CURRENT_PRICE = 200;
   Portfolio portfolio = new Portfolio();

   @Test
   void isWorthNothingWhenCreated() {
      assertThat(portfolio.value()).isEqualTo(0);
   }

   @Test
   void isWorthSymbolPriceForSingleSharePurchase() {
      var service = mock(StockLookupService.class);
      when(service.currentPrice("NOK")).thenReturn(NOKIA_CURRENT_PRICE);
      portfolio.setLookupService(service);

      portfolio.purchase("NOK", 1);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE);
   }

   @Test
   void isWorthSymbolPriceTimesNumberOfShares() {
      var service = new StockLookupService() {
         @Override
         public int currentPrice(String symbol) {
            return NOKIA_CURRENT_PRICE;
         }
      };
      portfolio.setLookupService(service);

      portfolio.purchase("NOK",42);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE * 42);
   }

   @Test
   void totalsValuesForAllSymbols() {
      var service = new StockLookupService() {
         @Override
         public int currentPrice(String symbol) {
            return symbol.equals("NOK")
                     ? NOKIA_CURRENT_PRICE
                     : APPLE_CURRENT_PRICE;
         }
      };
      portfolio.setLookupService(service);

      portfolio.purchase("NOK",42);
      portfolio.purchase("AAPL",10);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE * 42 + APPLE_CURRENT_PRICE * 10);
   }
}
