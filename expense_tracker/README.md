# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

# Added Functionalities
- Introduced a new filter feature which allows the user to filter the entered transactions based on either the amount
  or category attributes. The matching rows are highlighted in green.
- If an invalid amount or category is entered in the filter text field, an error popup will be displayed, prompting
  the user to enter a valid amount or category

- Updated "serial" Column Name to "Serial Number" for better Usability
- Added encapsulation and immutability for the ExpenseTrackerModel