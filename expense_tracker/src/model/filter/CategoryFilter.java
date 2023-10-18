package model.filter;
import model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter{
    private String category;

    public CategoryFilter(String category){
        this.category = category;
    }
    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}
