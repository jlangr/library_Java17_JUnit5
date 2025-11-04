package com.langrsoft.portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class APortfolio {

    StockPortfolio stock;

    @BeforeEach
    void initialize() {
        stock = new StockPortfolio();
    }
    @Test
    void shouldReturnEmptyInventoryWhenNoPurchases() {
        assertThat(stock.getUniqueStockCount()).isEqualTo(0);
    }

    @Test
    void shouldReturnOneStockWhenOnePurchaseIsMade() {
        stock.purchase("NOK", 30);
        assertThat(stock.getUniqueStockCount()).isEqualTo(1);
    }

    @Test
    void shouldHandleMultiplePurchasesOfSameStock() {
        stock.purchase("NOK", 30);
        stock.purchase("NOK", 30);

        assertThat(stock.getUniqueStockCount()).isEqualTo(1);
    }

    @Test
    void shouldHandleMultiplePurchasesOfDifferentStock() {
        stock.purchase("NOK", 30);
        stock.purchase("INFN", 30);
        stock.purchase("BLR", 30);
        assertThat(stock.getUniqueStockCount()).isEqualTo(3);
    }

    @Test
    void shouldHandleMultiplePurchasesOfMultipleStock() {
        stock.purchase("NOK", 30);
        stock.purchase("INFN", 30);
        stock.purchase("BLR", 30);
        stock.purchase("NOK", 30);
        stock.purchase("INFN", 30);
        assertThat(stock.getUniqueStockCount()).isEqualTo(3);
    }

    @Test
    void shouldNotAcceptEmptyStockName() {
        var thrown = assertThrows(InvalidStockException.class, () ->
                stock.purchase("", 0));
        assertThat(thrown.getMessage())
                .isEqualTo(StockPortfolio.STOCK_NAME_ERROR);
    }

    @Test
    void shouldPurchaseOneStockWithCount() {
        stock.purchase("NOK", 30);
        assertThat(stock.getUniqueStockCount()).isEqualTo(1);
        assertThat(stock.getCountForGivenStock("NOK")).isEqualTo(30);
    }

    @Test
    void shouldReturnCountAfterMultiplePurchases() {
        stock.purchase("NOK", 30);
        stock.purchase("INFN", 50);
        assertThat(stock.getCountForGivenStock("NOK")).isEqualTo(30);
        assertThat(stock.getCountForGivenStock("INFN")).isEqualTo(50);
    }

    @Test
    void shouldReturnTotalCountAfterMultiplePurchasesOfSameStock() {
        stock.purchase("NOK", 30);
        stock.purchase("NOK", 50);
        assertThat(stock.getCountForGivenStock("NOK")).isEqualTo(80);
    }

    @Test
    void shouldThrowInvalidBuyCountForZeroOrNegativePurchase() {
        var thrownZero = assertThrows(InvalidStockException.class, () ->
                stock.purchase("NOK", 0));
        var thrownNegative = assertThrows(InvalidStockException.class, () ->
                stock.purchase("NOK", -10));
        assertThat(thrownZero.getMessage())
                .isEqualTo(StockPortfolio.STOCK_COUNT_ERROR);
        assertThat(thrownNegative.getMessage())
                .isEqualTo(StockPortfolio.STOCK_COUNT_ERROR);

    }
}
