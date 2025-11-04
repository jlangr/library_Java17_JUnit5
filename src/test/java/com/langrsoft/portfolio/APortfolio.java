package com.langrsoft.portfolio;

import com.langrsoft.util.InvalidNameException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    //M Purchase multiple share with different number and check the value
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

    @Test
    public void testForMultipleSharePurchase(){
        port.purchase("TSLA", 5);
        port.purchase("TSLA", 6);
        Assert.assertEquals(11, port.getShareCount("TSLA"));
    }

    @Test
    public void testForMultipleSharesPurchase(){
        port.purchase("TSLA", 5);
        port.purchase("AAPL", 10);
        //port.purchase("X", 0);
        Assert.assertEquals(10, port.getShareCount("AAPL"));
        //Assert.assertEquals(5, port.getShareCount("TSLA"));
    }

    @Test
    public void testForInvalidSharesCountPurchase(){


        var thrown = assertThrows(InvalidParameterException.class, () ->
                port.purchase("TSLA", 0));
        assertThat(thrown.getMessage())
                .isEqualTo("Purchase count should be more than 0");


    }


    @Test
    public void testForNegativeSharesCountPurchase(){


        var thrown = assertThrows(InvalidParameterException.class, () ->
                port.purchase("TSLA", -1));
        assertThat(thrown.getMessage())
                .isEqualTo("Purchase count should be more than 0");


    }
}
