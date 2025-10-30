package com.langrsoft.util;

import java.time.Clock;
import java.time.Instant;
import java.util.function.Supplier;

public record TradeTransaction(String symbol,
                               int shares,
                               Instant timestamp,
                               String brokerageId,
                               String exchange,
                               String tradeId) {
   public TradeTransaction(String symbol, int shares, String exchange) {
      this(
         symbol,
         shares,
         clock.instant(),
         "BR-123309",
         exchange,
         uuidSource.get());
   }

   // a lazy approach to dependency injection. Not for production use!
   public static Clock clock = Clock.systemUTC();
   public static Supplier<String> uuidSource =
      () -> java.util.UUID.randomUUID().toString();
}
