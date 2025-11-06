package com.langrsoft.stock;

import com.langrsoft.util.NotYetImplementedException;

public class INYSC implements IStockExchange {
    @Override
    public int getPrice(String stockName) {
        throw new NotYetImplementedException();
    }
}
