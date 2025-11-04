package com.langrsoft.stock;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockPortfolioTest {

    StockPortfolio stockPortfolio;

    @BeforeEach
    void create() {
        stockPortfolio = new StockPortfolio();
    }

    @Test
    public void noPurchase() {
        Assert.assertEquals(stockPortfolio.getUniqueNumberOfStocks(), 0);
    }

    @Test
    void purchase() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setStockQty(1);
        stockPortfolio.purchase(stock);
        Assert.assertEquals(stockPortfolio.getUniqueNumberOfStocks(), 1);
    }

    @Test
    void manyPurchase() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setStockQty(2);
        stockPortfolio.purchase(stock);
        Assert.assertEquals(stockPortfolio.getUniqueNumberOfStocks(), 1);
    }

    @Test
    void differentStockPurchase() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setStockQty(2);
        stockPortfolio.purchase(stock);

        Stock stock1 = new Stock();
        stock1.setStockName("NOKI");
        stock1.setStockQty(2);
        stockPortfolio.purchase(stock1);
        Assert.assertEquals(stockPortfolio.isManyPurchase(), true);
    }

    @Test
    void getStockCountWhenNoStock() {
        Assert.assertEquals(stockPortfolio.getStockCount("AAPL"), 0);
    }

    @Test
    void getStockCount() throws InvalidStockQtyException {
        Stock stock1 = new Stock();
        stock1.setStockName("NOK");
        stock1.setStockQty(2);
        stockPortfolio.purchase(stock1);
        Assert.assertEquals(stockPortfolio.getStockCount("NOK"), 2);
    }

    @Test
    void getStockCountWhenMultiplePurchase() throws InvalidStockQtyException {
        Stock purchase1 = new Stock();
        purchase1.setStockName("NOK");
        purchase1.setStockQty(2);
        stockPortfolio.purchase(purchase1);
        Stock purchase2 = new Stock();
        purchase2.setStockName("NOK");
        purchase2.setStockQty(2);
        stockPortfolio.purchase(purchase2);
        Assert.assertEquals(stockPortfolio.getStockCount("NOK"), 4);
    }

    @Test
    void rejectInvalidStockQty() {
        Stock stock1 = new Stock();
        stock1.setStockName("NOK");
        stock1.setStockQty(0);
        var thrown = assertThrows(InvalidStockQtyException.class, () ->
                stockPortfolio.purchase(stock1));
        assertThat(thrown.getMessage())
                .isEqualTo("Invalid stock quantity");
    }
}
