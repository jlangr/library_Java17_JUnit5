package com.langrsoft.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
   void totalsValuesForAllSymbols() {
      when(service.currentPrice("NOK")).thenReturn(NOKIA_CURRENT_PRICE);
      when(service.currentPrice("AAPL")).thenReturn(APPLE_CURRENT_PRICE);

      portfolio.purchase("NOK",42);
      portfolio.purchase("AAPL",10);

      assertThat(portfolio.value()).isEqualTo(NOKIA_CURRENT_PRICE * 42 + APPLE_CURRENT_PRICE * 10);
   }
}
