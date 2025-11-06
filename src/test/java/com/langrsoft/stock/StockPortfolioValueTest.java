package com.langrsoft.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StockPortfolioValueTest {

    IStockExchange stockExchange;
    StockPortfolio stockPortfolio;
    Map<String, Integer> stockValue = new HashMap<>();

    @BeforeEach
    void create() {
        stockValue.put("NOK", 10);
        stockValue.put("APPLE", 20);

        stockExchange = new IStockExchange() {
            @Override
            public int getPrice(String stockName) {
                return stockValue.get(stockName);
            }
        };
        stockPortfolio = new StockPortfolio(stockExchange);
    }

    @Test
    void zeroStockPortfolio() {
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(0);
    }

    @Test
    void oneStockPortfolio() throws InvalidStockQtyException {
        Stock stock = new Stock();
        stock.setStockName("NOK");
        stock.setStockQty(1);
        stockPortfolio.purchase(stock);
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(10);
    }

    @Test
    void multiPurchaseSameStockPortfolio() throws InvalidStockQtyException {
        Stock purchase1 = new Stock();
        purchase1.setStockName("NOK");
        purchase1.setStockQty(1);
        stockPortfolio.purchase(purchase1);
        Stock purchase2 = new Stock();
        purchase2.setStockName("NOK");
        purchase2.setStockQty(10);
        stockPortfolio.purchase(purchase2);
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(110);
    }

    @Test
    void differentStockPortfolio() throws InvalidStockQtyException {
        Stock purchase1 = new Stock();
        purchase1.setStockName("NOK");
        purchase1.setStockQty(1);
        stockPortfolio.purchase(purchase1);
        Stock purchase2 = new Stock();
        purchase2.setStockName("APPLE");
        purchase2.setStockQty(1);
        stockPortfolio.purchase(purchase2);
        assertThat(stockPortfolio.getPortfolioValue()).isEqualTo(30);
    }
}
