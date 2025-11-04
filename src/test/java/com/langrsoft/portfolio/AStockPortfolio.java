package com.langrsoft.portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AStockPortfolio {
   StockPortfolio stockPortfolio;

   @BeforeEach
   void setUp() {
      stockPortfolio = new StockPortfolio();
   }

   @Test
   void contains0UniqueSymbolsWhenNoPurchases() {
      assertThat(stockPortfolio.uniqueSymbols()).isEqualTo(0);
   }

   @Test
   void contains1UniqueSymbolAfterPurchase() {
      stockPortfolio.purchase("NOK", 10);

      assertThat(stockPortfolio.uniqueSymbols()).isEqualTo(1);
   }

   @Test
   void incrementsUniqueSymbolsAfterPurchaseDifferentSymbol() {
      stockPortfolio.purchase("NOK", 10);
      stockPortfolio.purchase("AAPL", 5);

      assertThat(stockPortfolio.uniqueSymbols()).isEqualTo(2);
   }

   @Test
   void doesNotIncrementUniqueSymbolsAfterPurchaseSameSymbol() {
      stockPortfolio.purchase("NOK", 10);
      stockPortfolio.purchase("NOK", 5);

      assertThat(stockPortfolio.uniqueSymbols()).isEqualTo(1);
   }

   @Test
   void containsNoSharesForSymbolNotPurchased() {
      assertThat(stockPortfolio.shares("MSFT")).isEqualTo(0);
   }

   @Test
   void returnsSharesPurchased() {
      stockPortfolio.purchase("NOK", 10);

      var result = stockPortfolio.shares("NOK");

      assertThat(result).isEqualTo(10);
   }
}
