package controller;

import model.filter.AmountFilter;
import model.filter.CategoryFilter;
import model.filter.TransactionFilter;
import view.ExpenseTrackerView;
import java.util.List;
import model.ExpenseTrackerModel;
import model.Transaction;

import javax.swing.tree.TreeNode;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private TransactionFilter filter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions, filter);

  }

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
  
  // Other controller methods
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