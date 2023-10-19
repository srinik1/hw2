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

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JButton filterBtn;
  private JComboBox<String> filterTypeComboBox;
  private JTextField filterValueField;
  

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

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

  private void setTableRenderer(TransactionFilter filter) {
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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
        return c;
      }
    });
  }

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
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public JButton getFilterBtn() {
    return filterBtn;
  }

  public String getSelectedFilterType() {
    return (String) filterTypeComboBox.getSelectedItem();
  }

  public String getFilterValue() {
    return filterValueField.getText();
  }
}
