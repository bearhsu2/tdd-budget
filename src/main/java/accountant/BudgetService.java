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

        return this.budgetRepo.getAll()
                .stream()
                .mapToDouble(budget -> budget.getOverlappingAmount(new Period(start, end)))
                .sum();


    }


}
