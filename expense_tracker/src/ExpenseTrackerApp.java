import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;
import controller.InputValidation;

import java.util.logging.Logger;

/**
 * This class initializes and coordinates the model, view, and controller components 
 * of the Expense Tracker application.
 */
public class ExpenseTrackerApp {
  /** The logger factory to create logger instances. */
  private static Logger LoggerFactory;
  /** Logger for recording any issues in runtime. */
  private static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(ExpenseTrackerApp.class));

  /**
   * 
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

    //Handle apply filter button clicks
    view.getFilterBtn().addActionListener(e -> {
      controller.applyFilter();
    });
  }
}