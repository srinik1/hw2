package model.filter;
import model.Transaction;

import java.util.List;

/**
 * This interface can be be extended given a specific criteria against which transactions need to be filtered.
 */
public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions);
}
