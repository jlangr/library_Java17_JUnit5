package com.langrsoft.service.library;

public interface CheckCredit  {
    boolean hasCreditValid(String cardNumber);
    boolean hasCreditFailed(String cardNumber);
}
