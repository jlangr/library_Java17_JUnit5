package com.langrsoft.portfolio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AStockPortfolio {
   @Test
   void contains0UniqueSymbolsWhenNoPurchases() {
      var stockPortfolio = new StockPortfolio();

      assertThat(stockPortfolio.uniqueSymbols()).isEqualTo(0);
   }

   @Test
   void contains1UniqueSymbolAfterPurchase() {
      var stockPortfolio = new StockPortfolio();

      stockPortfolio.purchase("NOK", 10);

      assertThat(stockPortfolio.uniqueSymbols()).isEqualTo(1);
   }
}
