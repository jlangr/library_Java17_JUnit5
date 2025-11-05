package com.langrsoft.exercises.stock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UniqueStockUnitTest {
    private UniqueStock uniqueStock;

    @Before
    public void setUp() {
        uniqueStock = new UniqueStock();
    }

    @Test
    public void buyAStock() {
        uniqueStock.purchaseStock("NOK", 10);
        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);
    }

    @Test
    public void buyTheSameStockTwice() {
        uniqueStock.purchaseStock("APL", 10);
        uniqueStock.purchaseStock("APL", 15);

        assertThat(uniqueStock.checkStockCount("APL")).isEqualTo(25);
    }

    @Test
    public void buyManyStocksAndValidateWallet() {
        Map<String, Integer> expectedStockNumber = new HashMap<>();

        expectedStockNumber.put("NOK", 10);
        expectedStockNumber.put("APL", 15);
        expectedStockNumber.put("AMZN", 25);

        uniqueStock.purchaseStock("NOK", 10);
        uniqueStock.purchaseStock("APL", 15);
        uniqueStock.purchaseStock("AMZN", 25);

        for (String key : expectedStockNumber.keySet()) {
            assertThat(uniqueStock.checkStockCount(key)).isEqualTo(expectedStockNumber.get(key));
        }
    }

    @Test
    public void buyInvalidNumberOfStocks() {
        String expectedErrorMessage = "Number of shares must be positive.";

        var thrownWhenZeroShares = assertThrows(WrongShareAmount.class,
                () -> uniqueStock.purchaseStock("NOK", 0));
        assertThat(thrownWhenZeroShares.getMessage()).isEqualTo(expectedErrorMessage);

        var thrownWhenNegativeShares = assertThrows(WrongShareAmount.class,
                () -> uniqueStock.purchaseStock("NOK", -5));
        assertThat(thrownWhenNegativeShares.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    public void buyInvalidStockSymbol() {
        String expectedErrorMessage = "Wrong symbol.";

        var thrownWhenSymbolWithComma = assertThrows(StockNotAvailable.class,
                () -> uniqueStock.purchaseStock("NO,K", 20));
        assertThat(thrownWhenSymbolWithComma.getMessage()).isEqualTo(expectedErrorMessage);

        var thrownWhenSymbolWithNumbers = assertThrows(StockNotAvailable.class,
                () -> uniqueStock.purchaseStock("NO12K", 20));
        assertThat(thrownWhenSymbolWithNumbers.getMessage()).isEqualTo(expectedErrorMessage);

        var thrownWhenSymbolEmpty = assertThrows(StockNotAvailable.class,
                () -> uniqueStock.purchaseStock("", 20));
        assertThat(thrownWhenSymbolEmpty.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    public void checkUniqueStockSymbol() {
        uniqueStock.purchaseStock("NOK", 10);
        assertThat(uniqueStock.checkUniqueStockSymbolCount()).isEqualTo(1);

        uniqueStock.purchaseStock("NOK", 15);
        assertThat(uniqueStock.checkUniqueStockSymbolCount()).isEqualTo(1);

        uniqueStock.purchaseStock("APL", 20);
        assertThat(uniqueStock.checkUniqueStockSymbolCount()).isEqualTo(2);
    }

    @Ignore("Not implemented yet")
    @Test
    public void emptyWallet() {
        assertThat(uniqueStock.checkEmptyWallet()).isTrue();
        uniqueStock.purchaseStock("APL", 20);
        assertThat(uniqueStock.checkEmptyWallet()).isFalse();
    }

    @Ignore("Not implemented yet")
    @Test
    public void sellStock() {
        //TODO: Check wallet in the end and Negative Scenarios
        assertThat(uniqueStock.sellStock("NOK", 20)).isTrue();
    }

}
