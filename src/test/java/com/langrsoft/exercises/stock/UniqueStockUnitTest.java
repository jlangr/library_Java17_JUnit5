package com.langrsoft.exercises.stock;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.assertj.core.api.Assertions.assertThat;

public class UniqueStockUnitTest {
    /*
     zero stocks bought
     One stock bought
     Many stocks bought
     buy same stock twice
    * */

    @Test
    public void buyAStock(){
        UniqueStock uniqueStock = new UniqueStock();

        assertThat(uniqueStock.purchaseStock("NOK",10)).isEqualTo(true);
    }

    @Disabled
    @Test
    public void buyManyStocks(){
        UniqueStock uniqueStock = new UniqueStock();

        assertThat(uniqueStock.purchaseStock("NOK",10)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("APL",15)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("AMZN",15)).isEqualTo(true);
    }

    @Disabled
    @Test
    public void buyTheSameStockTwice() {
        UniqueStock uniqueStock = new UniqueStock();
        assertThat(uniqueStock.purchaseStock("APL",10)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("APL",15)).isEqualTo(true);
    }

    @Disabled
    @Test
    public void buyInvalidNumberOfStocks() {
        UniqueStock uniqueStock = new UniqueStock();

        assertThat(uniqueStock.purchaseStock("NOK", 0)).isEqualTo(false);

    }

    @Disabled
    @Test
    public void buyInvalidStockSymbold() {
        UniqueStock uniqueStock = new UniqueStock();
        assertThat(uniqueStock.purchaseStock("NO,K", 20)).isEqualTo(false);
    }

    @Disabled
    @Test
    public void checkUniqueStock(){
        UniqueStock uniqueStock = new UniqueStock();
        assertThat(uniqueStock.checkStockWalletSymbolCount()).isEqualTo(1);
    }
}
