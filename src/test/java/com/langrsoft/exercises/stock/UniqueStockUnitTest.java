package com.langrsoft.exercises.stock;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.util.HashMap;
import java.util.Map;

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
    public void setUp() {
        uniqueStock = new UniqueStock();
    }

    @Test
    public void buyAStock(){
        assertThat(uniqueStock.purchaseStock("NOK",10)).isEqualTo(true);
    }

    @Test
    public void buyTheSameStockTwice() {
        uniqueStock.purchaseStock("APL",10);
        uniqueStock.purchaseStock("APL",15);

        assertThat(uniqueStock.checkStockWalletSymbolCount("APL")).isEqualTo(25);
    }

    @Test
    public void buyManyStocks(){
        Map<String,Integer> expectedStockNumber = new HashMap<>();

        expectedStockNumber.put("NOK", 10);
        expectedStockNumber.put("APL", 15);
        expectedStockNumber.put("AMZN", 25);

        uniqueStock.purchaseStock("NOK",10);
        uniqueStock.purchaseStock("APL",15);
        uniqueStock.purchaseStock("AMZN",25);

        for (String key : expectedStockNumber.keySet()){
            assertThat(uniqueStock.checkStockWalletSymbolCount(key)).isEqualTo(expectedStockNumber.get(key));
        }
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
