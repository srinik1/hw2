/**
 * Package to control the expense tracker application.
 */
package controller;

import model.filter.AmountFilter;
import model.filter.CategoryFilter;
import model.filter.TransactionFilter;
import view.ExpenseTrackerView;
import java.util.List;
import model.ExpenseTrackerModel;
import model.Transaction;

import javax.swing.tree.TreeNode;

/**
 * Controller for the ExpenseTracker application.
 * <p>
 * This class is used to handle the user inputs, and interacting and updating the model.
 * </p>
 */
public class ExpenseTrackerController {
  
  /** The model instance of the Expense Tracker application. */
  private ExpenseTrackerModel model;
  /** The view instance of the Expense Tracker application. */
  private ExpenseTrackerView view;
  /** Filter to use on the transactions view */
  private TransactionFilter filter;

  /**
   * Constructs a new ExpenseTrackerController with arguments as the model and view.
   *
   * @param model The model for this controller.
   * @param view  The view for this controller.
   */
  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  /**
   * This is used to update the transactions view with any new inserted transactions.
   */
  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions, filter);

  }

  /**
   * Adds a new transaction when the validation checks for amount and category have been checked with the entered amount and category.
   *
   * @param amount   Amount entered for transaction.
   * @param category Category of the transaction.
   * @return {@code true} if the values are validated and transaction has been added; {@code false} otherwise.
   */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  /**
   * Applies a amount or category filter to the transactions based on the view selected by the user.
   */
  public void applyFilter(){
    refresh();
    String filterType = view.getSelectedFilterType();
    String filterValue = view.getFilterValue();

    if(filterType == "Amount"){
      try{
        double amount = Double.parseDouble(filterValue);
        if(controller.InputValidation.isValidAmount(amount)){
          filter = new AmountFilter(amount);
        }else{
          view.invalidAmountInput();
        }
      }catch (NumberFormatException e){
        System.out.println("Invalid Amount format");
      }
    } else if (filterType == "Category") {
        if(controller.InputValidation.isValidCategory(filterValue)){
          filter = new CategoryFilter(filterValue);
        }else{
          view.invalidCategoryInput();
        }
    }else{
      System.out.println("Unknown Filter type");
    }

    view.refreshTable(model.getTransactions(), filter);
  }
}