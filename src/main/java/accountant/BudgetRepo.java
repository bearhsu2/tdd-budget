package accountant;

import accountant.vo.Budget;

import java.util.List;

public interface BudgetRepo {
    List<Budget> getAll();
}
