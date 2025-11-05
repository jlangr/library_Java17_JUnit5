package com.langrsoft.exercises.stock;

import org.junit.Before;
import org.junit.Test;

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
        uniqueStock.purchaseStock("NOK", 10);
        uniqueStock.purchaseStock("APL", 15);
        uniqueStock.purchaseStock("AMZN", 25);

        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);
        assertThat(uniqueStock.checkStockCount("APL")).isEqualTo(15);
        assertThat(uniqueStock.checkStockCount("AMZN")).isEqualTo(25);
    }

    @Test
    public void buyInvalidNumberOfStocks() {
        String expectedErrorMessage = "Number of shares must be positive.";

        var ex1 = assertThrows(WrongShareAmount.class, () -> uniqueStock.purchaseStock("NOK", 0));
        assertThat(ex1.getMessage()).isEqualTo(expectedErrorMessage);

        var ex2 = assertThrows(WrongShareAmount.class, () -> uniqueStock.purchaseStock("NOK", -5));
        assertThat(ex2.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    public void buyInvalidStockSymbol() {
        String expectedErrorMessage = "Wrong symbol.";

        var thrownWhenSymbolWithComma = assertThrows(StockNotAvailable.class, () -> uniqueStock.purchaseStock("NO,K", 20));
        assertThat(thrownWhenSymbolWithComma.getMessage()).isEqualTo(expectedErrorMessage);

        var thrownWhenSymbolWithNumbers = assertThrows(StockNotAvailable.class, () -> uniqueStock.purchaseStock("NO12K", 20));
        assertThat(thrownWhenSymbolWithNumbers.getMessage()).isEqualTo(expectedErrorMessage);

        var thrownWhenSymbolEmpty = assertThrows(StockNotAvailable.class, () -> uniqueStock.purchaseStock("", 20));
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

    @Test
    public void emptyWallet() {
        assertThat(uniqueStock.checkEmptyWallet()).isTrue();
        uniqueStock.purchaseStock("APL", 20);
        assertThat(uniqueStock.checkEmptyWallet()).isFalse();
    }

    @Test
    public void sellStock() {
        uniqueStock.purchaseStock("NOK", 10);
        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);
        assertThat(uniqueStock.sellStock("NOK", 10)).isTrue();
        assertThat(uniqueStock.checkEmptyWallet()).isTrue();
    }

    @Test
    public void sellMoreSharesThanAvailable() {
        uniqueStock.purchaseStock("NOK", 10);
        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);

        var thrownWhenMoreSharesThanAvailable = assertThrows(WrongShareAmount.class, () -> uniqueStock.sellStock("NOK", 20));
        assertThat(thrownWhenMoreSharesThanAvailable.getMessage()).isEqualTo("Not enough shares to sell.");

        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);
    }

    @Test
    public void sellStockNotOwned() {
        String expectedErrorMessage = "Stock not available in wallet.";

        assertThat(uniqueStock.checkEmptyWallet()).isTrue();

        var thrownWhenStockNotOwned = assertThrows(StockNotAvailable.class, () -> uniqueStock.sellStock("APL", 20));
        assertThat(thrownWhenStockNotOwned.getMessage()).isEqualTo(expectedErrorMessage);

        uniqueStock.purchaseStock("NOK", 10);
        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);

        var thrownWhenStockNotOwnedAgain = assertThrows(StockNotAvailable.class, () -> uniqueStock.sellStock("APL", 20));
        assertThat(thrownWhenStockNotOwnedAgain.getMessage()).isEqualTo(expectedErrorMessage);

        assertThat(uniqueStock.checkStockCount("NOK")).isEqualTo(10);
    }
}