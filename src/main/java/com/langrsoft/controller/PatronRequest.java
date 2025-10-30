package com.langrsoft.controller;

import com.langrsoft.domain.Patron;

public class PatronRequest {
    String name;
    String id;
    int fineBalance;

    public PatronRequest() {
    }

    public PatronRequest(Patron patron) {
        this.id = patron.getId();
        this.name = patron.getName();
        this.fineBalance = patron.fineBalance();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFineBalance(Integer fineBalance) {
        this.fineBalance = fineBalance;
    }

    public int getFineBalance() {
        return fineBalance;
    }

    @Override
    public String toString() {
        return "PatronRequest{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", fineBalance=" + fineBalance +
                '}';
    }
}
