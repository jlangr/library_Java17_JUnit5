package com.langrsoft.portfolio;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class APortfolio {

    StockPortfolio port;
    @BeforeEach
    void instantiate() {
        port = new StockPortfolio();
    }

    @Test
    public void testForZeroPurchase(){
        Assert.assertEquals(0, port.getUniqueCount());
    }

    @Test
    public void testForSinglePurchase(){
        port.purchase("AAPL", 10);
        Assert.assertEquals(1, port.getUniqueCount());

    }

    @Test
    public void testForMultiplePurchaseOfDifferentSymbols(){
        port.purchase("AAPL", 10);
        port.purchase("GOOGL", 5);
        port.purchase("MSFT", 8);
        Assert.assertEquals(3, port.getUniqueCount());

    }


    @Test
    public void testForMultiplePurchaseOfSimilarKindOfSymbols(){
        port.purchase("AAPL", 10);
        port.purchase("AAP", 5);
        Assert.assertEquals(2, port.getUniqueCount());

    }

    @Test
    public void testForMultiplePurchaseOfSameSymbol(){
        port.purchase("AAPL", 10);
        port.purchase("AAPL", 5);
        port.purchase("AAPL", 8);
        Assert.assertEquals(1, port.getUniqueCount());

    }
}
