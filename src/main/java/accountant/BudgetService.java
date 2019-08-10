package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BudgetService {
    private BudgetRepo budgetRepo;


    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0;
        }

        Period period = new Period(start, end);


        List<Budget> budgets = this.budgetRepo.getAll();

        return budgets
                .stream()
                .mapToDouble(budget -> budget.getOverlappingAmount(period))
                .sum();


    }

    private Optional<Budget> findBudget(LocalDate targetDate) {
        return this.budgetRepo.getAll()
                .stream()
                .filter(budget -> budget.matchesDate(targetDate))
                .findFirst();
    }


}
