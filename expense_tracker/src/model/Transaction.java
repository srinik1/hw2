/**
 * Package that defines the model of the Transactions.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the data model for the Transactions which contains the amount, category and timestam fields.
 */
public class Transaction {

  private final double amount;
  private final String category;
  private final String timestamp;

  public Transaction(double amount, String category) {
    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }

  /**
   * @return amount of the transaction
   */
  public double getAmount() {
    return amount;
  }

  /**
   * @return category of the transaction
   */
  public String getCategory() {
    return category;
  }

  /**
   * @return timestamp of the transaction
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * Generates the Timestamp at the time transaction was added
   * @return Formatted timestamp
   */
  private String generateTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");  
    return sdf.format(new Date());
  }

}