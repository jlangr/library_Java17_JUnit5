package com.langrsoft.portfolio;

import java.util.Objects;

public class StockInfo {

    private String name;
    private int shares;

    public StockInfo(String name, int shares) {
        this.name = name;
        this.shares = shares;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockInfo stockInfo)) return false;
        return Objects.equals(getName(), stockInfo.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "name='" + name + '\'' +
                ", shares=" + shares +
                '}';
    }
}
