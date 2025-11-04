package com.langrsoft.portfolio;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class APortfolio {

    StockPortfolio port = new StockPortfolio();

    @BeforeEach
    public void instantiate() {
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

    //Z check for 0 value for symbol not purchased
    //O Purchase 1 share for a symbol and check if we get value 1
    //M Purchase multiple share wiht different number and check the value
    //M Purchase multiple time same share and check the value
    //B Purchase stock and add non valid value or 0. Check for exception
    //E validate the message in custom Exception

    @Test
    public void tesForNoSharesPurchased(){
        Assert.assertEquals(0, port.getShareCount("AAPL"));
    }

    @Test
    public void tesForOneSharePurchase(){
        port.purchase("TSLA", 1);
        Assert.assertEquals(1, port.getShareCount("TSLA"));
    }
}
