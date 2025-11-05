package com.langrsoft.exercises.stock;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.assertj.core.api.Assertions.assertThat;

public class UniqueStockUnitTest {
    private UniqueStock uniqueStock;
    /*
     zero stocks bought
     One stock bought
     Many stocks bought
     buy same stock twice
    * */

    @Before
    public void setUp() throws Exception {
        uniqueStock = new UniqueStock();
    }

    @Test
    public void buyAStock(){
        assertThat(uniqueStock.purchaseStock("NOK",10)).isEqualTo(true);
    }

    @Test
    public void buyTheSameStockTwice() {
        assertThat(uniqueStock.purchaseStock("APL",10)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("APL",15)).isEqualTo(true);
        assertThat(uniqueStock.checkStockWalletSymbolCount("APL")).isEqualTo(25);
    }

    @Disabled
    @Test
    public void buyManyStocks(){
        assertThat(uniqueStock.purchaseStock("NOK",10)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("APL",15)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("AMZN",15)).isEqualTo(true);
    }

    @Disabled
    @Test
    public void buyInvalidNumberOfStocks() {
        assertThat(uniqueStock.purchaseStock("NOK", 0)).isEqualTo(false);
    }

    @Disabled
    @Test
    public void buyInvalidStockSymbold() {
        assertThat(uniqueStock.purchaseStock("NO,K", 20)).isEqualTo(false);
    }

    @Disabled
    @Test
    public void checkUniqueStock(){
        assertThat(uniqueStock.checkStockWalletSymbolCount("APL")).isEqualTo(1);
    }
}
