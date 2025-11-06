package com.langrsoft.portfolio;

import com.langrsoft.util.InvalidNameException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class APortfolio {

    IStockPortFolioService mockStockService;
    //StockPortfolio port;

    @BeforeEach
    public void setup() {
        mockStockService = mock(IStockPortFolioService.class);
        /*port = new StockPortfolio(new IStockPortFolioService() {
            @Override
            public int getCurrentPrice(String stockSymbol) {
                return 0;
            }
        });*/
    }

    /*@Test
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


    }*/

    @Test
    public void testPortFolioValueWhenNoShares() {
        mockStockService = new IStockPortFolioService() {
            @Override
            public int getCurrentPrice(String stockSymbol) {
                return 0;
            }
        };
        StockPortfolio port = new StockPortfolio(mockStockService);
        Assert.assertEquals(0, port.getTotalValue());
    }

    @Test
    public void testPortFolioValueWhenSingleSharePurchased() {
        mockStockService = new IStockPortFolioService() {
            @Override
            public int getCurrentPrice(String stockSymbol) {
                return 7;
            }
        };
        StockPortfolio port = new StockPortfolio(mockStockService);
        port.purchase("NOK", 1);
        Assert.assertEquals(7, port.getTotalValue());
    }

    @Test
    public void testPortFolioValueWhenMultipleSharePurchased() {
        mockStockService = new IStockPortFolioService() {
            @Override
            public int getCurrentPrice(String stockSymbol) {
                return 7;
            }
        };
        StockPortfolio port = new StockPortfolio(mockStockService);
        port.purchase("NOK", 10);
        Assert.assertEquals(70, port.getTotalValue());
    }

    final int aaplPrice = 30;
    final int nokPrice = 6;
    @Test
    public void testPortFolioValueWhenDifferentSharePurchased() {
        //mockStockService = mock(IStockPortFolioService.class);
        StockPortfolio port = new StockPortfolio(mockStockService);
        when(mockStockService.getCurrentPrice("AAPL")).thenReturn(aaplPrice);
        when(mockStockService.getCurrentPrice("NOK")).thenReturn(nokPrice);
        port.purchase("NOK", 10);
        port.purchase("AAPL", 20);
        Assert.assertEquals(660, port.getTotalValue());
    }
}
