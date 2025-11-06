package com.langrsoft.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class StockPortfolioValueTest {

    IAuditor auditor;
    IStockExchange stockExchange;
    StockPortfolio stockPortfolio;

    @BeforeEach
    void create() {
        auditor = mock(IAuditor.class);
        stockExchange = mock(IStockExchange.class);
        stockPortfolio = new StockPortfolio(auditor, stockExchange);
    }

    @Test
    void zeroStockPortfolio() {
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(0);
    }

    @Test
    void oneStockPortfolio() throws InvalidStockQtyException {
        when(stockExchange.getPrice("NOK")).thenReturn(10);
        Stock stock = new Stock().setStockName("NOK").setStockQty(1);
        stockPortfolio.purchase(stock);
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(10);
    }

    @Test
    void oneStockPurchaseVerify() throws InvalidStockQtyException {
        when(stockExchange.getPrice("NOK")).thenReturn(10);
        Stock stock = new Stock().setStockName("NOK").setStockQty(1);
        stockPortfolio.purchase(stock);
        verify(auditor).logPurchase(stock.stockName, stock.stockQty);
    }

    @Test
    void multiPurchaseSameStockPortfolio() throws InvalidStockQtyException {
        when(stockExchange.getPrice("NOK")).thenReturn(10);
        Stock purchase1 = new Stock().setStockName("NOK").setStockQty(1);
        stockPortfolio.purchase(purchase1);
        Stock purchase2 = new Stock().setStockName("NOK").setStockQty(10);
        stockPortfolio.purchase(purchase2);
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(110);
    }

    @Test
    void differentStockPortfolio() throws InvalidStockQtyException {
        when(stockExchange.getPrice("NOK")).thenReturn(20);
        when(stockExchange.getPrice("APPLE")).thenReturn(40);
        Stock purchase1 = new Stock().setStockName("NOK").setStockQty(1);
        stockPortfolio.purchase(purchase1);
        Stock purchase2 = new Stock().setStockName("APPLE").setStockQty(1);
        stockPortfolio.purchase(purchase2);
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(60);
    }
}
