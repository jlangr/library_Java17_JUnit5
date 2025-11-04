package com.langrsoft.stock;

import com.langrsoft.util.InvalidNameException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockPortfolioTest {

    StockPortfolio stockPortfolio = new StockPortfolio();

    @Test
    public void noPurchase() {
        Assert.assertEquals(stockPortfolio.getNumberOfStocks(), 0);
    }

    @Test
    void purchase() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setNumberOfShares(1);
        stockPortfolio.purchase(stock);
        Assert.assertEquals(stockPortfolio.getNumberOfStocks(), 1);
    }

    @Test
    void manyPurchase() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setNumberOfShares(2);
        stockPortfolio.purchase(stock);
        Assert.assertEquals(stockPortfolio.getNumberOfStocks(), 2);
    }

    @Test
    void differentStockPurchase() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setNumberOfShares(2);
        stockPortfolio.purchase(stock);

        Stock stock1 = new Stock();
        stock1.setStockName("NOKI");
        stock1.setNumberOfShares(2);
        stockPortfolio.purchase(stock1);
        Assert.assertEquals(stockPortfolio.isManyPurchase(), true);
    }

    @Test
    void getStockCount() throws InvalidStockQtyException {
        Stock stock1 = new Stock();
        stock1.setStockName("NOK");
        stock1.setNumberOfShares(2);
        stockPortfolio.purchase(stock1);
        Assert.assertEquals(stockPortfolio.getStockCount("NOK"), 2);
    }

    @Test
    void rejectInvalidStockQty() {
        Stock stock1 = new Stock();
        stock1.setStockName("NOK");
        stock1.setNumberOfShares(0);
        var thrown = assertThrows(InvalidStockQtyException.class, () ->
                stockPortfolio.purchase(stock1));
        assertThat(thrown.getMessage())
                .isEqualTo("Invalid stock quantity");
    }
}
