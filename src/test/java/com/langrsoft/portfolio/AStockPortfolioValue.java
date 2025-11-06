package com.langrsoft.portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AStockPortfolioValue {
   static final int NOKIA_PRICE = 7;
   static final int APPLE_PRICE = 100;

   StockLookupService service = mock(StockLookupService.class);
   StockPortfolio stockPortfolio;

   @BeforeEach
   void setup() {
      stockPortfolio = new StockPortfolio(service);
   }

   @Test
   void isZeroWhenNoPurchasesMade() {
      stockPortfolio = new StockPortfolio();
      var value = stockPortfolio.value();

      assertThat(value).isEqualTo(0);
   }

   @Test
   void isWorthStockPriceWhenOneSharePurchased() {
      when(service.price("NOK")).thenReturn(NOKIA_PRICE);

      stockPortfolio.purchase("NOK", 1);

      assertThat(stockPortfolio.value()).isEqualTo(NOKIA_PRICE);
   }

   @Test
   void multipliesPriceBySharesHeld() {
      when(service.price("NOK")).thenReturn(NOKIA_PRICE);

      stockPortfolio.purchase("NOK", 10);

      assertThat(stockPortfolio.value()).isEqualTo(10 * NOKIA_PRICE);
   }

   @Test
   void accumulatesValuesForAllSymbols() {
      when(service.price("NOK")).thenReturn(NOKIA_PRICE);
      when(service.price("AAPL")).thenReturn(APPLE_PRICE);

      stockPortfolio.purchase("NOK", 10);
      stockPortfolio.purchase("AAPL", 1000);

      assertThat(stockPortfolio.value()).isEqualTo(10 * NOKIA_PRICE + 1000 * APPLE_PRICE);
   }
}
