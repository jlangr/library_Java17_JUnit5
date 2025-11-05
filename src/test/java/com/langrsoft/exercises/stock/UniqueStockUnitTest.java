package com.langrsoft.exercises.stock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
        assertThat(uniqueStock.purchaseStock("NOK", 0)).isEqualTo(false);
        assertThat(uniqueStock.purchaseStock("NOK", -5)).isEqualTo(false);

    }

    @Test
    public void buyInvalidStockSymbold() {
        assertThat(uniqueStock.purchaseStock("NO,K", 20)).isEqualTo(false);
        assertThat(uniqueStock.purchaseStock("NO12K", 20)).isEqualTo(false);
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

}
