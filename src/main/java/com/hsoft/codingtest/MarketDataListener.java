package com.hsoft.codingtest;

public interface MarketDataListener {
    void transactionOccured(String productId, long quantity, double price);
}
