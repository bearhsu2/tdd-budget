package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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

        // find budget
        // find budget daily amount
        // find overlapping days
        // get daily amount * overlapping days
        if (start.isEqual(end)) {

            Optional<Budget> budgetOpt = getBudget(start);

            if (budgetOpt.isPresent()) {
                return budgetOpt.get().getAmount() / start.lengthOfMonth();
            } else {
                return 0D;
            }
        }

        if (diffMonth(start, end) == 0) {
            return calculateBudgetAverage(start) * diffDay(start, end);
        }

        int diffMonth = diffMonth(start, end);
        if (diffMonth > 0) {
            double partialBudget = calculateBudgetAverage(start) * (start.lengthOfMonth() - start.getDayOfMonth() + 1) +
                    calculateBudgetAverage(end) * (end.getDayOfMonth());
            for (int i = 1; i < diffMonth; i++) {
                LocalDate middle = start.plusMonths(i);
                partialBudget += calculateBudgetAverage(middle) * middle.lengthOfMonth();
            }
            return partialBudget;
        }
        return 0;
    }

    private Optional<Budget> getBudget(LocalDate targetDate) {
        return this.budgetRepo.getAll()
                        .stream()
                        .filter(budget -> budget.matchesDate(targetDate))
                        .findFirst();
    }

    private double calculateBudgetAverage(LocalDate current) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        for (Budget budget : this.budgetRepo.getAll()) {
            if (budget.getYearMonth().equals(current.format(formatter))) {
                return budget.getAmount() / current.lengthOfMonth();
            }
        }
        return 0D;
    }

    private int diffDay(LocalDate start, LocalDate end) {
        return end.getDayOfMonth() - start.getDayOfMonth() + 1;
    }

    private int diffMonth(LocalDate start, LocalDate end) {
        if (start.getYear() == end.getYear()) {
            return Math.abs(start.getMonth().getValue() - end.getMonth().getValue());
        }
        YearMonth from = YearMonth.from(start);
        return YearMonth.from(end).minusMonths(from.getMonthValue()).getMonthValue();
    }
}
