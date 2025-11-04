package com.langrsoft.stock;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class StockPortfolioTest {

    StockPortfolio stockPortfolio = new StockPortfolio();
    @Test
    public  void noPurchase(){
        Assert.assertEquals(stockPortfolio.getNumberOfStocks(),0);
    }

    @Test void purchase(){
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setNumberOfShares(1);
        stockPortfolio.purchase(stock);
        Assert.assertEquals(stockPortfolio.getNumberOfStocks(),1);
    }

    @Test void manyPurchase(){
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setNumberOfShares(2);
        stockPortfolio.purchase(stock);
        Assert.assertEquals(stockPortfolio.getNumberOfStocks(),2);
    }

    @Test void differentStockPurchase(){
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setNumberOfShares(2);
        stockPortfolio.purchase(stock);

        Stock stock1 = new Stock();
        stock1.setStockName("NOKI");
        stock1.setNumberOfShares(2);
        stockPortfolio.purchase(stock1);
        Assert.assertEquals(stockPortfolio.isManyPurchase(),true);
    }

}
