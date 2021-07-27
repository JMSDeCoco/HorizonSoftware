package com.hsoft.codingtest;

public interface DataProvider {
    public void addMarketDataListener(MarketDataListener marketDataListener);

    public void addPricingDataListener(PricingDataListener pricingDataListener);

    public void listen();
}
