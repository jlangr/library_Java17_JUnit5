package com.langrsoft.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class APortfolio {
   static final Exchange NYSE = new Exchange("NYSE", "New York Stock Exchange");

   @InjectMocks
   Portfolio portfolio;

   @Mock
   Auditor auditor;

   @Mock
   StockLookupService lookupService;

   @Mock
   Supplier<String> uuidSource;

   // This should be organized around behaviors, not characteristics.
   @BeforeEach
   void setup() {
      when(lookupService.exchangeLookup(anyString())).thenReturn(NYSE);
   }

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

      @BeforeEach
      void setup() {
         when(lookupService.exchangeLookup("NOK")).thenReturn(NYSE);
      }

      @Test
      void auditsTradeTransactionOnPurchaseVerifyByPopulatingExpectedObject() {
         TradeTransaction.uuidSource = () -> "UUID12345";
         var timestamp = Instant.parse("2025-10-28T12:00:00Z");
         TradeTransaction.clock = Clock.fixed(timestamp, ZoneOffset.UTC);
         portfolio.setBrokerageId("BRK123");

         portfolio.purchase("NOK", 10);

         var expectedTransaction = new TradeTransaction(
            "NOK", 10, timestamp, "BR-123309", NYSE.exchange(), "UUID12345"
         );
         verify(auditor).logPurchase(expectedTransaction);
      }

      org.mockito.ArgumentCaptor<TradeTransaction> captor =
         ArgumentCaptor.forClass(TradeTransaction.class);

      @Test
      void auditsTradeTransactionVerifyUsingArgCapture() {
         portfolio.purchase("NOK", 30);

         verify(auditor).logPurchase(captor.capture());
         var transaction = captor.getValue();
         assertThat(transaction.symbol()).isEqualTo("NOK");
         assertThat(transaction.shares()).isEqualTo(30);
         assertThat(transaction.exchange()).isEqualTo("NYSE");
      }

      @Test
      void auditsTradeTransactionVerifyUsingAssertJ() {
         portfolio.purchase("NOK", 30);

         verify(auditor).logPurchase(captor.capture());
         var transaction = captor.getValue();
         assertThat(transaction)
            .extracting(TradeTransaction::symbol, TradeTransaction::shares, TradeTransaction::exchange)
            .containsExactly("NOK", 30, "NYSE");
      }

      @Test
      void auditsTradeTransactionOnPurchase() {
         portfolio.purchase("NOK", 10);

         verify(auditor).logPurchase(argThat(transaction ->
            transaction.symbol().equals("NOK") &&
            transaction.shares() == 10 &&
            transaction.exchange().equals("NYSE")
         ));
      }
   }
}