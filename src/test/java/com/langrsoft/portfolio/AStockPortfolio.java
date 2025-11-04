package com.langrsoft.portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

      assertThat(stockPortfolio.shares("NOK")).isEqualTo(10);
   }

   @Test
   void accumulatesSharesForSameSymbolPurchase() {
      stockPortfolio.purchase("NOK", 10);

      stockPortfolio.purchase("NOK", 15);

      assertThat(stockPortfolio.shares("NOK")).isEqualTo(25);
   }

   @Test
   void separatesSharesBySymbol() {
      stockPortfolio.purchase("NOK", 10);
      stockPortfolio.purchase("AAPL", 5);

      assertThat(stockPortfolio.shares("NOK")).isEqualTo(10);
      assertThat(stockPortfolio.shares("AAPL")).isEqualTo(5);
   }

   @ParameterizedTest
   @ValueSource(ints = {0, -1})
   void throwsWhenPurchasingNonPositiveShares(int shares) {
      assertThrows(InvalidPurchaseException.class,
         () -> stockPortfolio.purchase("NOK", shares));
   }
}
