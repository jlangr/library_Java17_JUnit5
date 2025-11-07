package com.langrsoft.service.library;

public class CheckCreditStub implements CheckCredit {
    public boolean hasCreditValid(String cardNumber){
        return true;
    }

    public boolean hasCreditFailed(String cardNumber){
        return false;
    }
}

