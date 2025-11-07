package com.langrsoft.service.library;

public class CheckCreditStub implements CheckCredit {
    public boolean hasCreditValid(String cardNumber){
        return false;
    }

    public boolean hasCreditFailed(String cardNumber){
        return false;
    }
}

