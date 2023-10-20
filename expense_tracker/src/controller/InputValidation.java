/**
 * Package responsible for validating various inputs for the Expense Tracker application.
 */
package controller;

import java.util.Arrays;

/**
 * Utility class for validating inputs related to transactions.
 * <p>
 * This class provides static methods to check the validity of transaction amounts and categories.
 * </p>
 */
public class InputValidation {

  /**
   * Validates if the provided amount is within acceptable limits.
   *
   * @param amount The transaction amount to be validated.
   * @return {@code true} if the amount is valid; {@code false} otherwise.
   */
  public static boolean isValidAmount(double amount) {
    
    // Check range
    if(amount >1000) {
      return false;
    }
    if (amount < 0){
      return false;
    }
    if (amount == 0){
      return false;
    }
    return true;
  }

  /**
   * Validates if the provided category matches predefined criteria.
   * <p>
   * A valid category is non-null, non-empty, contains only alphabetic characters,
   * and matches a set of predefined valid words.
   * </p>
   *
   * @param category The category string to be validated.
   * @return {@code true} if the category is valid; {@code false} otherwise.
   */
  public static boolean isValidCategory(String category) {

    if(category == null) {
      return false; 
    }
  
    if(category.trim().isEmpty()) {
      return false;
    }

    if(!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = {"food", "travel", "bills", "entertainment", "other"};

    if(!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word  
      return false;
    }
  
    return true;
  
  }

}