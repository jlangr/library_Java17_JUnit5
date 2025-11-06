package com.langrsoft.portfolio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AStockPortfolioValue {
   private static final int NOKIA_PRICE = 7;
   private static final int APPLE_PRICE = 100;

   @Test
   void isZeroWhenNoPurchasesMade() {
      var value = new StockPortfolio().value();

      assertThat(value).isEqualTo(0);
   }

   @Test
   void isWorthStockPriceWhenOneSharePurchased() {
      var stubLookupService = new StockLookupService() {
         @Override
         public int price(String symbol) {
            return NOKIA_PRICE;
         }
      };
      var stockPortfolio = new StockPortfolio(stubLookupService);

      stockPortfolio.purchase("NOK", 1);

      assertThat(stockPortfolio.value()).isEqualTo(NOKIA_PRICE);
   }

   @Test
   void multipliesPriceBySharesHeld() {
      var stubLookupService = new StockLookupService() {
         @Override
         public int price(String symbol) {
            return NOKIA_PRICE;
         }
      };
      var stockPortfolio = new StockPortfolio(stubLookupService);

      stockPortfolio.purchase("NOK", 10);

      assertThat(stockPortfolio.value()).isEqualTo(10 * NOKIA_PRICE);
   }

   @Test
   void accumulatesValuesForAllSymbols() {
      var stubLookupService = new StockLookupService() {
         @Override
         public int price(String symbol) {
            return symbol.equals("AAPL") ? APPLE_PRICE : NOKIA_PRICE;
         }
      };
      var stockPortfolio = new StockPortfolio(stubLookupService);

      stockPortfolio.purchase("NOK", 10);
      stockPortfolio.purchase("AAPL", 1000);

      assertThat(stockPortfolio.value()).isEqualTo(10 * NOKIA_PRICE + 1000 * APPLE_PRICE);
   }

   @Test
   void mockito() {

      var service = mock(StockLookupService.class);
      when(service.price("NOK")).thenReturn(NOKIA_PRICE);

   }
}
