/**
 * Package for controlling the expense tracker.
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
 * This class is responsible for handling user inputs, updating the model, and refreshing the view.
 * </p>
 */
public class ExpenseTrackerController {
  
  /** The model instance of the Expense Tracker application. */
  private ExpenseTrackerModel model;
  /** The view instance of the Expense Tracker application. */
  private ExpenseTrackerView view;
  /** Current filter applied to transactions in the view. */
  private TransactionFilter filter;

  /**
   * Constructs a new ExpenseTrackerController with the given model and view.
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
   * Refreshes the view with updated transactions from the model.
   */
  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions, filter);

  }

  /**
   * Adds a new transaction with the specified amount and category.
   * <p>
   * The method performs validation checks for the input amount and category.
   * </p>
   *
   * @param amount   The amount of the transaction.
   * @param category The category of the transaction.
   * @return {@code true} if the transaction was successfully added; {@code false} otherwise.
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
   * Applies a filter to the transactions based on user input.
   * <p>
   * The method determines the type of filter (Amount or Category) and updates the view accordingly.
   * </p>
   */
  public void applyFilter(){
    refresh();
    String filterType = view.getSelectedFilterType();
    String filterValue = view.getFilterValue();

    if(filterType == "Amount"){
      try{
        double amount = Double.parseDouble(filterValue);
        filter = new AmountFilter(amount);
      }catch (NumberFormatException e){
        System.out.println("Invalid Amount format");
      }
    } else if (filterType == "Category") {
        filter = new CategoryFilter(filterValue);
    }else{
      System.out.println("Unknown Filter type");
    }

    view.refreshTable(model.getTransactions(), filter);
  }
}