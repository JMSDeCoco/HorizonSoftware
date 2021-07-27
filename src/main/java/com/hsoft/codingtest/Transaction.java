package com.hsoft.codingtest;

/**
 * Instances of this classget informations of all transactions.
 */
public class Transaction {

    String productID;
    long quantity;
    double price;

    public Transaction(String pProductID, long pQuantity, double pPrice){
        this.productID = pProductID;
        this.quantity = pQuantity;
        this.price = pPrice;
    }
}
