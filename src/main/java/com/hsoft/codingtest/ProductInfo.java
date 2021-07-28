package com.hsoft.codingtest;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Instances of this class keep runtime informations and help compute vwap related informations.
 */
public class ProductInfo {
    private static HashMap<String, ProductInfo> productList = new HashMap<>();

    public final String productId;
    private double VWAPValue;
    private double fairValue;
    private static final Logger logger = Logger.getLogger("TEST_PRODUCT");
    //List of all transaction
    List<Transaction> listTransaction = new ArrayList<>();

    /**
     * Create a product info for the specified product ID
     * @param productId the product ID
     */
    public ProductInfo(String productId){
        this.productId=productId;
    }

    //Method to test if the list already had 5 transaction

    /**
     * Add a transaction to the list of last 5 transactions for this product.
     * @param quantity quantity of product
     * @param price product price
     * @return the difference between the vwap and the fair value
     */
    public double addTransaction(long quantity, double price){
        if(listTransaction.size() == 5){
            listTransaction.remove(0);
        }
        listTransaction.add(new Transaction(productId, quantity, price));
        // update VWAPValue
        this.VWAPValue = calcVWAP();
        logger.debug("VWAP for " + productId + " = " + VWAPValue);
        return VWAPValue - this.fairValue;
    }

    /**
     * Calculate the VWAP of the current product.
     * @return
     */
    private double calcVWAP(){
        double res = 0;
        double sumQuantity = 0;
        for(Transaction transaction : listTransaction){
            res += (transaction.price * transaction.quantity);
            sumQuantity += transaction.quantity;
        }
        res = res / sumQuantity;
        return res;
    }

    public double setFairValue(double newFairValue) {
        this.fairValue=newFairValue;
        return VWAPValue - this.fairValue;
    }

    /**
     * Get product info and replace if already exist or create new.
     * @param productId the product ID
     * @return
     */
    public static ProductInfo getProductInfo(String productId) {
        ProductInfo productInfo = productList.get(productId);
        if(productInfo ==null) {
            productInfo = new ProductInfo(productId);
            productList.put(productId, productInfo);
        }
        return productInfo;
    }

    public double getVWAPValue(){
        return VWAPValue;
    }

    public double getFairValue() {
        return fairValue;
    }
}
