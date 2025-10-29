package com.langrsoft.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class APortfolioValue {
   private static final int NOKIA_CURRENT_PRICE = 7;
   private static final int APPLE_CURRENT_PRICE = 200;

   @InjectMocks
   Portfolio portfolio;
   @Mock
   StockLookupService service;

   @Test
   void isWorthNothingWhenCreated() {
      assertThat(portfolio.value()).isEqualTo(0);
   }

   @Test
   void isWorthSymbolPriceForSingleSharePurchase() {
      when(service.currentPrice("NOK")).thenReturn(NOKIA_CURRENT_PRICE);

      portfolio.purchase("NOK", 1);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE);
   }

   @Test
   void isWorthSymbolPriceTimesNumberOfShares() {
      when(service.currentPrice("NOK")).thenReturn(NOKIA_CURRENT_PRICE);

      portfolio.purchase("NOK",42);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE * 42);
   }

   @Test
   void demonstratesArgumentMatchers() {
      when(service.currentPrice(anyString())).thenReturn(NOKIA_CURRENT_PRICE);

      portfolio.purchase("NOK",42);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE * 42);
   }

   @Test
   void totalsValuesForAllSymbols() {
      when(service.currentPrice("NOK")).thenReturn(NOKIA_CURRENT_PRICE);
      when(service.currentPrice("AAPL")).thenReturn(APPLE_CURRENT_PRICE);

      portfolio.purchase("NOK",42);
      portfolio.purchase("AAPL",10);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE * 42 + APPLE_CURRENT_PRICE * 10);
   }

   @Disabled
   @Test
   public void argumentMatchesUseOneUseAll() {
      when(service.historicalClosingPrice("NOK", anyInt())).thenReturn(42);
      int daysAgo = new java.util.Random().nextInt();

      var result = service.historicalClosingPrice("NOK", daysAgo);

      assertThat(result).isEqualTo(42);
   }

   @Test
   public void argumentMatchesUseOneUseAllWorking() {
      when(service.historicalClosingPrice(eq("NOK"), anyInt())).thenReturn(42);
      int daysAgo = new java.util.Random().nextInt();

      var result = service.historicalClosingPrice("NOK", daysAgo);

      assertThat(result).isEqualTo(42);
   }

   @Test
   void throwsServiceException() {
      when(service.currentPrice(anyString())).thenThrow(new SecurityException());
      portfolio.purchase("NOK",10);

      assertThrows(ServiceException.class, () -> portfolio.value());
   }
}
