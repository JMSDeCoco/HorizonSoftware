package com.hsoft.codingtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances create data for all listener.
 *
 */

public class DataProviderFactory {

    static class DefaultDataProvider implements DataProvider {
        List<MarketDataListener> marketDataListeners = new ArrayList<>();
        List<PricingDataListener> pricingDataListeners = new ArrayList<>();
        @Override public void addMarketDataListener(MarketDataListener marketDataListener) {
            this.marketDataListeners.add(marketDataListener);
        }

        @Override public void addPricingDataListener(PricingDataListener pricingDataListener) {
            this.pricingDataListeners.add(pricingDataListener);
        }

        /**
         * Method for listener when a transaction is occured.
         * @param productID
         * @param quantity
         * @param value
         */
        void addMarketData(String productID, int quantity, double value) {
            for (MarketDataListener m:marketDataListeners) {
                m.transactionOccured(productID,quantity,value);
            }
        }

        /**
         * Method for listener when new product with fair value.
         * @param productID
         * @param fairValue
         */
        void addPricingData(String productID, double fairValue) {
            for (PricingDataListener p:pricingDataListeners) {
                p.fairValueChanged(productID,fairValue);
            }
        }

        @Override public void listen() {
            addPricingData("P1",11.0);
            addPricingData("P1",10.9);
            addPricingData("P2",12.5);

            addMarketData("P1", 1000, 10.0);
            addMarketData("P1", 2000, 11.0);
            addMarketData("P2", 500, 12.0);
        }
    }

    /**
     * Singleton to be sure only one thread can work with.s
     */
    private static DataProvider INSTANCE = null;
    public synchronized static DataProvider getDataProvider() {
        if (INSTANCE==null) INSTANCE=new DefaultDataProvider();
        return INSTANCE;
    }
}
