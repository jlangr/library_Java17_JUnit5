package com.langrsoft.stock;

public class InvalidStockQtyException extends  Exception{
    public InvalidStockQtyException(String message) {
        super(message);
    }
}
