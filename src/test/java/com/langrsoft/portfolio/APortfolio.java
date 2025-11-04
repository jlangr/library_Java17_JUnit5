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

    }

    @Test
    public void testForSinglePurchase(){
        port.purchase("AAPL", 10);
        Assert.assertEquals(1, port.getUniqueCount());

    }
}
