/**
 * Represents the data model for the Expense Tracker application which provides a list of functionalities like adding, removing and getting transactions.
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerModel {

  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  /**
   * Adds a transaction to the previously defined set of transactions
   *
   * @param t The transaction to be added.
   */
  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  /**
   * Removes a transaction to the previously defined set of transactions
   *
   * @param t The transaction to be deleted.
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  /**
   * returns a existing set of transactions
   * @return A list containing all managed transactions.
   */
  public List<Transaction> getTransactions() {
    List<Transaction> copyOfTransactions = new ArrayList<>();
    copyOfTransactions.addAll(this.transactions);
    return copyOfTransactions;
  }

}