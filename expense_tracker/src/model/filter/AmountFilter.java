package model.filter;
import model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the TransactionFilter and updates the view with the selected amount filter.
 */
public class AmountFilter implements TransactionFilter{
    private double amount;
    
    public AmountFilter(double amount){
        this.amount = amount;
    }
    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() == amount)
                .collect(Collectors.toList());
    }
}
