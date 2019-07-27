package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BudgetService {
    private BudgetRepo budgetRepo;


    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0;
        }

        if (start.isEqual(end)) {
            return calculateBudgetAverage(start);
        }

        if (diffMonth(start, end) == 0) {
            return calculateBudgetAverage(start) * diffDay(start, end);
        }

        if (diffMonth(start, end) > 0) {
            return calculateBudgetAverage(start) * start.lengthOfMonth() + calculateBudgetAverage(end) * end.lengthOfMonth();
        }
        return 0;
    }

    private int calculateBudgetAverage(LocalDate start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        for (Budget budget : this.budgetRepo.getAll()) {
            if (budget.getYearMonth().equals(start.format(formatter))) {
                return budget.getAmount() / start.lengthOfMonth();
            }
        }
        return 0;
    }

    private int diffDay(LocalDate start, LocalDate end) {
        return end.getDayOfMonth() - start.getDayOfMonth() + 1;
    }

    private int diffMonth(LocalDate start, LocalDate end) {
        return Math.abs(start.getMonth().getValue() - end.getMonth().getValue());
    }
}
