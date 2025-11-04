package com.langrsoft.portfolio;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
        stock.purchase("NOK");
        assertThat(stock.getUniqueStockCount()).isEqualTo(1);
    }

    @Test
    void shouldHandleMultiplePurchasesOfSameStock() {
        stock.purchase("NOK");
        stock.purchase("NOK");

        assertThat(stock.getUniqueStockCount()).isEqualTo(1);
    }

    @Test
    void shouldHandleMultiplePurchasesOfDifferentStock() {
        stock.purchase("NOK");
        stock.purchase("INFN");
        stock.purchase("BLR");
        assertThat(stock.getUniqueStockCount()).isEqualTo(3);
    }

    @Test
    void shouldHandleMultiplePurchasesOfMultipleStock() {
        stock.purchase("NOK");
        stock.purchase("INFN");
        stock.purchase("BLR");
        stock.purchase("NOK");
        stock.purchase("INFN");
        assertThat(stock.getUniqueStockCount()).isEqualTo(3);
    }

    @Test
    void shouldNotAcceptEmptyStockName() {
        assertThatThrownBy(() -> stock.purchase(""))
                .cause()
                .isInstanceOf(InvalidStockNameException.class);
    }
}
