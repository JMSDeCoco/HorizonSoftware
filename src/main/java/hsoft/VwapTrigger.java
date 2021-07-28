package hsoft;

import com.hsoft.codingtest.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;


public class VwapTrigger {

  private static final Logger logger = Logger.getLogger("TEST_PRODUCT");

  public static void main(String[] args) {
    /**
     * Listener for all new transactions.
     */
    DataProvider provider = DataProviderFactory.getDataProvider();
    provider.addMarketDataListener(new MarketDataListener() {
      public void transactionOccured(String productId, long quantity, double price) {
        ProductInfo pi = ProductInfo.getProductInfo(productId);
        // TODO Start to code here when a transaction occurred
        double diffValue = pi.addTransaction(quantity, price);
        if (diffValue>0) {
          logValueHigherThanFair(pi);
        }
      }
    });

    /**
     * Listener for fair value changed.
     */
    provider.addPricingDataListener(new PricingDataListener() {
      public void fairValueChanged(String productId, double fairValue) {
        ProductInfo pi = ProductInfo.getProductInfo(productId);
        // TODO Start to code here when a fair value changed
        double diffValue = pi.setFairValue(fairValue);
        if (diffValue>0)  logValueHigherThanFair(pi);
      }
    });

    provider.listen();
    // When this method returns, the test is finished and you can check your results
  }

  /**
   * Compare values of fair and VWAP to compare them and log them.
   * @param pi Product info
   */
  public static void logValueHigherThanFair(ProductInfo pi){
    if(pi.getVWAPValue() > pi.getFairValue()){

      ConsoleAppender appender = (ConsoleAppender)logger.getAppender("ConsoleAppender");
      logger.addAppender(appender);

      logger.debug("VWAP (" + pi.getVWAPValue() + ") > FairValue (" + pi.getFairValue() +") ");
    }

  }
}