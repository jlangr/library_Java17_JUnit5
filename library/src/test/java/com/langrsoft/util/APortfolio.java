package com.langrsoft.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class APortfolio {
   @InjectMocks
   Portfolio portfolio;

   @Mock
   Auditor auditor;

   // This should be organized around behaviors, not characteristics.

   @Nested
   class IsEmpty {
      @Test
      void isTrueWhenCreated() {
         assertThat(portfolio.isEmpty()).isTrue();
      }

      @Test
      void isFalseAfterPurchase() {
         portfolio.purchase("AAPL", 1);

         assertThat(portfolio.isEmpty()).isFalse();
      }

      @Test
      void isTrueAfterLastSymbolSelloff() {
         portfolio.purchase("AAPL", 1);

         portfolio.sell("AAPL", 1);

         assertThat(portfolio.isEmpty()).isTrue();
      }
   }

   @Nested
   class UniqueSymbolCount {
      @Test
      void isZeroWhenEmpty() {
         assertThat(portfolio.uniqueSymbolCount()).isEqualTo(0);
      }

      @Test
      void updatesOnPurchase() {
         portfolio.purchase("AAPL", 10);

         assertThat(portfolio.uniqueSymbolCount()).isEqualTo(1);
      }

      @Test
      void incrementsWithNewSymbol() {
         portfolio.purchase("AAPL", 10);

         portfolio.purchase("IBM", 20);

         assertThat(portfolio.uniqueSymbolCount()).isEqualTo(2);
      }

      @Test
      void doesNotIncrementOnPurchaseSameSymbol() {
         portfolio.purchase("AAPL", 10);

         portfolio.purchase("AAPL", 20);

         assertThat(portfolio.uniqueSymbolCount()).isEqualTo(1);
      }

      @Test
      void decrementsOnSymbolSelloff() {
         portfolio.purchase("AAPL", 10);

         portfolio.sell("AAPL", 10);

         assertThat(portfolio.uniqueSymbolCount()).isEqualTo(0);
      }
   }

   @Nested
   class Shares {
      @Test
      void throwsWhenPurchasingNonPositiveShares() {
         assertThrows(InvalidPurchaseException.class, () -> portfolio.purchase("AAPL", 0));

      }

      @Test
      void areZeroForUnpurchasedSymbol() {
         assertThat(portfolio.shares("AAPL")).isEqualTo(0);
      }

      @Test
      void areWhatWasPurchased() {
         portfolio.purchase("AAPL", 10);

         assertThat(portfolio.shares("AAPL")).isEqualTo(10);
      }

      @Test
      void accumulateForSymbol() {
         portfolio.purchase("AAPL", 10);

         portfolio.purchase("AAPL", 20);

         assertThat(portfolio.shares("AAPL")).isEqualTo(30);
      }

      @Test
      void segregateBySymbol() {
         portfolio.purchase("AAPL", 10);

         portfolio.purchase("IBM", 20);

         assertThat(portfolio.shares("AAPL")).isEqualTo(10);
      }

      @Test
      void decreaseAfterSale() {
         portfolio.purchase("AAPL", 100);

         portfolio.sell("AAPL", 75);

         assertThat(portfolio.shares("AAPL")).isEqualTo(25);
      }
   }

   @Nested
   class Selling {
      @Test
      void createsAuditRecordForSale() {
         portfolio.purchase("NOK", 50);

         portfolio.sell("NOK", 10);

         verify(auditor).logSale("NOK", 10);
      }
   }
}