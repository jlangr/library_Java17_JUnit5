package com.langrsoft.portfolio;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class APortfolio {

    IStockPortFolioService mockStockService;
    IAuditor mockAuditor;
    StockPortfolio port;
    final int aaplPrice = 30;
    final int nokPrice = 6;

    @BeforeEach
    void setup() {
        mockStockService = mock(IStockPortFolioService.class);
        mockAuditor = mock(IAuditor.class);
        port = new StockPortfolio(mockStockService, mockAuditor);
    }

    @Test
    public void testForZeroPurchase(){
        Assertions.assertEquals(0, port.getUniqueCount());
    }

    @Test
    public void testForSinglePurchase(){
        port.purchase("AAPL", 10);
        Assertions.assertEquals(1, port.getUniqueCount());

    }

    @Test
    public void testForMultiplePurchaseOfDifferentSymbols(){
        port.purchase("AAPL", 10);
        port.purchase("GOOGL", 5);
        port.purchase("MSFT", 8);
        Assertions.assertEquals(3, port.getUniqueCount());

    }


    @Test
    public void testForMultiplePurchaseOfSimilarKindOfSymbols(){
        port.purchase("AAPL", 10);
        port.purchase("AAP", 5);
        Assertions.assertEquals(2, port.getUniqueCount());

    }

    @Test
    public void testForMultiplePurchaseOfSameSymbol(){
        port.purchase("AAPL", 10);
        port.purchase("AAPL", 5);
        port.purchase("AAPL", 8);
        Assertions.assertEquals(1, port.getUniqueCount());

    }

    //Z check for 0 value for symbol not purchased
    //O Purchase 1 share for a symbol and check if we get value 1
    //M Purchase multiple share with different number and check the value
    //M Purchase multiple time same share and check the value
    //B Purchase stock and add non valid value or 0. Check for exception
    //E validate the message in custom Exception

    @Test
    public void tesForNoSharesPurchased(){
        Assertions.assertEquals(0, port.getShareCount("AAPL"));
    }

    @Test
    public void tesForOneSharePurchase(){
        port.purchase("TSLA", 1);
        Assertions.assertEquals(1, port.getShareCount("TSLA"));
    }

    @Test
    public void testForMultipleSharePurchase(){
        port.purchase("TSLA", 5);
        port.purchase("TSLA", 6);
        Assertions.assertEquals(11, port.getShareCount("TSLA"));
    }

    @Test
    public void testForMultipleSharesPurchase(){
        port.purchase("TSLA", 5);
        port.purchase("AAPL", 10);
        //port.purchase("X", 0);
        Assertions.assertEquals(10, port.getShareCount("AAPL"));
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

    @Test
    public void testPortFolioValueWhenNoShares() {
        Assertions.assertEquals(0, port.getTotalValue());
    }

    @Test
    public void testPortFolioValueWhenSingleSharePurchased() {
        when(mockStockService.getCurrentPrice("NOK")).thenReturn(nokPrice);
        port.purchase("NOK", 1);
        Assertions.assertEquals(6, port.getTotalValue());
    }

    @Test
    public void testPortFolioValueWhenMultipleSharePurchased() {
        when(mockStockService.getCurrentPrice("NOK")).thenReturn(nokPrice);
        port.purchase("NOK", 10);
        Assertions.assertEquals(60, port.getTotalValue());
    }


    @Test
    public void testPortFolioValueWhenDifferentSharePurchased() {
        when(mockStockService.getCurrentPrice("AAPL")).thenReturn(aaplPrice);
        when(mockStockService.getCurrentPrice("NOK")).thenReturn(nokPrice);

        port.purchase("NOK", 10);
        port.purchase("AAPL", 20);
        Assertions.assertEquals(660, port.getTotalValue());
    }

    @Test
    public void verifyAuditLogAfterPurchase() {
        when(mockStockService.getCurrentPrice("NOK")).thenReturn(nokPrice);

        port.purchase("NOK", 10);
        verify(mockAuditor).logPurchase("NOK", 10);
        Assertions.assertEquals(60, port.getTotalValue());
    }

    @Test
    public void verifyAuditLogAfterMultiplePurchases() {
        when(mockStockService.getCurrentPrice("NOK")).thenReturn(nokPrice);
        when(mockStockService.getCurrentPrice("AAPL")).thenReturn(aaplPrice);

        port.purchase("NOK", 10);
        port.purchase("AAPL", 20);
        verify(mockAuditor, times(2)).logPurchase(anyString(), anyInt());
        Assertions.assertEquals(660, port.getTotalValue());
    }
}
