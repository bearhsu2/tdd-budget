package accountant;

import java.time.LocalDate;

public class BudgetService {
    private BudgetRepo budgetRepo;


    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isEqual(end)) {
            return calculateBudgetAverage(start);
        }

        if (diffMonth(start, end) == 0) {
            return calculateBudgetAverage(start) * diffDay(start, end);
        }


        return 0;
    }

    private int calculateBudgetAverage(LocalDate start) {
        return this.budgetRepo.getAll().get(0).getAmount() / start.lengthOfMonth();
    }

    private int diffDay(LocalDate start, LocalDate end) {

        return end.getDayOfMonth() - start.getDayOfMonth() + 1;
    }

    private int diffMonth(LocalDate start, LocalDate end) {
        return start.getMonth().getValue() - end.getMonth().getValue();
    }
}
