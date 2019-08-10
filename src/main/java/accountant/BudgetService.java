package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
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

        // find budget
        // find budget daily amount
        // find overlapping days
        // get daily amount * overlapping days
        double total = 0D;

        for (int i = 0; i <= period.diffMonth(); i++) {
            LocalDate middle = start.plusMonths(i);

            Optional<Budget> middleBudgetOpt = findBudget(middle);

            total+= middleBudgetOpt
                    .map(budget -> budget.getOverlappingAmount(period))
                    .orElse(0D);
            
        }


        return total;

    }

    private Optional<Budget> findBudget(LocalDate targetDate) {
        return this.budgetRepo.getAll()
                .stream()
                .filter(budget -> budget.matchesDate(targetDate))
                .findFirst();
    }


}
