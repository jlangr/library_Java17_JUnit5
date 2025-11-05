package com.langrsoft.exercises.stock;

import org.junit.Assert;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

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

    @Test
    public void buyManyStocks(){
        UniqueStock uniqueStock = new UniqueStock();

        assertThat(uniqueStock.purchaseStock("NOK",10)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("APL",15)).isEqualTo(true);
        assertThat(uniqueStock.purchaseStock("AMZN",15)).isEqualTo(true);
    }

    @Test
    public void checkUniqueStock(){

    }
}
