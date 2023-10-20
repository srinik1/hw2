/**
 * Package responsible for providing the view component for the Expense Tracker application.
 */
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;
import model.filter.TransactionFilter;

import java.util.Arrays;
import java.util.List;

/**
 * The User interface for the Expense Tracker application which contains components to display, add or filter a transaction.
 */
public class ExpenseTrackerView extends JFrame {

  /** Table to display transactions. */
  private JTable transactionsTable;
  /** Button to initiate the addition of a transaction. */
  private JButton addTransactionBtn;
  /** Field to input the transaction amount. */
  private JFormattedTextField amountField;
  /** Field to input the transaction category. */
  private JTextField categoryField;
  /** Model for handling data in the transactionsTable. */
  private DefaultTableModel model;
  /** Button to apply filtering on transactions. */
  private JButton filterBtn;
  /** Dropdown to select the type of filter to apply. */
  private JComboBox<String> filterTypeComboBox;
  private JTextField filterValueField;
  
  /**
   * Constructs the main view for the Expense Tracker application.
   */
  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"Serial Number", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
          // All cells are not editable
          return false;
      }
  };

    addTransactionBtn = new JButton("Add Transaction");

    filterBtn = new JButton("Filter");
    filterTypeComboBox = new JComboBox<>(new String[]{"Amount", "Category"});
    filterValueField = new JTextField(10);

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    JPanel filterPanel = new JPanel();
    filterPanel.add(new JLabel("Filter by:"));
    filterPanel.add(filterTypeComboBox);
    filterPanel.add(filterValueField);
    filterPanel.add(filterBtn);
  
    // Add panels to frame
    Box combinedBox = Box.createVerticalBox();
    combinedBox.add(inputPanel);
    combinedBox.add(filterPanel);
    add(combinedBox, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  /**
   * Configures the table renderer based on the provided filter.
   *
   * @param filter The transaction filter which needs to be applied.
   */
  private void setTableRenderer(TransactionFilter filter) {
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        } else {
            if (filter != null && row < table.getRowCount() - 1) { // -1 to exclude the "Total" row
                Transaction t = new Transaction((double) table.getValueAt(row, 1), (String) table.getValueAt(row, 2));
                if (filter.filter(Arrays.asList(t)).size() > 0) {
                    c.setBackground(new Color(173, 255, 168)); 
                } else {
                    c.setBackground(table.getBackground());
                }
            } else {
                c.setBackground(table.getBackground());
            }
            c.setForeground(table.getForeground()); // This sets the default text color
        }
        return c;
      }
    });
  }

  /**
   * Refreshes the transaction table with the latest list of transactions and filter.
   *
   * @param transactions The list of transactions to be displayed.
   * @param filter       The filter to be applied on the transactions.
   */
  public void refreshTable(List<Transaction> transactions, TransactionFilter filter) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();

    setTableRenderer(filter);
  }

  /**
   * @return The button for adding a new transaction.
   */
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  /**
   * @return The basic table model for transactions.
   */
  public DefaultTableModel getTableModel() {
    return model;
  }

  // Other view methods
  /**
 * @return The transactions table.
 */
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  /**
   * @return The value entered by the user in the amount field.
   */
  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  /**
   * Sets the amount field .
   * 
   * @param amountField The formatted text field to set.
   */
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  /**
   * Gets the category input from the category field.
   * 
   * @return The category input.
   */
  public String getCategoryField() {
    return categoryField.getText();
  }

  /**
   * Sets the category field.
   * 
   * @param categoryField The category text field to set.
   */
  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  /**
   * @return filtering transactions button.
   */
  public JButton getFilterBtn() {
    return filterBtn;
  }

  /**
   * Gets the user selected filter.
   * 
   * @return The selected filter type.
   */
  public String getSelectedFilterType() {
    return (String) filterTypeComboBox.getSelectedItem();
  }

  /**
   * Retrieves the value for filtering transactions.
   * 
   * @return The input value for filtering.
   */
  public String getFilterValue() {
    return filterValueField.getText();
  }
  /**
   * Displays an error message in case an invalid amount is entered.
   */
  public void invalidAmountInput(){
    JOptionPane.showMessageDialog(null, "Please enter a value between 0 and 1000");
  }
  /**
   * Displays an error message in case an invalid category is entered.
   */
  public void invalidCategoryInput(){
    JOptionPane.showMessageDialog(null, "Please enter a category from the following list : \"food\", \"travel\", \"bills\", \"entertainment\", \"other\"");
  }
}
